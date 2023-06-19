package com.example.ProgettoSistemiInformativi.entity;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
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

    @Column(name = "scontrino", nullable = false)
    @Getter @Setter
    private int scontrino;

    @ManyToMany
    @JoinTable(
            name = "listaProdotti",
            joinColumns = @JoinColumn(name = "vendita_id"),
            inverseJoinColumns = @JoinColumn(name = "prodotto_id")
    )
    @Getter @Setter
    private List<ProdottoInVetrina> listaProdotti;
}
