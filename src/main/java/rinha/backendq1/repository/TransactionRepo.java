package rinha.backendq1.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import rinha.backendq1.models.Transaction;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {
    public List<Transaction> findFirst10ByClienteidOrderByIdDesc(Long id);

}
