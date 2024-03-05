package rinha.backendq1.routes;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rinha.backendq1.models.Costumers;
import rinha.backendq1.models.Transaction;
import rinha.backendq1.models.TransactionRequest;
import rinha.backendq1.repository.CostumersRepo;
import rinha.backendq1.repository.TransactionRepo;

@Controller
public class BankStatement {
    @Autowired
    private CostumersRepo costumersRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/clientes/{id}/extrato")
    public ResponseEntity<Object> bankStatement(@PathVariable Long id) {
        if (id > 5 || id < 1) {
            return ResponseEntity.unprocessableEntity().build();
        }

        System.out.println(transactionRepo.findByClienteidOrderByIdAsc(id));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/clientes/{id}/transacoes")
    public ResponseEntity<Object> createTransaction(@PathVariable Long id, @RequestBody TransactionRequest request) {
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
        Optional<Costumers> costumerR = costumersRepo.findById(id);
        if (!costumerR.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Costumers costumer = costumerR.get();
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

        String jsonString = "{"
                + "\"limite\": " + limit + ","
                + "\"saldo\": " + balance
                + "}";

        return ResponseEntity.ok(jsonString);

    }

}
