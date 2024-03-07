package rinha.backendq1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import rinha.backendq1.models.Transaction;
import rinha.backendq1.models.TransactionRequest;
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
    public void CreateTransaction(TransactionRequest request, Costumers costumer, Long id, Integer newbalance) {
        Transaction newTransaction = new Transaction();

        newTransaction.SetKind(request.tipo());
        newTransaction.SetValue(request.valor());
        newTransaction.SetDescription(request.descricao());
        newTransaction.SetClientId(id);

        costumer.Setbalance(newbalance);
        costumer.AddTransaction(newTransaction);

        transactionRepo.save(newTransaction);
        costumersRepo.save(costumer);
    }

}
