package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.PilotoCorridaDTO;
import br.com.trier.springvespertino.utils.DataUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "piloto_corrida")
public class PilotoCorrida {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piloto_corrida")
    private Integer id;

    @ManyToOne
    @NotNull
    private Piloto piloto;

    @ManyToOne
    @NotNull
    private Corrida corrida;

    @Column(name = "posicao_piloto")
    private Integer position;

    public PilotoCorrida(PilotoCorridaDTO racePilotDTO, Piloto piloto, Corrida corrida) {
        this(racePilotDTO.getId(), piloto, corrida, racePilotDTO.getPosition());
    }

    public PilotoCorridaDTO toDTO() {
        return new PilotoCorridaDTO(id, piloto.getId(), piloto.getName(), corrida.getId(),
                DataUtils.zoneDateToBrDate(corrida.getDate()), position);
    }

}