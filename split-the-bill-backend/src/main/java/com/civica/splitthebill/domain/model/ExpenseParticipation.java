package com.civica.splitthebill.domain.model;

public record ExpenseParticipation (

    Long participantId,
    Expense expense,
    double owedAmount,
    double paidAmount
    
){}