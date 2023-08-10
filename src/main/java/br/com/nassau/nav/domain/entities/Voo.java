package br.com.nassau.nav.domain.entities;

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
public class Voo {

    private CiaArea ciaAerea;

    private String numero;

    private String origem;

    private String destino;

    private LocalDateTime dataHora;

    private BigDecimal valor;

}
