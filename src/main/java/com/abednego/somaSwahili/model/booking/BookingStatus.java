package com.abednego.somaSwahili.model.booking;

public enum BookingStatus {
    PENDING,            // student requested; awaiting tutor action
    CONFIRMED,          // tutor accepted
    COMPLETED,          // lesson delivered
    CANCELLED,          // cancelled (by student/admin, with policy)
    DECLINED_BY_TUTOR   // tutor declined; credit goes back to wallet (CREDIT_BACK)
}
