package com.ecommerce.cart_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    @NotNull(message = "Please don't put value null userId")
    @NotEmpty(message = "Please don't ignore userId")
    String userId;
    Date createAt;
    Date updateAt;
    @Min(value = 0, message = "Value total must greater than 0")
    @NotEmpty(message = "Please don't ignore total")
    int total;
}
