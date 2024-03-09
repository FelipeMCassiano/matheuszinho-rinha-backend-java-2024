package rinha.backendq1.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BankStatement(@JsonProperty("saldo") Balance balance,
        @JsonProperty("ultimas_transacoes") List<TransactionsStatement> last_transactions) {
}
