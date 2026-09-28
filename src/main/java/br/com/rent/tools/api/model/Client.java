package br.com.rent.tools.api.model;

import br.com.rent.tools.api.dto.CreateClientDto;
import br.com.rent.tools.api.dto.UpdateClientDto;
import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_category")
    private CustomerCategory category;

    @Column(nullable = false)
    private Boolean defaulted = false;

    public Client() {}

    public Client(CreateClientDto dto) {
        this.name = dto.name();
        this.cpf = dto.cpf();
        this.category = dto.category();
        this.defaulted = false;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public CustomerCategory getCategory() {
        return category;
    }

    public Boolean getDefaulted() {
        return defaulted;
    }

    public void update(UpdateClientDto dto) {
        if(dto.name() != null) {
            this.name = dto.name();
        }
        if(dto.category() != null) {
            this.category = dto.category();
        }
    }

    public Integer rentLimit() {
        if(this.category == CustomerCategory.REGULAR) {
            return 3;
        } else if (this.category == CustomerCategory.PREMIUM) {
            return 10;
        } else if (this.category == CustomerCategory.BUSINESS) {
            return 50;
        } else {
            return 0;
        }
    }
}
