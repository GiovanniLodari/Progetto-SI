package com.example.ProgettoSistemiInformativi.repository;

import com.example.ProgettoSistemiInformativi.entity.Dipendente;
import com.example.ProgettoSistemiInformativi.services.DipendenteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, String> {
    //metodi che vengono utilizzati nel service quando deve fare operazioni sulla tabella.
    public boolean existsByCF(String CF);
    public Dipendente findByCodiceBadge(int codiceBadge);
    public Dipendente findByRuolo(String ruolo);
}
