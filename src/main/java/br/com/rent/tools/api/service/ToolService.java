package br.com.rent.tools.api.service;

import br.com.rent.tools.api.dto.CreateToolDto;
import br.com.rent.tools.api.dto.ToolDto;
import br.com.rent.tools.api.dto.UpdateToolDto;
import br.com.rent.tools.api.model.Tool;
import br.com.rent.tools.api.repository.ToolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    @Autowired
    private ToolRepository repository;

    public void createTool(CreateToolDto dto) {
        boolean jaCadastrado = repository.existsByNameAndDescription(dto.name(), dto.description());

        if(jaCadastrado) {
            throw new DataIntegrityViolationException("Dados já cadastrados");
        }

        repository.save(new Tool(dto));

    }

    public List<ToolDto> listTools() {

        return repository.findAll()
                .stream()
                .map(ToolDto::new)
                .toList();

    }

    public ToolDto listToolById(Long id) {
        Tool tool = returnToolById(id);

        return new ToolDto(tool);
    }

    public List<ToolDto> listToolsByName(String name) {

        return repository
                .findByNameIgnoreCase(name)
                .stream()
                .map(ToolDto::new)
                .toList();

    }

    public void updateTool(Long id, UpdateToolDto dto) {

        Tool tool = returnToolById(id);

        tool.update(dto);

    }

    public void unavailableTool(Long id) {

        Tool tool = returnToolById(id);

        tool.unavailableTool();

    }

    public void availableTool(Long id) {

        Tool tool = returnToolById(id);

        tool.availableTool();

    }

    public void maintenanceTool(Long id) {

        Tool tool = returnToolById(id);

        tool.maintenanceTool();

    }

    private Tool returnToolById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ferramenta não encontrada"));
    }
}
