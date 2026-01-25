package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.request.BrandRequest;
import com.ecommerce.product_service.dto.response.BrandResponse;
import com.ecommerce.product_service.dto.response.PageResponse;
import com.ecommerce.product_service.entity.Brand;
import com.ecommerce.product_service.enums.EntityStatus;
import com.ecommerce.product_service.exception.BadRequestException;
import com.ecommerce.product_service.exception.NotFoundException;
import com.ecommerce.product_service.mapper.BrandMapper;
import com.ecommerce.product_service.repository.BrandRepository;
import com.ecommerce.product_service.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;

    @Override
    @Transactional
    public BrandResponse createBrand(BrandRequest brandRequest) {
        try {
            log.info("Starting create brand");
             this.validateBrandName(brandRequest.getName());
             this.validateBrandSlug(brandRequest.getSlug());
            Brand brand = this.brandMapper.toEntity(brandRequest);
            this.brandRepository.save(brand);
            log.debug("Create brand with id : {}", brand.getId());
            return this.brandMapper.toResponse(brand);
        } finally {
            log.info("Ending create brand");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BrandResponse> getBrands(Specification<Brand> specification, Pageable pageable, String filter) {
        specification = specification.and((root, query, cb) -> root.get("entityStatus").in(EntityStatus.ACTIVE));
        Page<Brand> brandPage = this.brandRepository.findAll(specification, pageable);
        Page<BrandResponse> brandResponses = brandPage.map(this.brandMapper::toResponse);
        return new PageResponse<>(brandResponses, "Brands", filter);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandById(String id) {
        Brand brand = this.brandRepository.findById(UUID.fromString(id))
                .filter(b-> EntityStatus.ACTIVE.equals(b.getEntityStatus()))
                .orElseThrow(() -> new NotFoundException("Brand not found"));
        return this.brandMapper.toResponse(brand);
    }

    @Override
    @Transactional
    public void deleteBrand(String id) {
        try {
            log.info("Starting delete brand");
            Brand brand = this.brandRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new NotFoundException("Brand not found"));
            if (EntityStatus.INACTIVE.equals(brand.getEntityStatus()) || !brand.getProducts().isEmpty()) {
                throw new BadRequestException("Couldn't delete brand");
            }

            brand.setEntityStatus(EntityStatus.INACTIVE);
            brand.setUpdatedAt(ZonedDateTime.now());
            brand.setUpdatedBy(UUID.randomUUID().toString());
            brand.setDeletedAt(ZonedDateTime.now());
            brand.setDeletedBy(UUID.randomUUID().toString());
            log.debug("Delete brand with id: {}", brand.getId());
            this.brandRepository.save(brand);
            log.info("Delete brand successfully with id: {}", brand.getId());
        } catch (Exception exception) {
            log.error("Failed delete brand and error cause {}", exception.getMessage());
            throw  exception;
        } finally {
            log.info("Ending delete brand");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> selectBrandInfinity() {
        List<Brand> brands = this.brandRepository.findAll()
                        .stream()
                        .filter(brand -> EntityStatus.ACTIVE.equals(brand.getEntityStatus()))
                        .toList();

        return brands.stream().map(this.brandMapper::toResponse).toList();
    }

    private void validateBrandName (String  name) {
        boolean isExistsName = this.brandRepository.existsByName(name);

        if (isExistsName) {
            throw new BadRequestException("Brand name was exists in program");
        }
    }

    private void validateBrandSlug (String slug) {
        boolean isExistsSlug = this.brandRepository.existsBySlug(slug);

        if (isExistsSlug) {
            throw new BadRequestException("Brand slug was exists in program");
        }
    }
}
