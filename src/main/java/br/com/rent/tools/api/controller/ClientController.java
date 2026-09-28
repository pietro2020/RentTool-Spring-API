package br.com.rent.tools.api.controller;

import br.com.rent.tools.api.dto.ClientDto;
import br.com.rent.tools.api.dto.CreateClientDto;
import br.com.rent.tools.api.dto.UpdateClientDto;
import br.com.rent.tools.api.dto.UpdateToolDto;
import br.com.rent.tools.api.service.ClientService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createClient(@RequestBody @Valid CreateClientDto dto) {

        try {
            clientService.createClient(dto);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<ClientDto> createClient(@PathVariable Long id) {

        ClientDto client = clientService.listClientById(id);
        return ResponseEntity.ok(client);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateClient(@PathVariable Long id, @RequestBody @Valid UpdateClientDto dto) {

        clientService.updateClient(id, dto);
        return ResponseEntity.ok().build();

    }

}
