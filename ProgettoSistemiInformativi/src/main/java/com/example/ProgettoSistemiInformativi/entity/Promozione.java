package com.example.ProgettoSistemiInformativi.entity;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "promozione")
public class Promozione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter @Setter
    private int Id;

    @Column(name = "prezzo", nullable = false, unique = true)
    @Getter @Setter
    private int prezzo;

    @ManyToMany(mappedBy = "promozioni")
    @Getter @Setter
    private List<ProdottoInVetrina> listaProdotti;
}
