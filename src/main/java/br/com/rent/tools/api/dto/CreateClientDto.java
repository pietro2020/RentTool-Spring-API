package br.com.rent.tools.api.dto;

import br.com.rent.tools.api.model.CustomerCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateClientDto(

        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf,

        @NotNull
        CustomerCategory category

) {}
