package com.ecommerce.order_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @NotEmpty(message = "userId must not empty")
    @NotNull (message = "userId must not null")
    String userId;

    LocalDate createAt;

    LocalDate updateAt;

    @Min(value = 0, message = "total price must equal or greater than 0")
    BigDecimal totalPrice;
}
