package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.PilotoCorridaDTO;
import br.com.trier.springvespertino.utils.DataUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "piloto_corrida")
public class PilotoCorrida {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piloto_corrida")
    private Integer id;

    @Column(name = "id_colocacao")
    private Integer colocacao;

    @ManyToOne
    private Piloto piloto;

    @ManyToOne
    private Corrida corrida;

    public PilotoCorrida(PilotoCorridaDTO pilotoCorridaDTO, Piloto piloto, Corrida corrida) {
        this(pilotoCorridaDTO.getId(), pilotoCorridaDTO.getColocacao(), piloto, corrida);
    }

    public PilotoCorridaDTO toDTO() {
        return new PilotoCorridaDTO(id, colocacao, piloto.getId(), piloto.getName(), corrida.getId(),
                DataUtils.zoneDateToBrDate(corrida.getDate()));
    }
}