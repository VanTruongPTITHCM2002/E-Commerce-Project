package com.ecommerce.product_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandResponse {
    UUID id;
    String name;
    String slug;
    String logoUrl;
    String description;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    ZonedDateTime createdAt;
    String createdBy;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    ZonedDateTime updatedAt;
    String updatedBy;
}
