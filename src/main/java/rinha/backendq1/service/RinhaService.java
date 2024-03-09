package rinha.backendq1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity;

import java.time.LocalDateTime;
import java.util.List;
import rinha.backendq1.models.Transaction;
import rinha.backendq1.models.TransactionRequest;
import rinha.backendq1.models.TransactionResponse;
import rinha.backendq1.models.TransactionsStatement;
import jakarta.transaction.Transactional;
import rinha.backendq1.exception.UnprocessableEntityException;
import rinha.backendq1.models.Balance;
import rinha.backendq1.models.BankStatement;
import rinha.backendq1.models.Costumers;
import rinha.backendq1.repository.CostumersRepo;
import rinha.backendq1.repository.TransactionRepo;

@Service
public class RinhaService {

    @Autowired
    private CostumersRepo costumersRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    // @Transactional
    // public Optional<Costumers> findById(Long id) {
    // return costumersRepo.findClienteByIdForUpdate(id);
    // };

    @Transactional
    public BankStatement GetBankStatement(Long id) {
        List<TransactionsStatement> Ltransactions = transactionRepo.findTransactions(id).stream().map(
                t -> new TransactionsStatement(t.GetValue(), t.GetKind(), t.GetDescription(), t.GetDate()))
                .toList();
        Costumers costumers = costumersRepo.findById(id).orElse(null);

        Balance balance = new Balance(costumers.GetBalance(), LocalDateTime.now(), costumers.GetLimit());

        BankStatement bStatement = new BankStatement(balance, Ltransactions);
        System.err.println(Ltransactions);
        return bStatement;

    }

    @Transactional
    public TransactionResponse CreateTransaction(TransactionRequest request, Long id) {

        Transaction newTransaction = new Transaction();
        Costumers costumer = costumersRepo.findClienteByIdForUpdate(id).orElse(null);
        if (costumer == null) {
            throw new UnprocessableEntityException("");
        }
        Integer limit = costumer.GetLimit();
        Integer balance = costumer.GetBalance();

        Integer newbalance = 0;

        switch (request.tipo()) {
            case "c":
                newbalance = balance + request.valor().intValue();
                break;
            case "d":
                newbalance = balance - request.valor().intValue();
                break;
            default:
                throw new UnprocessableEntityException("");
        }

        if ((newbalance + limit) < 0) {
            throw new UnprocessableEntityException("");
        }
        TransactionResponse transactionResponse = new TransactionResponse(limit, newbalance);

        newTransaction.SetKind(request.tipo());
        newTransaction.SetValue(request.valor().intValue());
        newTransaction.SetDescription(request.descricao());
        newTransaction.SetClientId(id);

        costumer.Setbalance(newbalance);
        costumer.AddTransaction(newTransaction);

        transactionRepo.save(newTransaction);
        costumersRepo.save(costumer);

        return transactionResponse;
    }

}
