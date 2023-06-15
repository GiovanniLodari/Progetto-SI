package com.example.ProgettoSistemiInformativi.services;

import com.example.ProgettoSistemiInformativi.entity.Dipendente;
import com.example.ProgettoSistemiInformativi.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DipendenteService {
    @Autowired
    DipendenteRepository dipendenteRepository;

    public Dipendente creaDipendente(String CF, int codiceBadge,String nome, String cognome, String IBAN, String residenza, String ruolo, Date dataN){
        Dipendente dipendente = new Dipendente();
        dipendente.setCF(CF);
        dipendente.setCodiceBadge(codiceBadge);
        dipendente.setNome(nome);
        dipendente.setCognome(cognome);
        dipendente.setIBAN(IBAN);
        dipendente.setResidenza(residenza);
        dipendente.setRuolo(ruolo);
        dipendente.setDataN(dataN);
        return dipendente;
    }
}
