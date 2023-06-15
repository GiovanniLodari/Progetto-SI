package com.example.ProgettoSistemiInformativi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class FilmInProgrammazione extends FilmInUscita{
    @ManyToOne
    @JoinColumn(name = "film_in_uscita_id")
    @Column(name = "data", nullable = false)
    @Getter @Setter
    private Date data;

    @ManyToOne
    @JoinColumn(name = "film_in_uscita_id")
    @Column(name = "ora", nullable = false)
    @Getter @Setter
    private Date ora; //forse conviene rappresentarlo come int

    @Column(name = "DataFineProgrammazione", nullable = false)
    @Getter @Setter
    private Date dataFineProgrammazione;

    @Column(name = "capienzaResidua", nullable = false)
    @Getter @Setter
    private int capienzaResidua;
}
