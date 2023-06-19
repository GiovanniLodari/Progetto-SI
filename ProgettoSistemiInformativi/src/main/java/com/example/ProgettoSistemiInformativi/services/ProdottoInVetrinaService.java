package com.example.ProgettoSistemiInformativi.services;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.repository.ProdottoInVetrinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ProdottoInVetrinaService{
    private final ProdottoInVetrinaRepository prodottoInVetrinaRepository;
    @Autowired
    public ProdottoInVetrinaService(ProdottoInVetrinaRepository prodottoInVetrinaRepository) {
        this.prodottoInVetrinaRepository = prodottoInVetrinaRepository;
    }

    public ProdottoInVetrina getProdottoById(int id){
        return prodottoInVetrinaRepository.findById(id);
    }
    public ProdottoInVetrina getProdottoByCodiceBarre(int codiceBarre) {
        return prodottoInVetrinaRepository.findByCodiceBarre(codiceBarre);
    }
    public ProdottoInVetrina saveProdottoInVetrina(ProdottoInVetrina prodottoInVetrina) {
        return prodottoInVetrinaRepository.save(prodottoInVetrina);
    }
    public ProdottoInVetrina updateProdottoInVetrina(ProdottoInVetrina prodottoInVetrina) {
        return prodottoInVetrinaRepository.save(prodottoInVetrina);
    }
    public void deleteProdottoInVetrina(int codiceBarre) {
        ProdottoInVetrina prodottoInVetrina = prodottoInVetrinaRepository.findByCodiceBarre(codiceBarre);
        if (prodottoInVetrina != null) {
            prodottoInVetrinaRepository.delete(prodottoInVetrina);
        }
    }
    public List<ProdottoInVetrina> getProdottiByTipo(String tipo) {
        return prodottoInVetrinaRepository.findByDtype(tipo);
    }

    public ProdottoInVetrina salvaProdotto(ProdottoInVetrina prodotto){
        ProdottoInVetrina product = prodottoInVetrinaRepository.save(prodotto);
        return product;
    }

    public int getIdInserisciNuovoProdotto(){
        List<ProdottoInVetrina> lista = prodottoInVetrinaRepository.findAll();
        return (1+lista.size());
    }
}

