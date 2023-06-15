package com.example.ProgettoSistemiInformativi.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class FilmInUscita extends Film{
    @Column(name = "DataUscita", nullable = false)
    @Getter
    @Setter
    private Date dataUcita;
}
