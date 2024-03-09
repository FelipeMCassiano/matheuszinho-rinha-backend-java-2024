package rinha.backendq1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import rinha.backendq1.models.Costumers;

@Repository
public interface CostumersRepo extends JpaRepository<Costumers, Long> {
    // @Query("SELECT c FROM Costumers c WHERE c.id = :id FOR UPDATE")
    // Optional<Costumers> findClienteById(Long id);

    @Query(value = "SELECT * FROM clientes WHERE id = :id FOR UPDATE", nativeQuery = true)
    Optional<Costumers> findClienteByIdForUpdate(@Param("id") Long id);
}
