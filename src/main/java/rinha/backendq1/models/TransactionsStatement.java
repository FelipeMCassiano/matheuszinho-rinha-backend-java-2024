package rinha.backendq1.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionsStatement(
        Integer valor, String tipo, String descricao, @JsonProperty("realizada_em") LocalDateTime realizada_em) {
}
