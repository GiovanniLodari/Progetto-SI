package com.example.ProgettoSistemiInformativi.repository;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.entity.VenditaProdotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VenditaProdottoRepository extends JpaRepository<VenditaProdotto, Integer> {
    public List<VenditaProdotto> findAll();
    VenditaProdotto findByData(Date data);
    VenditaProdotto findById(int id);
}
