package com.eclub.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PagingCollector {

    /**
     *
     * @param pageRequest page request
     * @param count total items
     * @return page of items in flux
     * @param <T> type of items
     */
    public static <T> Function<Flux<T>, Mono<Page<T>>> collectPages(PageRequest pageRequest, Mono<Long> count) {
        return items -> items
                .collectList()
                .zipWith(count)
                .map(itemsAndCount -> new PageImpl<>(itemsAndCount.getT1(), pageRequest, itemsAndCount.getT2()));
    }
}
