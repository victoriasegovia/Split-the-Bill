package com.civica.splitthebill.infraestructure.adapter.out.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.MapperUtils;

class MapperUtilsTest {

    @Test
    void entitiesToIdSet_ShouldInteractWithMocks() {
        
        Object mockEntity1 = Mockito.mock(Object.class);
        Object mockEntity2 = Mockito.mock(Object.class);
        List<Object> entities = List.of(mockEntity1, mockEntity2);

        Function<Object, Long> idExtractor = Mockito.mock(Function.class);
        Mockito.when(idExtractor.apply(mockEntity1)).thenReturn(101L);
        Mockito.when(idExtractor.apply(mockEntity2)).thenReturn(102L);

        Set<Long> result = MapperUtils.entitiesToIdSet(entities, idExtractor);

        assertAll(
            () -> assertEquals(2, result.size()),
            () -> assertTrue(result.contains(101L)),
            () -> assertTrue(result.contains(102L)),
            () -> Mockito.verify(idExtractor).apply(mockEntity1),
            () -> Mockito.verify(idExtractor).apply(mockEntity2)
        );
    }

    @Test
    void idsToEntityProxySet_ShouldInvokeCreationFunction() {

        Set<Long> ids = Set.of(50L);
        Object mockProxy = Mockito.mock(Object.class);
        
        Function<Long, Object> creator = Mockito.mock(Function.class);
        Mockito.when(creator.apply(50L)).thenReturn(mockProxy);

        List<Object> result = MapperUtils.idsToEntityProxySet(ids, creator);

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(mockProxy, result.get(0)),
            () -> Mockito.verify(creator).apply(50L)
        );
    }
}
