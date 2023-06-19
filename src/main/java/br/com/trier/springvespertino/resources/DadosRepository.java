package br.com.trier.springvespertino.resources;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trier.springvespertino.models.Dados;
import jakarta.persistence.Id;

public class DadosRepository {

    public interface DiceRepository extends JpaRepository<Dados, Id> {
    }
}
