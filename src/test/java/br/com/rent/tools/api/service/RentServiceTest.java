package br.com.rent.tools.api.service;

import br.com.rent.tools.api.dto.CreateRentDto;
import br.com.rent.tools.api.dto.RentDto;
import br.com.rent.tools.api.model.Available;
import br.com.rent.tools.api.model.Client;
import br.com.rent.tools.api.model.Rent;
import br.com.rent.tools.api.model.Tool;
import br.com.rent.tools.api.repository.ClientRepository;
import br.com.rent.tools.api.repository.RentRepository;
import br.com.rent.tools.api.repository.ToolRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentServiceTest {

    @Mock
    private RentRepository rentRepository;

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private RentService service;


    // =========================
    // CREATE RENT
    // =========================

    @Test
    void shouldCreateRent() {

        Long clientId = 1L;
        Long toolId = 1L;

        CreateRentDto dto = new CreateRentDto(
                5,
                clientId,
                toolId
        );

        Client client = mock(Client.class);
        Tool tool = mock(Tool.class);

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.of(client));

        when(toolRepository.findById(toolId))
                .thenReturn(Optional.of(tool));

        when(tool.getId())
                .thenReturn(toolId);

        when(rentRepository.existsByToolAndStatusTrue(tool))
                .thenReturn(false);

        when(toolRepository.existsByIdAndAvailable(
                toolId,
                Available.AVAILABLE
        )).thenReturn(true);

        when(client.rentLimit())
                .thenReturn(3);

        when(rentRepository.hasMoreEqualsThanRents(
                client,
                3
        )).thenReturn(false);

        service.createRent(dto);

        verify(rentRepository).save(any(Rent.class));
    }


    @Test
    void shouldNotCreateRentWhenToolIsAlreadyRented() {

        Long clientId = 1L;
        Long toolId = 1L;

        CreateRentDto dto = new CreateRentDto(
                5,
                clientId,
                toolId
        );

        Client client = mock(Client.class);
        Tool tool = mock(Tool.class);

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.of(client));

        when(toolRepository.findById(toolId))
                .thenReturn(Optional.of(tool));

        when(tool.getId())
                .thenReturn(toolId);

        when(rentRepository.existsByToolAndStatusTrue(tool))
                .thenReturn(true);

        when(toolRepository.existsByIdAndAvailable(
                toolId,
                Available.AVAILABLE
        )).thenReturn(true);

        when(client.rentLimit())
                .thenReturn(3);

        when(rentRepository.hasMoreEqualsThanRents(
                client,
                3
        )).thenReturn(false);

        assertThrows(
                ValidationException.class,
                () -> service.createRent(dto)
        );

        verify(rentRepository, never())
                .save(any(Rent.class));
    }


    @Test
    void shouldNotCreateRentWhenToolIsUnavailable() {

        Long clientId = 1L;
        Long toolId = 1L;

        CreateRentDto dto = new CreateRentDto(
                5,
                clientId,
                toolId
        );

        Client client = mock(Client.class);
        Tool tool = mock(Tool.class);

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.of(client));

        when(toolRepository.findById(toolId))
                .thenReturn(Optional.of(tool));

        when(tool.getId())
                .thenReturn(toolId);

        when(rentRepository.existsByToolAndStatusTrue(tool))
                .thenReturn(false);

        when(toolRepository.existsByIdAndAvailable(
                toolId,
                Available.AVAILABLE
        )).thenReturn(false);

        when(client.rentLimit())
                .thenReturn(3);

        when(rentRepository.hasMoreEqualsThanRents(
                client,
                3
        )).thenReturn(false);

        assertThrows(
                ValidationException.class,
                () -> service.createRent(dto)
        );

        verify(rentRepository, never())
                .save(any(Rent.class));
    }


    @Test
    void shouldNotCreateRentWhenClientReachedRentLimit() {

        Long clientId = 1L;
        Long toolId = 1L;

        CreateRentDto dto = new CreateRentDto(
                5,
                clientId,
                toolId
        );

        Client client = mock(Client.class);
        Tool tool = mock(Tool.class);

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.of(client));

        when(toolRepository.findById(toolId))
                .thenReturn(Optional.of(tool));

        when(tool.getId())
                .thenReturn(toolId);

        when(rentRepository.existsByToolAndStatusTrue(tool))
                .thenReturn(false);

        when(toolRepository.existsByIdAndAvailable(
                toolId,
                Available.AVAILABLE
        )).thenReturn(true);

        when(client.rentLimit())
                .thenReturn(3);

        when(rentRepository.hasMoreEqualsThanRents(
                client,
                3
        )).thenReturn(true);

        assertThrows(
                ValidationException.class,
                () -> service.createRent(dto)
        );

        verify(rentRepository, never())
                .save(any(Rent.class));
    }


    // =========================
    // CLIENT NOT FOUND
    // =========================

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {

        Long clientId = 1L;
        Long toolId = 1L;

        CreateRentDto dto = new CreateRentDto(
                5,
                clientId,
                toolId
        );

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.createRent(dto)
        );

        verify(toolRepository, never())
                .findById(toolId);

        verify(rentRepository, never())
                .save(any(Rent.class));
    }


    // =========================
    // TOOL NOT FOUND
    // =========================

    @Test
    void shouldThrowExceptionWhenToolDoesNotExist() {

        Long clientId = 1L;
        Long toolId = 1L;

        CreateRentDto dto = new CreateRentDto(
                5,
                clientId,
                toolId
        );

        Client client = mock(Client.class);

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.of(client));

        when(toolRepository.findById(toolId))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.createRent(dto)
        );

        verify(rentRepository, never())
                .save(any(Rent.class));
    }


    // =========================
    // LIST RENTS
    // =========================

    @Test
    void shouldListActiveRents() {

        Rent rent1 = mock(Rent.class);
        Rent rent2 = mock(Rent.class);

        when(rentRepository.findAllByStatus(true))
                .thenReturn(List.of(rent1, rent2));

        List<RentDto> result = service.listRents();

        assertEquals(2, result.size());

        verify(rentRepository)
                .findAllByStatus(true);
    }


    @Test
    void shouldReturnEmptyListWhenThereAreNoActiveRents() {

        when(rentRepository.findAllByStatus(true))
                .thenReturn(List.of());

        List<RentDto> result = service.listRents();

        assertTrue(result.isEmpty());

        verify(rentRepository)
                .findAllByStatus(true);
    }


    // =========================
    // FINISH RENT
    // =========================

    @Test
    void shouldFinishRent() {

        Long id = 1L;

        Rent rent = mock(Rent.class);

        when(rentRepository.findById(id))
                .thenReturn(Optional.of(rent));

        service.rentFinish(id);

        verify(rentRepository)
                .findById(id);

        verify(rent)
                .finish();
    }


    @Test
    void shouldThrowExceptionWhenFinishingNonexistentRent() {

        Long id = 1L;

        when(rentRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.rentFinish(id)
        );

        verify(rentRepository)
                .findById(id);
    }
}