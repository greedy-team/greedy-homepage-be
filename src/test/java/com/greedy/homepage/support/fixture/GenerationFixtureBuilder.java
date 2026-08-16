package com.greedy.homepage.support.fixture;

import com.greedy.homepage.generation.domain.Generation;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

public class GenerationFixtureBuilder {

    private Integer number = 1;
    private LocalDate startDate = LocalDate.of(2025, 3, 1);
    private LocalDate endDate = LocalDate.of(2025, 8, 31);

    public static GenerationFixtureBuilder builder() {
        return new GenerationFixtureBuilder();
    }

    public GenerationFixtureBuilder number(Integer number) {
        this.number = number;
        return this;
    }

    public GenerationFixtureBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public GenerationFixtureBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Generation build() {
        return Generation.builder()
                .number(number)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public Generation buildWithId(Long id) {
        Generation generation = build();
        ReflectionTestUtils.setField(generation, "id", id);
        return generation;
    }
}
