package br.com.rent.tools.api.dto;

import br.com.rent.tools.api.model.Available;
import br.com.rent.tools.api.model.Category;
import br.com.rent.tools.api.model.Condition;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateToolDto(

        @NotBlank
        String name,

        @NotNull
        Double price,

        @NotNull
        Category category,

        String description,

        @NotNull
        Integer minimumRentalDays,

        @NotNull
        Condition condition

) {}
