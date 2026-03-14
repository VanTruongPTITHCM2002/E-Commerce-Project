package com.ecommerce.product_service.utils;

import com.ecommerce.product_service.enums.EntityStatus;

public class StatusUtils {
    public static boolean transitionStatus(String fromStatus, String toStatus) {
         EntityStatus oldStatus = getEntityStatus(fromStatus);
         EntityStatus newStatus = getEntityStatus(toStatus);
         return oldStatus.canTransitionTo(newStatus);
    }

    public static EntityStatus getEntityStatus(String status) {
        return EntityStatus.valueOf(status);
    }
}
