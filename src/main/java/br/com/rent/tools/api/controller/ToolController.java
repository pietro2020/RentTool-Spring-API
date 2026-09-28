package br.com.rent.tools.api.controller;

import br.com.rent.tools.api.dto.CreateToolDto;
import br.com.rent.tools.api.dto.ToolDto;
import br.com.rent.tools.api.dto.UpdateToolDto;
import br.com.rent.tools.api.service.ToolService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tool")
public class ToolController {

    @Autowired
    private ToolService toolService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createTool(@RequestBody @Valid CreateToolDto dto) {
        try {
            toolService.createTool(dto);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<ToolDto>> listTools () {

        List<ToolDto> tools = toolService.listTools();
        return ResponseEntity.ok(tools);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolDto> listToolById (@PathVariable Long id) {

        ToolDto tool = toolService.listToolById(id);
        return ResponseEntity.ok(tool);

    }

    @GetMapping(params = "name")
    public ResponseEntity<List<ToolDto>> listToolsByName (@RequestParam String name) {

        List<ToolDto> tools = toolService.listToolsByName(name);
        return ResponseEntity.ok(tools);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateTool(@PathVariable Long id, @RequestBody @Valid UpdateToolDto dto) {

        toolService.updateTool(id, dto);
        return ResponseEntity.ok().build();

    }

    @PatchMapping("/disable/{id}")
    @Transactional
    public ResponseEntity<String> unavailableTool(@PathVariable Long id) {

        toolService.unavailableTool(id);
        return ResponseEntity.ok().build();

    }

    @PatchMapping("/enable/{id}")
    @Transactional
    public ResponseEntity<String> availableTool(@PathVariable Long id) {

        toolService.availableTool(id);
        return ResponseEntity.ok().build();

    }

    @PatchMapping("/maintenance/{id}")
    @Transactional
    public ResponseEntity<String> maintenanceTool(@PathVariable Long id) {

        toolService.maintenanceTool(id);
        return ResponseEntity.ok().build();

    }

}
