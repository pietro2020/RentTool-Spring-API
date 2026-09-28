package br.com.rent.tools.api.repository;

import br.com.rent.tools.api.model.Client;
import br.com.rent.tools.api.model.Tool;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByCpf(@NotBlank @Pattern(regexp = "\\d{11}") String cpf);

}
