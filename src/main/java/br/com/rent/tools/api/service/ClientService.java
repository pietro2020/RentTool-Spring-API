package br.com.rent.tools.api.service;

import br.com.rent.tools.api.dto.ClientDto;
import br.com.rent.tools.api.dto.CreateClientDto;
import br.com.rent.tools.api.dto.ToolDto;
import br.com.rent.tools.api.dto.UpdateClientDto;
import br.com.rent.tools.api.model.Client;
import br.com.rent.tools.api.model.Tool;
import br.com.rent.tools.api.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public void createClient(CreateClientDto dto) {

        boolean jaCadastrado = repository.existsByCpf(dto.cpf());

        if(jaCadastrado) {
            throw new DataIntegrityViolationException("Dados já cadastrados");
        }

        repository.save(new Client(dto));

    }


    public ClientDto listClientById(Long id) {

        Client client = returnClientById(id);

        return new ClientDto(client);

    }

    public void updateClient(Long id, UpdateClientDto dto) {

        Client client = returnClientById(id);

        client.update(dto);

    }



    private Client returnClientById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }
}
