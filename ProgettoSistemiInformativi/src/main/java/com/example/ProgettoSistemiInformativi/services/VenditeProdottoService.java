package com.example.ProgettoSistemiInformativi.services;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.entity.Promozione;
import com.example.ProgettoSistemiInformativi.entity.VenditaProdotto;
import com.example.ProgettoSistemiInformativi.repository.PromozioneRepository;
import com.example.ProgettoSistemiInformativi.repository.VenditaProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VenditeProdottoService {
    private final VenditaProdottoRepository venditaProdottoRepository;
    @Autowired
    private PromozioneRepository promozioneRepository;
    @Autowired
    public VenditeProdottoService(VenditaProdottoRepository venditaProdottoRepository){
        this.venditaProdottoRepository = venditaProdottoRepository;
    }

    private VenditaProdotto createVendita(int id, Date data, int scontrino, List<ProdottoInVetrina> listaProdotti){
        Promozione promozione = promozioneRepository.findByListaProdotti(listaProdotti);
        VenditaProdotto venditaProdotto = new VenditaProdotto();
        venditaProdotto.setListaProdotti(listaProdotti);
        int prezzo = 0;
        while(promozione != null) {
            prezzo += promozione.getPrezzo();
            for (int i = 0; i < promozione.getListaProdotti().size(); i++) {
                listaProdotti.remove(promozione.getListaProdotti().get(i));
            }
            promozione = promozioneRepository.findByListaProdotti(listaProdotti);
        }
        for(int j=0; j<listaProdotti.size(); j++)
            prezzo += listaProdotti.get(j).getPrezzo();
        venditaProdotto.setId(id);
        venditaProdotto.setData(data);
        venditaProdotto.setScontrino(scontrino);
        return venditaProdottoRepository.save(venditaProdotto);
    }

}
