package com.example.ProgettoSistemiInformativi.repository;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.entity.Promozione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromozioneRepository extends JpaRepository<Promozione, Integer> {
    public List<Promozione> findAll();
    Promozione findById(int id);
    @Query("SELECT p FROM Promozione p WHERE p.listaProdotti IN :listaProdotti")
    List<Promozione> findByListaProdotti(@Param("listaProdotti") List<ProdottoInVetrina> listaProdotti);

}
