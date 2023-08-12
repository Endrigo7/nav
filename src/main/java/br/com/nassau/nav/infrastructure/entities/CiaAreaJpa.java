package br.com.nassau.nav.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "CIA_AREA")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CiaAreaJpa {
    @Id
    private String id;

    @Column
    private String nome;

    @Column
    private String endpointListarVoos;

}
