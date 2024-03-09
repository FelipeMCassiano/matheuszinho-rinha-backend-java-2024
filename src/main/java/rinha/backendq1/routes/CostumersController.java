package rinha.backendq1.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.persistence.EntityManager;

import rinha.backendq1.models.BankStatement;
import rinha.backendq1.models.TransactionRequest;
import rinha.backendq1.models.TransactionResponse;
import rinha.backendq1.service.RinhaService;
import jakarta.persistence.PersistenceContext;

@Controller
public class CostumersController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RinhaService rinhaService;

    @GetMapping("/clientes/{id}/extrato")
    public ResponseEntity<Object> bankStatement(@PathVariable Long id) {
        if (id > 5 || id < 1) {
            return ResponseEntity.notFound().build();
        }

        BankStatement bStatement = rinhaService.GetBankStatement(id);

        return ResponseEntity.ok(bStatement);
    }

    public boolean isDoubleInt(double d) {
        double TOLERANCE = 1E-5;
        return Math.abs(Math.floor(d) - d) < TOLERANCE;
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

        if (!isDoubleInt(request.valor())) {

            return ResponseEntity.unprocessableEntity().build();
        }

        if (request.descricao().length() > 10) {
            return ResponseEntity.unprocessableEntity().build();
        }

        TransactionResponse trasanctionResponse = rinhaService.CreateTransaction(request, id);

        return ResponseEntity.ok(trasanctionResponse);

    }

}
