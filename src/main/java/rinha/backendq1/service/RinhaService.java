package rinha.backendq1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import rinha.backendq1.models.Transaction;

import jakarta.transaction.Transactional;
import rinha.backendq1.models.Costumers;
import rinha.backendq1.repository.CostumersRepo;
import rinha.backendq1.repository.TransactionRepo;

@Service
public class RinhaService {

    @Autowired
    private CostumersRepo costumersRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Transactional
    public Optional<Costumers> findById(Long id) {
        return costumersRepo.findClienteById(id);
    };

    @Transactional
    public Optional<Costumers> GetClienteByid(Long id) {
        return costumersRepo.findById(id);
    };

    @Transactional
    public List<Transaction> GetLastTransactions(Long id) {
        return transactionRepo.findFirst10ByClienteidOrderByIdDesc(id);
    }

    @Transactional
    public void CreateTransaction(Transaction transaction, Costumers costumer) {
        transactionRepo.save(transaction);
        costumersRepo.save(costumer);
    }

}
