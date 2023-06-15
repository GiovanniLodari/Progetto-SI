package com.example.ProgettoSistemiInformativi.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "dipendente")
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CF", nullable = false, length = 16)
    @Getter @Setter
    private String CF;

    @Column(name = "codiceBadge", nullable = false, unique = true, length = 10)
    @Getter @Setter
    private int codiceBadge;

    @Column(name = "nome", nullable = false, length = 10)
    @Getter @Setter
    private String nome;

    @Column(name = "cognome", nullable = false, length = 15)
    @Getter @Setter
    private String cognome;

    @Column(name = "IBAN", nullable = false, length = 27)
    @Getter @Setter
    private String IBAN;

    @Column(name = "residenza", nullable = false, length = 60)
    @Getter @Setter
    private String residenza; //città

    @Column(name = "ruolo", nullable = false, length = 30)
    @Getter @Setter
    private String ruolo;

    @Column(name = "dataNascita", nullable = false, length = 30)
    @Getter @Setter
    private Date dataN;
}

