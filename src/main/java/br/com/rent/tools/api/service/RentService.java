package br.com.rent.tools.api.service;

import br.com.rent.tools.api.dto.CreateRentDto;
import br.com.rent.tools.api.dto.RentDto;
import br.com.rent.tools.api.model.Available;
import br.com.rent.tools.api.model.Client;
import br.com.rent.tools.api.model.Rent;
import br.com.rent.tools.api.model.Tool;
import br.com.rent.tools.api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentService {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private ClientRepository clientRepository;

    public void createRent(@Valid CreateRentDto dto) {

        Client client = returnClientById(dto.clientId());
        Tool tool = returnToolById(dto.toolId());

        boolean rentTool = rentRepository.existsByToolAndStatusTrue(tool);
        boolean availableTool = toolRepository.existsByIdAndAvailable(tool.getId(), Available.AVAILABLE);
        boolean clientLimit = rentRepository.hasMoreEqualsThanRents(client, client.rentLimit());

        if(rentTool || !availableTool || clientLimit) {
            throw new ValidationException("Não foi possível fazer o aluguel");
        }

        rentRepository.save(new Rent(dto, client, tool));
    }

    private Tool returnToolById(Long id) {
        return toolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ferramenta não encontrada"));
    }

    private Client returnClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    private Rent returnRentById(Long id) {
        return rentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel não encontrado"));
    }

    public List<RentDto> listRents() {

        return rentRepository
                .findAllByStatus(true)
                .stream()
                .map(RentDto::new)
                .toList();

    }

    public void rentFinish(Long id) {

        Rent rent = returnRentById(id);
        rent.finish();

    }
}
