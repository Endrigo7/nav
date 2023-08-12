package br.com.nassau.nav.application.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VooDTO {

    private String ciaAerea;

    private String numero;

    private String origem;

    private String destino;

    private LocalDateTime dataHora;

    private BigDecimal valor;

}
