package com.example.ProgettoSistemiInformativi.repository;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoInVetrinaRepository extends JpaRepository<ProdottoInVetrina, Integer> {
    public List<ProdottoInVetrina> findAll();
    ProdottoInVetrina findById(int id);
    ProdottoInVetrina findByCodiceBarre(int codiceBarre);
    @Query("SELECT p FROM ProdottoInVetrina p WHERE p.dtype = :tipo")
    List<ProdottoInVetrina> findByDtype(String tipo);
}
