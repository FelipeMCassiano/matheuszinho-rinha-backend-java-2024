package rinha.backendq1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import rinha.backendq1.models.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.clienteid = :id ORDER BY t.created_at DESC LIMIT 10")
    public List<Transaction> findTransactions(Long id);

}
