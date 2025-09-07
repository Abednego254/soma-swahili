package com.abednego.somaSwahili.model;

public enum WalletTransactionType {
    // Money into wallet
    DEPOSIT,            // External top-up from payment gateway
    CREDIT_BACK,        // Tutor declined or booking cancelled -> credit returns to wallet (non-cash refund)
    BONUS,              // Promotional/top-up by system
    ADJUSTMENT_CREDIT,  // Manual admin correction (credit)

    // Money out of wallet
    SERVICE_FEE,        // One-time system fee
    LESSON_PAYMENT,     // Pay-as-you-go session
    ADJUSTMENT_DEBIT    // Manual admin correction (debit)
}
