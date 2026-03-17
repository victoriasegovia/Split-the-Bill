package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtils {

    private MapperUtils() {
    }

    public static <E, K> Set<K> entitiesToIdSet(Set<E> entities, Function<E, K> idExtractor) {
        return entities.stream()
                .map(idExtractor)
                .collect(Collectors.toSet());
    }

    public static <E, K> Set<E> idsToEntityProxySet(Set<K> ids, Function<K, E> entityCreation) {
        return ids.stream()
                .map(entityCreation)
                .collect(Collectors.toSet());
    }

    public static <E, K> E createEntityProxy (K id, Function<K, E> entityCreation) {
        return entityCreation.apply(id);
    }
}
