package br.com.nassau.nav.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "VOO")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Voo {

    @Id
    private String numero;

    @Column
    private String origem;

    @Column
    private String destino;

    @Column
    private LocalDateTime dataHora;

    @Column
    private BigDecimal valor;

}
