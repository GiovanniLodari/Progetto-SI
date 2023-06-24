package com.example.ProgettoSistemiInformativi.controller;

import com.example.ProgettoSistemiInformativi.entity.*;
import com.example.ProgettoSistemiInformativi.services.DipendenteService;
import com.example.ProgettoSistemiInformativi.services.ProdottoInVetrinaService;
import com.example.ProgettoSistemiInformativi.services.PromozioneService;
import com.example.ProgettoSistemiInformativi.services.VenditeProdottoService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class AreaVetrinaController {
    private final ProdottoInVetrinaService prodottoInVetrinaService;
    private final PromozioneService promozioneService;
    private final DipendenteService dipendenteService;
    private final VenditeProdottoService venditeProdottoService;
    @Autowired
    public AreaVetrinaController(ProdottoInVetrinaService prodottoInVetrinaService, PromozioneService promozioneService, DipendenteService dipendenteService, VenditeProdottoService venditeProdottoService) {
        this.prodottoInVetrinaService = prodottoInVetrinaService;
        this.promozioneService = promozioneService;
        this.dipendenteService = dipendenteService;
        this.venditeProdottoService = venditeProdottoService;
    }
    @GetMapping("/areaVetrina")
    public ResponseEntity<String> mostraPagina(@RequestParam("badgeCode") String badgeCode) {
        return ResponseEntity.ok("Area Vetrina richiesta con successo");
    }
    @GetMapping("/cassa")
    public String showCassa() {return "redirect:/cassa";}

    @GetMapping("/getPrice")
    public ResponseEntity<String> gestisciCassa(@RequestParam String barcode, @RequestParam String quantity) {
        ProdottoInVetrina prodotto = prodottoInVetrinaService.getProdottoByCodiceBarre(Integer.parseInt(barcode));
        if(prodotto == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        int prezzo = prodotto.getPrezzo();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String priceJson = objectMapper.writeValueAsString(prezzo);
            return ResponseEntity.ok().body(priceJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<String> savePayment(@RequestBody String items){
        //ricevi parametri
        if(items != null) {
            String[] array = items.split("\"");
            String[] productList = array[3].split("n");
            int totalAmount=0;
            int barCode = 0;
            int quantity = 0;
            List<ProdottoInVetrina> listaProdotti = new ArrayList<>();
            for (int i = 0; i < productList.length; i++) {
                String[] param = productList[i].split(" ");
                barCode = Integer.parseInt(param[0]);
                quantity = Integer.parseInt(param[2]);
                totalAmount+=Integer.parseInt(param[4]);
                ProdottoInVetrina prodotto = prodottoInVetrinaService.getProdottoByCodiceBarre(barCode);
                int attuale = prodotto.getQuantita();
                prodotto.setQuantita(attuale - quantity);
                if ((attuale - quantity) <= prodotto.getMin()) {
                    //Notifiica di sistema, pop-up verso il frontend
                }
                prodottoInVetrinaService.updateProdottoInVetrina(prodotto);
                for(int j=0; j<quantity; j++)
                    listaProdotti.add(prodotto);
            }
            Date date = new Date();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = dateFormat.parse(formattedDate);
                VenditaProdotto  vendita = venditeProdottoService.createVendita(date, items, listaProdotti);
                int prezzoVendita = vendita.getPrezzoTotale();
                if(totalAmount != prezzoVendita) {
                    String confirmationMessage = "{\"message\": \"Sono state riconosciute delle promozioni.\", \"totalAmount\": \""+prezzoVendita+"\"}";
                    return ResponseEntity.ok(confirmationMessage);
                }
                else{
                    String confirmationMessage = "{\"message\": \"Non è stata riconosciuta alcuna promozione.\", \"totalAmount\": \""+prezzoVendita+"\"}";
                    return ResponseEntity.ok(confirmationMessage);
                }
            } catch (ParseException e) {throw new RuntimeException(e);}
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/elencoProdotti")
    @ResponseBody
    public ResponseEntity<String> gestisciElencoProdotti(@RequestParam("badgeCode") String badgeCode) {
        String ruolo = getRuoloByBadgeCode(badgeCode);
        List<ProdottoInVetrina> prodotti = new ArrayList<>();
        if (ruolo.equals("addettoBar")) {
            prodotti = prodottoInVetrinaService.getProdottiByTipo("ProdottoInVetrinaInterno");
        } else if (ruolo.equals("addettoChiosco")) {
            prodotti = prodottoInVetrinaService.getProdottiByTipo("ProdottoInVetrinaDriveIn");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String productsJson = objectMapper.writeValueAsString(prodotti);
           return ResponseEntity.ok().body(productsJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/statistiche")
    public ResponseEntity<String> showGeneralStats(){
        List<VenditaProdotto> vendite = new ArrayList<>();
        vendite = venditeProdottoService.getAll();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String venditeJson = objectMapper.writeValueAsString(vendite);
            return ResponseEntity.ok().body(venditeJson);
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

    @PostMapping("/dettagli-prodotto")
    public ResponseEntity<String> saveProductDetails(@RequestParam String productId, @RequestParam String minThreshold, @RequestParam String maxThreshold){
        try {
            ProdottoInVetrina prodotto = prodottoInVetrinaService.getProdottoById(Integer.parseInt(productId));
            if(minThreshold != "")
                prodotto.setMin(Integer.parseInt(minThreshold));
            if(maxThreshold != "")
                prodotto.setMax(Integer.parseInt(maxThreshold));
            prodottoInVetrinaService.updateProdottoInVetrina(prodotto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/statisticheProdotto")
    public ResponseEntity<String> showProductStats(@RequestParam String productId){
        ProdottoInVetrina prodotto = prodottoInVetrinaService.getProdottoById(Integer.parseInt(productId));
        List<VenditaProdotto> vendite = venditeProdottoService.getVenditeByProdotto(prodotto);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String venditeJson = objectMapper.writeValueAsString(vendite);
            return ResponseEntity.ok().body(venditeJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/elimina-prodotto")
    public ResponseEntity<String> deleteProduct(@RequestParam String productId){
        try {
            ProdottoInVetrina prodotto = prodottoInVetrinaService.getProdottoById(Integer.parseInt(productId));
            prodottoInVetrinaService.deleteProdottoInVetrina(prodotto.getCodiceBarre());
            return ResponseEntity.ok("Prodotto eliminato con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/inserisciProdotto")
    public ModelAndView showInserisciProdottoPage(@RequestParam("badgeCode") String badgeCode) {
        ModelAndView model = new ModelAndView("aggiungiProdotto.html");
        model.addObject("badgeCode", badgeCode);
        return model;
    }

    @PostMapping("/inserisci_prodotto")
    public ResponseEntity<String> addProductDetails(@RequestParam String badgeCode, @RequestParam String barCode, @RequestParam String price, @RequestParam String quantity, @RequestParam String min, @RequestParam String max){
        String ruolo = getRuoloByBadgeCode(badgeCode);
        try{
            if(ruolo == "addettoBar") {
                String dType = "ProdottoInVetrinaInterno";
                ProdottoInVetrinaInterno nuovo = new ProdottoInVetrinaInterno();
                nuovo.setDtype(dType);
                nuovo.setCodiceBarre(Integer.parseInt(barCode));
                nuovo.setPrezzo(Integer.parseInt(price));
                nuovo.setQuantita(Integer.parseInt(quantity));
                nuovo.setMin(Integer.parseInt(min));
                nuovo.setMax(Integer.parseInt(max));
                ProdottoInVetrina prodottoSalvato = prodottoInVetrinaService.salvaProdotto(nuovo);
                if (prodottoSalvato != null) {
                    return ResponseEntity.ok("Prodotto inserito con successo");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'inserimento del prodotto");
                }
            }else {
                String dType = "ProdottoInVetrinaDriveIn";
                ProdottoInVetrinaDriveIn nuovo = new ProdottoInVetrinaDriveIn();
                nuovo.setDtype(dType);
                nuovo.setCodiceBarre(Integer.parseInt(barCode));
                nuovo.setPrezzo(Integer.parseInt(price));
                nuovo.setQuantita(Integer.parseInt(quantity));
                nuovo.setMin(Integer.parseInt(min));
                nuovo.setMax(Integer.parseInt(max));
                ProdottoInVetrina prodottoSalvato = prodottoInVetrinaService.salvaProdotto(nuovo);
                if (prodottoSalvato != null) {
                    return ResponseEntity.ok("Prodotto inserito con successo");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'inserimento del prodotto");
                }
            }
        } catch(Exception e){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'inserimento del prodotto: " + e.getMessage());
        }
    }


    @GetMapping("/elencoPromozioni")
    @ResponseBody
    public ResponseEntity<String> gestisciElencoPromozioni(@RequestParam("badgeCode") String badgeCode) {
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

    @GetMapping("/dettagli-promozione")
    public ResponseEntity<String> getPromosDetails(@RequestParam String promoId) {
        Promozione promo = promozioneService.getPromozioneById(Integer.parseInt(promoId));
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String productsJson = objectMapper.writeValueAsString(promo);
            return ResponseEntity.ok(productsJson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/inserisci_promozione")
    public ModelAndView showInserisciPromoPage(@RequestParam("badgeCode") String badgeCode) {
        ModelAndView model = new ModelAndView("inserisci_promozione.html");
        model.addObject("badgeCode", badgeCode);
        return model;
    }

    @PostMapping("/inserisci_promozione")
    public ResponseEntity<String> savePromotion(@RequestBody String promo) {
        String[] array = promo.split("\"");
        int price = Integer.parseInt(array[3]);
        List<ProdottoInVetrina> productList = new ArrayList<>();
        for (int i = 9; i < array.length; i += 4) {
            ProdottoInVetrina prod = prodottoInVetrinaService.getProdottoByCodiceBarre(Integer.parseInt(array[i]));
            if (prod != null)
                productList.add(prod);
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        Promozione newPromo = new Promozione();
        newPromo.setPrezzo(price);
        newPromo.setListaProdotti(productList);
        List<Promozione> demoPromo = promozioneService.getPromozioni();
        int id =0;
        for(Promozione p : demoPromo){
            if(p.getId()>id) id = p.getId()+1;
        }
        newPromo.setId(id+1);
        Promozione pro = promozioneService.salvaPromozione(newPromo);
        if (pro != null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/elimina-promozione")
    public ResponseEntity<String> deletePromo(@RequestParam String promoId){
        try {
            Promozione promozione = promozioneService.getPromozioneById(Integer.parseInt(promoId));
            promozioneService.deletePromozione(promozione.getId());
            return ResponseEntity.ok("Prodotto eliminato con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/prodottiPrelevati")
    public ModelAndView showFormWithdrawProducts(@RequestParam("badgeCode") String badgeCode) {
        ModelAndView model = new ModelAndView("prodottiPrelevati.html");
        model.addObject("badgeCode", badgeCode);
        return model;
    }

    @PostMapping("/prodottiPrelevati")
    public ResponseEntity<String> saveProdotti(@RequestBody String data) {
        String [] array = data.split("\":\"");
        ProdottoInVetrina prodotto = new ProdottoInVetrina();
        int somma=0;
        for(int i = 1; i<array.length-1; i++){
            String [] aux = array[i].split("\"");
            if(i%2 != 0){
                prodotto = prodottoInVetrinaService.getProdottoByCodiceBarre(Integer.parseInt(aux[0]));
                if(prodotto == null)
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }else{
                somma = prodotto.getQuantita() + Integer.parseInt(aux[0]);
                prodotto.setQuantitaAggiunta(Integer.parseInt(aux[0]));
                if(somma>prodotto.getMax())
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                else
                    prodotto.setQuantita(somma);
            }
        }
        String[] aux2 = array[array.length-1].split("\"");
        int codiceBadge = Integer.parseInt(aux2[0]);
        prodotto.setDipendente(dipendenteService.getDipendenteByBadgeCode(codiceBadge));
        prodottoInVetrinaService.saveProdottoInVetrina(prodotto);
        return ResponseEntity.ok("I prodotti sono stati salvati con successo");
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