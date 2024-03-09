package rinha.backendq1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionResponse(@JsonProperty("limite") Integer limit, @JsonProperty("saldo") Integer balance) {
}
