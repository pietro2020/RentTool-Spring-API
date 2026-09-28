package br.com.rent.tools.api.repository;

import br.com.rent.tools.api.model.Client;
import br.com.rent.tools.api.model.Rent;
import br.com.rent.tools.api.model.Tool;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {

    boolean existsByToolAndStatusTrue(@NotNull Tool tool);

    @Query("""
    SELECT COUNT(r) >= :limit
    FROM Rent r
    WHERE r.client = :client
    AND r.status = true
    """)
    boolean hasMoreEqualsThanRents(Client client, int limit);

    List<Rent> findAllByStatus(Boolean status);
}
