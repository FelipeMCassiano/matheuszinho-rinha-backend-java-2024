package rinha.backendq1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import rinha.backendq1.models.Costumers;

@Repository
public interface CostumersRepo extends JpaRepository<Costumers, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Costumers> findClienteById(Long id);
}
