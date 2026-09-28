package br.com.rent.tools.api.model;

import br.com.rent.tools.api.dto.CreateToolDto;
import br.com.rent.tools.api.dto.UpdateToolDto;
import jakarta.persistence.*;
import jakarta.validation.ValidationException;

@Entity
@Table(name = "tools")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    private Integer minimumRentalDays;

    @Enumerated(EnumType.STRING)
    private Available available;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    public Tool() {}

    public Tool(CreateToolDto dto) {
        this.name = dto.name();
        this.price = dto.price();
        this.category = dto.category();
        this.description = dto.description();
        this.minimumRentalDays = dto.minimumRentalDays();
        this.available = Available.AVAILABLE;
        this.condition = dto.condition();
    }

    public void update(UpdateToolDto dto) {
       if(dto.condition() != null) {
           updateCondition(dto.condition());
       }
       if(dto.price() != null) {
           this.price = dto.price();
       }
       if(dto.minimumRentalDays() != null) {
           this.minimumRentalDays = dto.minimumRentalDays();
       }
    }

    private void updateCondition(Condition condition) {
        this.condition = condition;

        if (!condition.isUsable()) {
            this.available = Available.UNAVAILABLE;
        }

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Integer getMinimumRentalDays() {
        return minimumRentalDays;
    }

    public Available getAvailable() {
        return available;
    }

    public Condition getCondition() {
        return condition;
    }

    public void unavailableTool() {

        if(this.available == Available.RESERVED) {
            throw new ValidationException("Ferramenta reservada, espere a devolução");
        } else if (this.available == Available.UNAVAILABLE) {
            throw new ValidationException("Ferramenta já indisponível!");
        }

        this.available = Available.UNAVAILABLE;
    }

    public void availableTool() {

        if(this.available == Available.AVAILABLE) {
            throw new ValidationException("Ferramenta já está disponível!");
        }

        if(!this.condition.isUsable()) {
            throw new ValidationException("Ferramenta danificada!");
        }

        this.available = Available.AVAILABLE;
    }

    public void maintenanceTool() {

        if(this.available == Available.RESERVED) {
            throw new ValidationException("Ferramenta reservada, espere a devolução");
        } else if (this.available == Available.UNDER_MAINTENANCE) {
            throw new ValidationException("Ferramenta já em manutenção!");
        }

        this.available = Available.UNDER_MAINTENANCE;
    }
}
