package br.com.nassau.nav.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CiaAerea {

    private UUID id;

    private String nome;

    private String endpointListaVoos;

}
