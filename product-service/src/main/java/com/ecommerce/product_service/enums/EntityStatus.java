package com.ecommerce.product_service.enums;

public enum EntityStatus {
    ACTIVE {
        @Override
        public boolean canTransitionTo(EntityStatus entityStatus) {
            return entityStatus == INACTIVE || entityStatus == DELETED;
        }
    },
    INACTIVE {
        @Override
        public boolean canTransitionTo(EntityStatus entityStatus) {
            return entityStatus == ACTIVE || entityStatus == DELETED;
        }
    },
//    OUT_OF_STOCK,
//    DRAFT,
    DELETED {
    @Override
    public boolean canTransitionTo(EntityStatus entityStatus) {
        return entityStatus == INACTIVE;
    }
};
    public abstract boolean canTransitionTo(EntityStatus entityStatus);
}
