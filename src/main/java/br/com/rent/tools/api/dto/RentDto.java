package br.com.rent.tools.api.dto;

import br.com.rent.tools.api.model.*;

import java.time.LocalDateTime;

public record RentDto(Long id, LocalDateTime startDate, LocalDateTime expectedReturnDate, LocalDateTime actualReturnDate, Client client, Tool tool) {

    public RentDto(Rent rent) {
        this(rent.getId(), rent.getStartDate(), rent.getExpectedReturnDate(), rent.getActualReturnDate(), rent.getClient(), rent.getTool());
    }
}
