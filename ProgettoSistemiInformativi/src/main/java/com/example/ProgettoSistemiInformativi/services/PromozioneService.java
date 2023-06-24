package com.example.ProgettoSistemiInformativi.services;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.entity.Promozione;
import com.example.ProgettoSistemiInformativi.repository.ProdottoInVetrinaRepository;
import com.example.ProgettoSistemiInformativi.repository.PromozioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromozioneService {
    private final PromozioneRepository promozioneRepository;
    private final ProdottoInVetrinaRepository prodottoInVetrinaRepository;
    @Autowired
    public PromozioneService(PromozioneRepository promozioneRepository, ProdottoInVetrinaRepository prodottoInVetrinaRepository) {
        this.promozioneRepository = promozioneRepository;
        this.prodottoInVetrinaRepository = prodottoInVetrinaRepository;
    }
    public Promozione createPromo(int id, int prezzo, List<ProdottoInVetrina> listaProdotti){
        Promozione promozione = new Promozione();
        promozione.setId(id);
        promozione.setPrezzo(prezzo);
        promozione.setListaProdotti(listaProdotti);
        return promozioneRepository.save(promozione);
    }

    public void deletePromozione(int id){
        Promozione promozione = promozioneRepository.findById(id);
        if(promozione != null) {
            try {
                List<ProdottoInVetrina> productList = promozione.getListaProdotti();
                for (ProdottoInVetrina prodotto : productList) {
                    prodotto.getPromozioni().remove(promozione);
                }
                prodottoInVetrinaRepository.saveAll(productList);
                promozioneRepository.delete(promozione);
            } catch (Exception e) {e.printStackTrace(); }
        }
    }
    public List<Promozione> getPromozioni(){
        List<Promozione> listaPromo = promozioneRepository.findAll();
        return listaPromo;
    }
    public Promozione getPromozioneById(int id){
        return promozioneRepository.findById(id);
    }
    /*
    public Promozione salvaPromozione(Promozione promozione){
        return promozioneRepository.save(promozione);
    }
     */

    public Promozione salvaPromozione(Promozione promozione) {
        try {
            Promozione savedPromo = promozioneRepository.save(promozione);
            List<ProdottoInVetrina> productList = promozione.getListaProdotti();
            for (ProdottoInVetrina prodotto : productList) {
                prodotto.getPromozioni().add(savedPromo);
                prodottoInVetrinaRepository.save(prodotto);
            }
            return savedPromo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    public Promozione findByListaProdotti(List<ProdottoInVetrina> listaProdotti){
        List<Promozione> totalePromozioni = promozioneRepository.findAll();
        Promozione trovata = null;
        for(int i=0; i<totalePromozioni.size(); i++) {
            Promozione promozione = totalePromozioni.get(i);
            int counter = promozione.getListaProdotti().size();
            for (int j = 0; j < promozione.getListaProdotti().size(); j++) {
                if (listaProdotti.contains(promozione.getListaProdotti().get(j).getId()))
                    counter--;
            }
            if (counter == 0) {
                trovata = promozione;
                break;
            }
        }
        return trovata;
    }*/
}
