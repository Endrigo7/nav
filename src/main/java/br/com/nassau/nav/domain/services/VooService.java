package br.com.nassau.nav.domain.services;

import br.com.nassau.nav.domain.entities.BuscarVoo;
import br.com.nassau.nav.domain.entities.CiaAerea;
import br.com.nassau.nav.domain.entities.Voo;
import br.com.nassau.nav.infrastructure.services.VooPorCiaAereaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class VooService {

    @Autowired
    private CiaAereaService ciaAereaService;

    @Autowired
    private VooPorCiaAereaService vooPorCiaAereaService;

    @Value("${valor.comissao:1.2}")
    private String valorComissao;

    public List<Voo> listarTodosOsVoos(BuscarVoo buscarVoo){
        List<Voo> voos = new ArrayList<>();


        for (CiaAerea ciaAerea : ciaAereaService.listarTodos()) {
            List<Voo> voosEncontrados = vooPorCiaAereaService.getVoo(ciaAerea);
            voosEncontrados.forEach(System.out::println);

            voosEncontrados.forEach(voo -> voo.setValorNav(voo.getValorCiaAerea()
                                                            .multiply(new BigDecimal(valorComissao)
                                                                        .setScale(2, RoundingMode.HALF_EVEN))));

            voos.addAll(voosEncontrados);
        }

        voos.sort(Comparator.comparing(Voo::getValorNav));

        return voos;
    }
}
