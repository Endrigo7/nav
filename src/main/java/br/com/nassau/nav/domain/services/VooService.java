package br.com.nassau.nav.domain.services;

import br.com.nassau.nav.domain.entities.BuscarVoo;
import br.com.nassau.nav.domain.entities.CiaArea;
import br.com.nassau.nav.domain.entities.Voo;
import br.com.nassau.nav.infrastructure.services.VooPorCiaAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class VooService {

    @Autowired
    private CiaAreaService ciaAreaService;

    @Autowired
    private VooPorCiaAreaService vooPorCiaAreaService;

    @Value("${valor.comissao:1.2}")
    private String valorComissao;

    /*
     * este é um metodo temporario até a chamada aos
     * outros microservices estarem prontas
     */

    private String builderCodigoVoo(CiaArea ciaArea){
        String codigoCiaArea = ciaArea.getNome().substring(0, 2);
        String numeroVoo = String.format("%04d", new Random().nextInt(9999));
        return codigoCiaArea + "-" + numeroVoo;
    }

    private Voo builderVoo(CiaArea ciaArea, BuscarVoo buscarVoo){
        BigDecimal valorVoo = new BigDecimal("100.10").setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal valorComissao = new BigDecimal("1.2").setScale(2, RoundingMode.HALF_EVEN);

        return Voo.builder()
                .ciaAerea(ciaArea)
                .numero(builderCodigoVoo(ciaArea))
                .origem(buscarVoo.getOrigem())
                .destino(buscarVoo.getDestino())
                .valorCiaArea(valorVoo)
                .valorNav(valorVoo.multiply(valorComissao))
                .build();
    }

    public List<Voo> listarTodosOsVoos(BuscarVoo buscarVoo){
        List<Voo> voos = new ArrayList<>();


        for (CiaArea ciaArea : ciaAreaService.listarTodos()) {
            List<Voo> voosEncontrados = vooPorCiaAreaService.getVoo(ciaArea);

            voosEncontrados.forEach(voo -> voo.setValorNav(voo.getValorCiaArea()
                                                            .multiply(new BigDecimal(valorComissao)
                                                                        .setScale(2, RoundingMode.HALF_EVEN))));

            voos.addAll(voosEncontrados);
        }

        voos.sort(Comparator.comparing(Voo::getValorNav));

        return voos;
    }
}
