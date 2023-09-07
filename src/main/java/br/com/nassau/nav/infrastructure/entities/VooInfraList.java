package br.com.nassau.nav.infrastructure.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VooInfraList {

    private List<VooInfra> voos;

    public VooInfraList(){
        voos = new ArrayList<>();
    }

}
