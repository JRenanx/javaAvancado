package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.models.Piloto;
import br.com.trier.springvespertino.models.PilotoCorrida;

@Repository
public interface PilotoCorridaRepository extends JpaRepository<PilotoCorrida, Integer> {
    List<PilotoCorrida> findByPlacement(Integer colocacao);

    List<PilotoCorrida> findByPilotoOrderByColocacao(Piloto piloto);

    List<PilotoCorrida> findByCorridaOrderByColocacao(Corrida Corrida);

}
