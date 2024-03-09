package rinha.backendq1.models;

import java.time.LocalDateTime;

public record Balance(Integer total, LocalDateTime data_extrato, Integer limite) {

}
