package com.example.ProgettoSistemiInformativi.controller;

import com.example.ProgettoSistemiInformativi.entity.ProdottoInVetrina;
import com.example.ProgettoSistemiInformativi.entity.Promozione;
import com.example.ProgettoSistemiInformativi.services.ProdottoInVetrinaService;
import com.example.ProgettoSistemiInformativi.services.PromozioneService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AreaVetrinaController {
    private final ProdottoInVetrinaService prodottoInVetrinaService;
    private final PromozioneService promozioneService;
    @Autowired
    public AreaVetrinaController(ProdottoInVetrinaService prodottoInVetrinaService, PromozioneService promozioneService, ObjectMapper objectMapper) {
        this.prodottoInVetrinaService = prodottoInVetrinaService;
        this.promozioneService = promozioneService;
    }
    @GetMapping("/areaVetrina")
    public ResponseEntity<String> mostraPagina(@RequestParam("badgeCode") String badgeCode) {
        return ResponseEntity.ok("Area Vetrina richiesta con successo");
    }
    @PostMapping("/cassa")
    public String gestisciCassa() {
        // Azione da eseguire quando viene premuto il pulsante "Cassa"
        // Esempio:
        return "redirect:/cassa";
    }

    @GetMapping("/elencoProdotti")
    public ResponseEntity<String> gestisciElencoProdotti(@RequestParam("badgeCode") String badgeCode) {
        String ruolo = getRuoloByBadgeCode(badgeCode);
        List<ProdottoInVetrina> prodotti = new ArrayList<>();
        if (ruolo.equals("addettoBar")) {
            prodotti = prodottoInVetrinaService.getProdottiByTipo("ProdottoInVetrinaInterno");
        } else if (ruolo.equals("addettoChiosco")) {
            prodotti = prodottoInVetrinaService.getProdottiByTipo("ProdottoInVetrinaDriveIn");
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String productsJson = objectMapper.writeValueAsString(prodotti);
            return ResponseEntity.ok(productsJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/dettagli-prodotto")
    public ResponseEntity<String> getProductDetails(@RequestParam String productId) {
        ProdottoInVetrina prodotto = prodottoInVetrinaService.getProdottoById(Integer.parseInt(productId));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String productsJson = objectMapper.writeValueAsString(prodotto);
            return ResponseEntity.ok(productsJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/inserisciProdotto")
    public ModelAndView showInserisciProdottoPage(@RequestParam("badgeCode") String badgeCode) {
        ModelAndView model = new ModelAndView("aggiungiProdotto.html");
        model.addObject("badgeCode", badgeCode);
        return model;
    }

    @PostMapping("/inserisciProdotto")
    public ResponseEntity<String> addProductDetails(@RequestParam String badgeCode, @RequestBody ProdottoInVetrina nuovoProdotto){
        String ruolo = getRuoloByBadgeCode(badgeCode);
        String dType = ruolo == "addettoBar" ? "ProdottoInVetrinaInterno" : "ProdottoInVetrinaDriveIn";
        nuovoProdotto.setId(prodottoInVetrinaService.getIdInserisciNuovoProdotto());
        nuovoProdotto.setDtype(dType);
        try {
            ProdottoInVetrina prodottoSalvato = prodottoInVetrinaService.salvaProdotto(nuovoProdotto);
            if (prodottoSalvato != null) {
                return ResponseEntity.ok("Prodotto inserito con successo");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'inserimento del prodotto");
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'inserimento del prodotto: " + e.getMessage());
        }
    }
    @GetMapping("/elencoPromozioni")
    public ResponseEntity<String> gestisciElencoPromozioni() {
        List<Promozione> promozioni = new ArrayList<>();
        promozioni = promozioneService.getPromozioni();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String promoJson = objectMapper.writeValueAsString(promozioni);
            return ResponseEntity.ok(promoJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public String getRuoloByBadgeCode(String badgeCode){
        String ruolo;
        switch (badgeCode) {
            case "001":
                ruolo = "titolare";
                break;
            case "002":
                ruolo = "socio";
                break;
            case "003":
                ruolo = "addettoBar";
                break;
            case "004":
                ruolo = "addettoChiosco";
                break;
            default:
                ruolo = "ruolo_non_trovato";
                break;
        }
        return ruolo;
    }

}