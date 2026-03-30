package com.civica.splitthebill.application.services;

import java.util.Optional;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

public class UseCaseUtils {
    
    private UseCaseUtils() {
    }

    public static void checkExclusivity(Long id, LongPredicate condition, Supplier<RuntimeException> exception) {
        Optional.of(id)
                .filter(condition::test)
                .ifPresent(val -> {
                    throw exception.get();
                });
    }
}
