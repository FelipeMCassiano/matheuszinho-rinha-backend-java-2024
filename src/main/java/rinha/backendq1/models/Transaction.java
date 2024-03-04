package rinha.backendq1.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transacoes")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "cliente_id")
    private Integer costumers_id;

    // @OneToOne
    // @JoinColumn(name = "id", referencedColumnName = "cliente_id", nullable =
    // false)
    // private Costumers costumers;

    @Column(name = "valor")
    private Integer value;
    @Column(name = "tipo")
    private String kind;

    @Column(name = "descricao")
    private String description;

    @Column(name = "realizada_em")
    private LocalDateTime created_at;

    public Integer GetValue() {
        return value;
    }

    public String GetKind() {
        return kind;
    }

    public String GetDescription() {
        return description;
    }

    public LocalDateTime GetDate() {
        return created_at;
    }

    public void SetValue(Integer val) {
        value = val;
    }

    public void SetKind(String val) {
        kind = val;
    }

    public void SetDescription(String val) {
        description = val;
    }

    public void SetDate(LocalDateTime val) {
        created_at = val;
    }
}
