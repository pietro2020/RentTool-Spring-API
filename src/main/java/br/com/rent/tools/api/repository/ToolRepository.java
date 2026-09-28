package br.com.rent.tools.api.repository;

import br.com.rent.tools.api.model.Available;
import br.com.rent.tools.api.model.Tool;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    boolean existsByNameAndDescription(@NotBlank String name, String description);

    List<Tool> findByNameIgnoreCase(String idOrName);

    boolean existsByIdAndAvailable(Long id, Available available);
}
