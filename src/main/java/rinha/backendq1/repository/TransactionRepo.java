package rinha.backendq1.repository;

import org.springframework.data.repository.CrudRepository;

import rinha.backendq1.models.Transaction;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {
}
