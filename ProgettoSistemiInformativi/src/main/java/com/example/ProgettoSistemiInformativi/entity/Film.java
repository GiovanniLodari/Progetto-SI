package com.example.ProgettoSistemiInformativi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter @Setter
    private int id;

    @Column(name = "Nome", nullable = false, length = 50)
    @Getter @Setter
    private String Nome;

    @Column(name = "CasaProduttrice", nullable = false, length = 15)
    @Getter @Setter
    private String casaProduttrice;

    @Column(name = "Trama", nullable = false, length = 255)
    @Getter @Setter
    private String trama;

    @Column(name = "categoria", nullable = false, length = 20)
    @Getter @Setter
    private String categoria;

    @Column(name = "locandina", nullable = false, length = 255)
    @Getter @Setter
    private String locandina; //va messo l'URL della posizione nel pc

    @Column(name = "Prezzo", nullable = false)
    @Getter @Setter
    private int prezzo;

    @Column(name = "Agente", nullable = false, length = 30)
    @Getter @Setter
    private String agente; //forse conviene fare una classe Agente

}
