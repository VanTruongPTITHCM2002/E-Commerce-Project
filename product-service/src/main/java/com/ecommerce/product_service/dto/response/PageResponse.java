package com.ecommerce.product_service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public class PageResponse<T> {
    List<T> items;
    int page;
    int size;
    long totalItems;
    int totalPages;
    boolean hasNext;
    boolean hasPrevious;
    String resource;
    String filter;

    public PageResponse(List<T> items, int page, int size, long totalItems, String filter) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / size);
        this.hasNext = page + 1 < totalPages;
        this.hasPrevious = page > 0;
        this.filter = filter;
    }

    public PageResponse(Page<T> items, String resource, String filter) {
        this.items = items.toList();
        this.page = items.getPageable().getPageNumber();
        this.size = items.getPageable().getPageSize();
        this.totalItems = items.getTotalElements();
        this.totalPages = (int) Math.ceil((double) totalItems / size);
        this.hasNext = page + 1 < totalPages;
        this.hasPrevious = page > 0;
        this.resource = resource;
        this.filter = filter;
    }
}
