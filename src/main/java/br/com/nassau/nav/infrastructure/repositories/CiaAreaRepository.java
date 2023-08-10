package br.com.nassau.nav.infrastructure.repositories;

import br.com.nassau.nav.infrastructure.entities.CiaAreaJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CiaAreaRepository extends JpaRepository<CiaAreaJpa, UUID> {
}
