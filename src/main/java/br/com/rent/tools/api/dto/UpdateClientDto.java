package br.com.rent.tools.api.dto;
import br.com.rent.tools.api.model.CustomerCategory;

public record UpdateClientDto(

        String name,

        CustomerCategory category

) {}
