package br.com.rent.tools.api.service;

import br.com.rent.tools.api.dto.CreateToolDto;
import br.com.rent.tools.api.dto.ToolDto;
import br.com.rent.tools.api.dto.UpdateToolDto;
import br.com.rent.tools.api.model.Category;
import br.com.rent.tools.api.model.Condition;
import br.com.rent.tools.api.model.Tool;
import br.com.rent.tools.api.repository.ToolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToolServiceTest {

    @Mock
    private ToolRepository repository;

    @InjectMocks
    private ToolService service;


    // =========================
    // CREATE
    // =========================

    @Test
    void shouldCreateTool() {

        CreateToolDto dto = new CreateToolDto(
                "Furadeira",
                50.0,
                Category.ELETRICA,
                "Furadeira elétrica",
                3,
                Condition.GOOD
        );

        when(repository.existsByNameAndDescription(
                dto.name(),
                dto.description()
        )).thenReturn(false);

        service.createTool(dto);

        verify(repository).save(any(Tool.class));
    }

    @Test
    void shouldNotCreateToolWhenAlreadyExists() {

        CreateToolDto dto = new CreateToolDto(
                "Furadeira",
                50.0,
                Category.ELETRICA,
                "Furadeira elétrica",
                3,
                Condition.GOOD
        );

        when(repository.existsByNameAndDescription(
                dto.name(),
                dto.description()
        )).thenReturn(true);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> service.createTool(dto)
        );

        verify(repository, never()).save(any(Tool.class));
    }


    // =========================
    // LIST ALL
    // =========================

    @Test
    void shouldListAllTools() {

        Tool tool1 = new Tool(new CreateToolDto(
                "Furadeira",
                50.0,
                Category.ELETRICA,
                "Furadeira elétrica",
                3,
                Condition.GOOD
        ));

        Tool tool2 = new Tool(new CreateToolDto(
                "Martelo",
                20.0,
                Category.MANUAL,
                "Martelo profissional",
                1,
                Condition.EXCELLENT
        ));

        when(repository.findAll())
                .thenReturn(List.of(tool1, tool2));

        List<ToolDto> result = service.listTools();

        assertEquals(2, result.size());

        verify(repository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoTools() {

        when(repository.findAll())
                .thenReturn(List.of());

        List<ToolDto> result = service.listTools();

        assertTrue(result.isEmpty());

        verify(repository).findAll();
    }


    // =========================
    // FIND BY ID
    // =========================

    @Test
    void shouldListToolById() {

        Long id = 1L;

        Tool tool = new Tool(new CreateToolDto(
                "Furadeira",
                50.0,
                Category.ELETRICA,
                "Furadeira elétrica",
                3,
                Condition.GOOD
        ));

        when(repository.findById(id))
                .thenReturn(Optional.of(tool));

        ToolDto result = service.listToolById(id);

        assertNotNull(result);

        verify(repository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenToolDoesNotExist() {

        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.listToolById(id)
        );

        verify(repository).findById(id);
    }


    // =========================
    // FIND BY NAME
    // =========================

    @Test
    void shouldListToolsByName() {

        String name = "Furadeira";

        Tool tool1 = new Tool(new CreateToolDto(
                "Furadeira",
                50.0,
                Category.ELETRICA,
                "Furadeira elétrica",
                3,
                Condition.GOOD
        ));

        Tool tool2 = new Tool(new CreateToolDto(
                "Furadeira",
                60.0,
                Category.ELETRICA,
                "Furadeira profissional",
                5,
                Condition.EXCELLENT
        ));

        when(repository.findByNameIgnoreCase(name))
                .thenReturn(List.of(tool1, tool2));

        List<ToolDto> result = service.listToolsByName(name);

        assertEquals(2, result.size());

        verify(repository).findByNameIgnoreCase(name);
    }

    @Test
    void shouldReturnEmptyListWhenToolNameDoesNotExist() {

        String name = "Furadeira";

        when(repository.findByNameIgnoreCase(name))
                .thenReturn(List.of());

        List<ToolDto> result = service.listToolsByName(name);

        assertTrue(result.isEmpty());

        verify(repository).findByNameIgnoreCase(name);
    }


    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateTool() {

        Long id = 1L;

        Tool tool = mock(Tool.class);

        UpdateToolDto dto = new UpdateToolDto(
                70.0,
                5,
                Condition.EXCELLENT
        );

        when(repository.findById(id))
                .thenReturn(Optional.of(tool));

        service.updateTool(id, dto);

        verify(repository).findById(id);
        verify(tool).update(dto);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentTool() {

        Long id = 1L;

        UpdateToolDto dto = new UpdateToolDto(
                70.0,
                5,
                Condition.EXCELLENT
        );

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.updateTool(id, dto)
        );

        verify(repository).findById(id);
    }


    // =========================
    // UNAVAILABLE
    // =========================

    @Test
    void shouldMakeToolUnavailable() {

        Long id = 1L;

        Tool tool = mock(Tool.class);

        when(repository.findById(id))
                .thenReturn(Optional.of(tool));

        service.unavailableTool(id);

        verify(repository).findById(id);
        verify(tool).unavailableTool();
    }

    @Test
    void shouldThrowExceptionWhenMakingNonexistentToolUnavailable() {

        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.unavailableTool(id)
        );

        verify(repository).findById(id);
    }


    // =========================
    // AVAILABLE
    // =========================

    @Test
    void shouldMakeToolAvailable() {

        Long id = 1L;

        Tool tool = mock(Tool.class);

        when(repository.findById(id))
                .thenReturn(Optional.of(tool));

        service.availableTool(id);

        verify(repository).findById(id);
        verify(tool).availableTool();
    }

    @Test
    void shouldThrowExceptionWhenMakingNonexistentToolAvailable() {

        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.availableTool(id)
        );

        verify(repository).findById(id);
    }


    // =========================
    // MAINTENANCE
    // =========================

    @Test
    void shouldPutToolUnderMaintenance() {

        Long id = 1L;

        Tool tool = mock(Tool.class);

        when(repository.findById(id))
                .thenReturn(Optional.of(tool));

        service.maintenanceTool(id);

        verify(repository).findById(id);
        verify(tool).maintenanceTool();
    }

    @Test
    void shouldThrowExceptionWhenPuttingNonexistentToolUnderMaintenance() {

        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.maintenanceTool(id)
        );

        verify(repository).findById(id);
    }
}