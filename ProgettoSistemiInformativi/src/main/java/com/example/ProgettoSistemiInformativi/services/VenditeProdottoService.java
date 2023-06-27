package com.example.ProgettoSistemiInformativi.services;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.entity.Promozione;
import com.example.ProgettoSistemiInformativi.entity.VenditaProdotto;
import com.example.ProgettoSistemiInformativi.repository.PromozioneRepository;
import com.example.ProgettoSistemiInformativi.repository.VenditaProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VenditeProdottoService {
    private final VenditaProdottoRepository venditaProdottoRepository;

    private PromozioneRepository promozioneRepository;


    @Autowired
    public VenditeProdottoService(VenditaProdottoRepository venditaProdottoRepository, PromozioneRepository promozioneRepository){
        this.venditaProdottoRepository = venditaProdottoRepository;
        this.promozioneRepository = promozioneRepository;
    }

    public VenditaProdotto createVendita(Date data, String scontrino, List<ProdottoInVetrina> listaProdotti) {
        List<Promozione> promozioni = promozioneRepository.findAll();
        VenditaProdotto venditaProdotto = new VenditaProdotto();
        venditaProdotto.setListaProdotti(listaProdotti);
        int prezzo = 0;

        for (Promozione promozione : promozioni) {
            List<ProdottoInVetrina> prodottiPromozione = promozione.getListaProdotti();
            boolean isPromozioneApplicabile = true;
            for (ProdottoInVetrina prodottoPromozione : prodottiPromozione) {
                if (!listaProdotti.contains(prodottoPromozione)) {
                    isPromozioneApplicabile = false;
                    break;
                }
            }

            if (isPromozioneApplicabile) {
                for(ProdottoInVetrina prodotto : prodottiPromozione) {
                    listaProdotti.remove(prodotto);
                }
                prezzo = prezzo + promozione.getPrezzo();
            }
        }
        for (ProdottoInVetrina prodotto : listaProdotti) {
            prezzo = prezzo + prodotto.getPrezzo();
        }

        venditaProdotto.setData(data);
        venditaProdotto.setScontrino(scontrino);
        venditaProdotto.setPrezzoTotale(prezzo);

        return venditaProdottoRepository.save(venditaProdotto);
    }

    public List<VenditaProdotto> getAll(){
        return venditaProdottoRepository.findAll();
    }

    public List<VenditaProdotto> getVenditeByProdotto(ProdottoInVetrina prodotto) {
        List<VenditaProdotto> all = venditaProdottoRepository.findAll();
        List<VenditaProdotto> res = new ArrayList<>();
        for(VenditaProdotto vendita : all){
            List<ProdottoInVetrina> prodotti = vendita.getListaProdotti();
            if(prodotti.contains(prodotto)){
                res.add(vendita);
            }
        }
        return res;
    }
}
