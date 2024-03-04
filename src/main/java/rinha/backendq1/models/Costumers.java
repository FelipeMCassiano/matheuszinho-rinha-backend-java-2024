package rinha.backendq1.models;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    @OneToMany(targetEntity = Transaction.class)
    @JoinColumn(name = "cliente_id")
    private List<Transaction> transactions;

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
