package br.com.nassau.nav.infrastructure.repositories;

import br.com.nassau.nav.infrastructure.entities.CiaAreaJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiaAreaRepository extends JpaRepository<CiaAreaJpa, String> {

    List<CiaAreaJpa> findByNome(String nome);

}
