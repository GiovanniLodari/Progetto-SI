package com.example.ProgettoSistemiInformativi.entity;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vendita_prodotto")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class VenditaProdotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter@Setter
    private int Id;

    @Column(name = "data", nullable = false)
    @Getter @Setter
    private Date data;

    @Column(name = "prezzoTotale", nullable = false)
    @Getter @Setter
    private int prezzoTotale;

    @Column(name = "scontrino", nullable = false, length = 255)
    @Getter @Setter
    private String scontrino;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "lista_prodotti",
            joinColumns = @JoinColumn(name = "vendita_id"),
            inverseJoinColumns = @JoinColumn(name = "prodotto_id")
    )
    @JsonIgnore
    @Getter @Setter
    private List<ProdottoInVetrina> listaProdotti;
}
