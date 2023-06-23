package br.com.trier.springvespertino.service;

import java.util.List;

import br.com.trier.springvespertino.models.Corrida;
import br.com.trier.springvespertino.models.Piloto;
import br.com.trier.springvespertino.models.PilotoCorrida;

public interface PilotoCorridaService {

    PilotoCorrida findById(Integer id);

    PilotoCorrida insert(PilotoCorrida pilotoCorrida);

    PilotoCorrida update(PilotoCorrida pilotoCorrida);

    void delete(Integer id);

    List<PilotoCorrida> listAll();

    List<PilotoCorrida> findByPilotoOrderByPosition(Piloto piloto);

    List<PilotoCorrida> findByCorridaOrderByPosition(Corrida corrida);

    List<PilotoCorrida> findByPosition(Integer position);

}
