package com.example.ProgettoSistemiInformativi.services;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.entity.Promozione;
import com.example.ProgettoSistemiInformativi.repository.ProdottoInVetrinaRepository;
import com.example.ProgettoSistemiInformativi.repository.PromozioneRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
    private final PromozioneRepository promozioneRepository;
    @Autowired
    public ProdottoInVetrinaService(ProdottoInVetrinaRepository prodottoInVetrinaRepository, PromozioneRepository promozioneRepository) {
        this.prodottoInVetrinaRepository = prodottoInVetrinaRepository;
        this.promozioneRepository = promozioneRepository;
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
        try {
            ProdottoInVetrina prodotto = prodottoInVetrinaRepository.findByCodiceBarre(codiceBarre);

            List<Promozione> promozioni = prodotto.getPromozioni();
            for (Promozione promozione : promozioni) {
                promozione.getListaProdotti().remove(prodotto);
                if(promozione.getListaProdotti().size() == 0)
                    promozioneRepository.delete(promozione);
                else
                    promozioneRepository.save(promozione);
            }
            prodottoInVetrinaRepository.delete(prodotto);
        } catch (Exception e) {e.printStackTrace();}

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

