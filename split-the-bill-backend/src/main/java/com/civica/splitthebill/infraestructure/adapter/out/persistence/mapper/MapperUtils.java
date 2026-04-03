package com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtils {

    private MapperUtils() {
    }

    public static <E, K> Set<K> entitiesToIdSet(List<E> entities, Function<E, K> idExtractor) {
        return Optional.ofNullable(entities)
                .filter(org.hibernate.Hibernate::isInitialized)
                .stream()
                .flatMap(Collection::stream)
                .map(idExtractor)
                .collect(Collectors.toSet());
    }

    public static <E, K> List<E> idsToEntityProxySet(Set<K> ids, Function<K, E> entityCreation) {
        return ids.stream()
                .map(entityCreation)
                .toList();
    }

    public static <E, K> E createEntityProxy(K id, Function<K, E> entityCreation) {
        return entityCreation.apply(id);
    }
}
