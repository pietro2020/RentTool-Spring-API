package br.com.rent.tools.api.dto;

import br.com.rent.tools.api.model.Client;
import br.com.rent.tools.api.model.Tool;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateRentDto(

        @NotNull
        Integer days,

        @NotNull
        Long clientId,

        @NotNull
        Long toolId

) {}
