package rinha.backendq1.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Costumers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "limite")
    private Integer limit;

    @Column(name = "saldo")
    private Integer balance;

    @OneToMany(mappedBy = "clienteid", orphanRemoval = true)
    @OrderBy("realizada_em DESC")
    private List<Transaction> transactions;

    public void AddTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Integer GetId() {
        return id;

    }

    public Integer GetLimit() {
        return limit;
    }

    public void Setbalance(Integer value) {
        balance = value;

    }

    public Integer GetBalance() {
        return balance;
    }

    public List<Transaction> GetTransactions() {
        return transactions;
    }

}
