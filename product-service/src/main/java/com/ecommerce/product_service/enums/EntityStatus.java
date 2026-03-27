package com.ecommerce.product_service.enums;

public enum EntityStatus {
    ACTIVE,
    INACTIVE,
    //    OUT_OF_STOCK,
//    DRAFT,
    DELETED;

    public boolean canTransitionTo(EntityStatus entityStatus) {
        return switch (this) {
            case ACTIVE -> entityStatus == INACTIVE || entityStatus == DELETED;
            case INACTIVE -> entityStatus == ACTIVE || entityStatus == DELETED;
            case DELETED -> entityStatus == INACTIVE;
        };
    }
}
