package br.com.nassau.nav.infrastructure.repositories;

import br.com.nassau.nav.infrastructure.entities.CiaAereaJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiaAereaRepository extends JpaRepository<CiaAereaJpa, String> {

    List<CiaAereaJpa> findByNome(String nome);

}
