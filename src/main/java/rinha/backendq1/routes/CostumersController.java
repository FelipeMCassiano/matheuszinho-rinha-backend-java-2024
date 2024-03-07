package rinha.backendq1.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import rinha.backendq1.models.Costumers;
import rinha.backendq1.models.Transaction;
import rinha.backendq1.models.TransactionRequest;
import rinha.backendq1.repository.CostumersRepo;
import rinha.backendq1.repository.TransactionRepo;
import rinha.backendq1.service.RinhaService;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import org.json.JSONObject;

@Controller

public class CostumersController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CostumersRepo costumersRepo;

    @Autowired
    private RinhaService rinhaService;

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/clientes/{id}/extrato")
    public ResponseEntity<Object> bankStatement(@PathVariable Long id) {
        if (id > 5 || id < 1) {
            return ResponseEntity.notFound().build();
        }

        Costumers costumerInfo = rinhaService.findById(id).orElse(null);

        if (costumerInfo == null) {
            return ResponseEntity.notFound().build();
        }

        List<Transaction> transactions = rinhaService.GetLastTransactions(id);

        JSONObject result = new JSONObject();
        JSONObject userInfo = new JSONObject();

        List<JSONObject> transactionsObject = transactions.stream().map(x -> x.toJsonObject()).toList();

        userInfo.put("limite", costumerInfo.GetLimit());
        userInfo.put("data_extrato", LocalDateTime.now().toString());
        userInfo.put("total", costumerInfo.GetBalance());

        result.put("saldo", userInfo);
        result.put("ultimas_transacoes", transactionsObject);

        return ResponseEntity.ok(result.toString());
    }

    @PostMapping("/clientes/{id}/transacoes")
    public ResponseEntity<Object> createTransaction(@PathVariable Long id, @RequestBody TransactionRequest request) {

        if (id > 5) {
            return ResponseEntity.notFound().build();
        }
        if (request.descricao() == null || request.descricao().isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        if (request.tipo() == null || request.tipo().isEmpty()) {

            return ResponseEntity.unprocessableEntity().build();
        }

        if (request.valor() <= 0) {

            return ResponseEntity.unprocessableEntity().build();
        }

        if (request.descricao().length() > 10) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Costumers costumer = rinhaService.findById(id).orElse(null);
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

        costumer.Setbalance(newbalance);

        rinhaService.CreateTransaction(request, costumer, id, newbalance);

        JSONObject result = new JSONObject();

        result.put("limite", limit);
        result.put("saldo", newbalance);

        return ResponseEntity.ok(result.toString());

    }

}
