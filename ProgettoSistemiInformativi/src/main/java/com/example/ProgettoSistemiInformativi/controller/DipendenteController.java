package com.example.ProgettoSistemiInformativi.controller;

import com.example.ProgettoSistemiInformativi.repository.DipendenteRepository;
import com.example.ProgettoSistemiInformativi.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DipendenteController {
    private final DipendenteRepository dipendenteRepository;
    private final DipendenteService dipendenteService;

    @Autowired
    public DipendenteController(DipendenteRepository repository, DipendenteService service) {
        dipendenteRepository = repository;
        dipendenteService = service;
    }

    @GetMapping("/accessoDipendenti")
    public ModelAndView showLoginForm() {
        ModelAndView modelAndView = new ModelAndView("accessoDipendenti");
        return modelAndView;
    }
    @PostMapping("/accessoDipendenti")
    @ResponseBody
    public ResponseEntity<String> verificaCodiceBadge(@RequestParam("codiceBadge") int codiceBadge) {
        if (dipendenteRepository.existsByCodiceBadge(codiceBadge)) {
            return ResponseEntity.ok("Accesso riuscito");
        } else {
            return ResponseEntity.badRequest().body("Codice badge non valido");
        }
    }

    @GetMapping("/areaPersonale")
    public ModelAndView areaPersonale(@RequestParam("badgeCode") String badgeCode) {
        ModelAndView model = new ModelAndView("areaPersonale");
        String userRole = dipendenteService.getRoleByBadgeCode(badgeCode);
        model.addObject("userRole", userRole);
        return model;
    }
}