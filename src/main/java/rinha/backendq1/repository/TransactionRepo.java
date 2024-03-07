package rinha.backendq1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import rinha.backendq1.models.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    public List<Transaction> findFirst10ByClienteidOrderByIdDesc(Long id);

}
