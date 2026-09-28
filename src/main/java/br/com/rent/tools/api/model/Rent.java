package br.com.rent.tools.api.model;

import br.com.rent.tools.api.dto.CreateRentDto;
import br.com.rent.tools.api.dto.ToolDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rents")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime expectedReturnDate;

    private LocalDateTime actualReturnDate;

    @Column(name = "rent_status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id", nullable = false)
    private Tool tool;

    public Rent() {}

    public Rent(CreateRentDto dto, Client client, Tool tool) {
        this.startDate = LocalDateTime.now();
        this.expectedReturnDate = finishDay(dto.days());
        this.actualReturnDate = null;
        this.status = true;
        this.client = client;
        this.tool = tool;
    }

    private LocalDateTime finishDay(Integer days) {

        return LocalDateTime.now().plusDays(days);

    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public Tool getTool() {
        return tool;
    }

    public void finish() {

        this.actualReturnDate = LocalDateTime.now();
        this.status = false;

    }
}
