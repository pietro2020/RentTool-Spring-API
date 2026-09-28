package br.com.rent.tools.api.dto;

import br.com.rent.tools.api.model.Available;
import br.com.rent.tools.api.model.Condition;
import jakarta.validation.constraints.NotNull;

public record UpdateToolDto(

        Double price,

        Integer minimumRentalDays,

        Condition condition

) {}