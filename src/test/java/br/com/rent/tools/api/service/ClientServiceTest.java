package br.com.rent.tools.api.service;

import br.com.rent.tools.api.dto.ClientDto;
import br.com.rent.tools.api.dto.CreateClientDto;
import br.com.rent.tools.api.dto.UpdateClientDto;
import br.com.rent.tools.api.model.Client;
import br.com.rent.tools.api.model.CustomerCategory;
import br.com.rent.tools.api.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService service;


    @Test
    void shouldCreateClient() {

        CreateClientDto dto = new CreateClientDto(
                "João",
                "12345678900",
                CustomerCategory.REGULAR
        );

        when(repository.existsByCpf(dto.cpf()))
                .thenReturn(false);

        service.createClient(dto);

        verify(repository).save(any(Client.class));
    }


    @Test
    void shouldNotCreateClientWhenCpfAlreadyExists() {

        CreateClientDto dto = new CreateClientDto(
                "João",
                "12345678900",
                CustomerCategory.REGULAR
        );

        when(repository.existsByCpf(dto.cpf()))
                .thenReturn(true);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> service.createClient(dto)
        );

        verify(repository, never()).save(any(Client.class));
    }


    @Test
    void shouldListClientById() {

        Long id = 1L;

        Client client = new Client(
                new CreateClientDto(
                        "João",
                        "12345678900",
                        CustomerCategory.REGULAR
                )
        );

        when(repository.findById(id))
                .thenReturn(Optional.of(client));

        ClientDto result = service.listClientById(id);

        assertNotNull(result);
        assertEquals("João", result.name());
        assertEquals("12345678900", result.cpf());

        verify(repository).findById(id);
    }


    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.listClientById(id)
        );

        verify(repository).findById(id);
    }


    @Test
    void shouldUpdateClient() {

        Long id = 1L;

        Client client = new Client(
                new CreateClientDto(
                        "João",
                        "12345678900",
                        CustomerCategory.REGULAR
                )
        );

        UpdateClientDto dto = new UpdateClientDto(
                "Pedro",
                CustomerCategory.PREMIUM
        );

        when(repository.findById(id))
                .thenReturn(Optional.of(client));

        service.updateClient(id, dto);

        assertEquals("Pedro", client.getName());
        assertEquals(CustomerCategory.PREMIUM, client.getCategory());

        verify(repository).findById(id);
    }


    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentClient() {

        Long id = 1L;

        UpdateClientDto dto = new UpdateClientDto(
                "Pedro",
                CustomerCategory.PREMIUM
        );

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.updateClient(id, dto)
        );

        verify(repository).findById(id);
    }
}