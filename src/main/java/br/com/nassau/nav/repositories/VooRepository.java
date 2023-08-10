package br.com.nassau.nav.repositories;

import br.com.nassau.nav.entities.Voo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VooRepository extends JpaRepository<Voo, String> {
}
