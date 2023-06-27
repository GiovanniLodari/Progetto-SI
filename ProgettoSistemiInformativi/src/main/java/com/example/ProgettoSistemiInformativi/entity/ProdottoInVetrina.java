package com.example.ProgettoSistemiInformativi.entity;

import com.example.ProgettoSistemiInformativi.services.PromozioneService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "prodottoInVetrina")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ProdottoInVetrina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter @Setter
    private int Id;

    @Column(name = "codiceBarre", nullable = false, unique = true)
    @Getter @Setter
    private int codiceBarre;

    @Column(name = "prezzo", nullable = false)
    @Getter @Setter
    private int prezzo;

    @Column(name = "quantità", nullable = false)
    @Getter @Setter
    private int quantita;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    @Getter @Setter
    private Dipendente dipendente;


    @Column(name = "quantitaAggiunta")
    @Getter @Setter
    private Integer quantitaAggiunta;

    @Column(name = "max", nullable = false)
    @Getter @Setter
    private int max;

    @Column(name = "min", nullable = true)
    @Getter @Setter
    private int min;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "listaPromozioni",
            joinColumns = @JoinColumn(name = "prodotto_id"),
            inverseJoinColumns = @JoinColumn(name = "promozione_id")
    )
    @JsonIgnore
    @Getter @Setter
    private List<Promozione> promozioni;

    @ManyToMany(mappedBy = "listaProdotti")
    @Getter @Setter
    private List<VenditaProdotto> listaVendite;

    @Column(insertable = false, updatable = false)
    @Getter @Setter
    private String dtype;
    public void setSoglie(int inf, int sup){
        this.setMax(sup);
        this.setMin(inf);
    }
}
