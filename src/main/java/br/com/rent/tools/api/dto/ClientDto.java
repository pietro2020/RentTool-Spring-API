package br.com.rent.tools.api.dto;

import br.com.rent.tools.api.model.Client;
import br.com.rent.tools.api.model.CustomerCategory;

public record ClientDto(Long id, String name, String cpf, CustomerCategory category, Boolean defaulted) {

    public ClientDto(Client client) {

        this(client.getId(), client.getName(), client.getCpf(), client.getCategory(), client.getDefaulted());

    }

}
