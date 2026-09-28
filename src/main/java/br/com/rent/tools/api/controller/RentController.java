package br.com.rent.tools.api.controller;

import br.com.rent.tools.api.dto.CreateClientDto;
import br.com.rent.tools.api.dto.CreateRentDto;
import br.com.rent.tools.api.dto.RentDto;
import br.com.rent.tools.api.model.Rent;
import br.com.rent.tools.api.service.RentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentController {

    @Autowired
    private RentService rentService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createRent(@RequestBody @Valid CreateRentDto dto) {

        try {
            rentService.createRent(dto);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<RentDto>> listRents() {

        List<RentDto> rents = rentService.listRents();
        return ResponseEntity.ok(rents);

    }

    @PatchMapping("/{id}/finish")
    @Transactional
    public ResponseEntity<String> rentFinish(@PathVariable Long id) {

        rentService.rentFinish(id);
        return ResponseEntity.ok().build();

    }
}
