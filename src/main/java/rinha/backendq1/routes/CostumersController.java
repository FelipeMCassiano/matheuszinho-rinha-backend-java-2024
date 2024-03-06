package rinha.backendq1.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import rinha.backendq1.models.Costumers;
import rinha.backendq1.models.Transaction;
import rinha.backendq1.models.TransactionRequest;
import rinha.backendq1.repository.CostumersRepo;
import rinha.backendq1.repository.TransactionRepo;
import jakarta.persistence.EntityTransaction;

import org.json.JSONObject;

@Controller

public class CostumersController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CostumersRepo costumersRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/clientes/{id}/extrato")
    public ResponseEntity<Object> bankStatement(@PathVariable Long id) {
        if (id > 5 || id < 1) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Costumers costumerInfo = costumersRepo.findById(id).orElse(null);

        if (costumerInfo == null) {
            return ResponseEntity.notFound().build();
        }

        List<Transaction> transactions = transactionRepo.findFirst10ByClienteidOrderByIdDesc(id);

        JSONObject result = new JSONObject();
        JSONObject userInfo = new JSONObject();

        List<JSONObject> transactionsObject = transactions.stream().map(x -> x.toJsonObject()).toList();

        userInfo.put("limite", costumerInfo.GetLimit());
        userInfo.put("data_extrato", LocalDateTime.now().toString());
        userInfo.put("total", costumerInfo.GetBalance());

        result.put("saldo", userInfo);
        result.put("ultimas_transacoes",
                transactionsObject.subList(0, transactionsObject.size() > 10 ? 10 : transactionsObject.size()));

        return ResponseEntity.ok(result.toString());
    }

    @PostMapping("/clientes/{id}/transacoes")
    public ResponseEntity<Object> createTransaction(@PathVariable Long id, @RequestBody TransactionRequest request) {

        EntityTransaction entityTransction = entityManager.getTransaction();

        if (id > 5 || id < 1) {
            System.out.println("pass id");
            return ResponseEntity.unprocessableEntity().build();
        }
        if (request.descricao().isEmpty()) {
            System.out.println("pass desc");
            return ResponseEntity.unprocessableEntity().build();
        }

        if (request.tipo().isEmpty()) {

            System.out.println("pass empty typ");
            return ResponseEntity.unprocessableEntity().build();
        }

        if (request.valor() <= 0) {

            System.out.println("pass negative number");
            return ResponseEntity.unprocessableEntity().build();
        }

        if (request.descricao().length() > 10) {
            System.out.println("pass description too big");
            return ResponseEntity.unprocessableEntity().build();
        }
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM clientes WHERE id = :userId FOR UPDATE",
                Costumers.class);
        nativeQuery.setParameter("userId", id);

        Costumers costumer = (Costumers) nativeQuery.getSingleResult();
        if (costumer == null) {
            return ResponseEntity.notFound().build();
        }
        Integer limit = costumer.GetLimit();
        Integer balance = costumer.GetBalance();

        Integer newbalance = 0;

        switch (request.tipo()) {
            case "c":
                newbalance = balance + request.valor();
                break;
            case "d":
                newbalance = balance - request.valor();
                break;
            default:
                return ResponseEntity.unprocessableEntity().build();
        }

        if ((newbalance + limit) < 0) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Transaction newTransaction = new Transaction();

        newTransaction.SetKind(request.tipo());
        newTransaction.SetValue(request.valor());
        newTransaction.SetDescription(request.descricao());
        newTransaction.SetClientId(id);

        costumer.AddTransaction(newTransaction);

        costumer.Setbalance(newbalance);

        transactionRepo.save(newTransaction);

        costumersRepo.save(costumer);

        entityTransction.commit();

        String jsonString = "{"
                + "\"limite\": " + limit + ","
                + "\"saldo\": " + balance
                + "}";

        return ResponseEntity.ok(jsonString);

    }

}