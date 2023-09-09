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

    public List<Voo> listarTodosOsVoos(BuscarVoo buscarVoo){
        List<Voo> voos = new ArrayList<>();


        for (CiaArea ciaArea : ciaAreaService.listarTodos()) {
            List<Voo> voosEncontrados = vooPorCiaAreaService.getVoo(ciaArea);
            voosEncontrados.forEach(System.out::println);

            voosEncontrados.forEach(voo -> voo.setValorNav(voo.getValorCiaArea()
                                                            .multiply(new BigDecimal(valorComissao)
                                                                        .setScale(2, RoundingMode.HALF_EVEN))));

            voos.addAll(voosEncontrados);
        }

        voos.sort(Comparator.comparing(Voo::getValorNav));

        return voos;
    }
}
