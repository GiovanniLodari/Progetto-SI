package com.example.ProgettoSistemiInformativi.services;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.entity.Promozione;
import com.example.ProgettoSistemiInformativi.repository.PromozioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromozioneService {
    private final PromozioneRepository promozioneRepository;
    @Autowired
    public PromozioneService(PromozioneRepository promozioneRepository) {
        this.promozioneRepository = promozioneRepository;
    }
    public Promozione createPromo(int id, int prezzo, List<ProdottoInVetrina> listaProdotti){
        Promozione promozione = new Promozione();
        promozione.setId(id);
        promozione.setPrezzo(prezzo);
        promozione.setListaProdotti(listaProdotti);
        return promozioneRepository.save(promozione);
    }

    public void deletePromozione(int id){
        Promozione promo = promozioneRepository.findById(id);
        if(promo != null)
            promozioneRepository.delete(promo);
    }
    public List<Promozione> getPromozioni(){
        List<Promozione> listaPromo = promozioneRepository.findAll();
        return listaPromo;
    }
    public Promozione getPromozioneById(int id){
        return promozioneRepository.findById(id);
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
