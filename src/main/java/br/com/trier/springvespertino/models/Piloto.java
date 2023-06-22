package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.PilotoDTO;
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
@Entity(name = "piloto")
public class Piloto {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piloto")
    private Integer id;
    
    @Column(name = "nome_piloto")
    private String name;
    
    @ManyToOne
    private Pais pais;
    
    @ManyToOne
    private Equipe equipe;
    
    public Piloto(PilotoDTO dto, Pais pais, Equipe equipe) {
        this(dto.getId(), dto.getName(), pais, equipe);
    }
    
    public PilotoDTO toDto() {
        return new PilotoDTO(id, name, pais.getId(), pais.getName(), equipe.getId(), equipe.getName());
    }   
}
