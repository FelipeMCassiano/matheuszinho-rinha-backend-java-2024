package rinha.backendq1.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import rinha.backendq1.repository.CostumersRepo;

@Controller
public class BankStatement {
    @Autowired
    private CostumersRepo costumersRepo;

    @GetMapping("/clientes/{id}/extrato")
    public ResponseEntity bankStatement(@PathVariable Long id) {
        if (id > 5 || id < 1) {
            return ResponseEntity.unprocessableEntity().build();
        }

        System.out.println(costumersRepo.findById(id));
        return ResponseEntity.ok().build();
    }

}
