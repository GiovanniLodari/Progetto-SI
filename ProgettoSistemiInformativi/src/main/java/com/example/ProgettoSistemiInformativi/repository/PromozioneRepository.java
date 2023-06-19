package com.example.ProgettoSistemiInformativi.repository;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.entity.Promozione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromozioneRepository extends JpaRepository<Promozione, Integer> {
    public List<Promozione> findAll();
    Promozione findById(int id);

    @Query("SELECT p FROM Promozione p WHERE p.listaProdotti = :listaProdotti")
    Promozione findByListaProdotti(@Param("listaProdotti") List<ProdottoInVetrina> listaProdotti);
}
