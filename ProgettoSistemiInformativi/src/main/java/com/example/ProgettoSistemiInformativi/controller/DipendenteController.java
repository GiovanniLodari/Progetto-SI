package com.example.ProgettoSistemiInformativi.controller;

import com.example.ProgettoSistemiInformativi.entity.Dipendente;
import com.example.ProgettoSistemiInformativi.services.DipendenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class DipendenteController {
    @Autowired
    DipendenteService dipendenteService;

    @PostMapping(value = "/loginDipendente")
    public ModelAndView register(@RequestParam Map<String,String> mappa, HttpServletResponse res, HttpServletRequest req, Dipendente d){
        HttpSession session=req.getSession();
        session.setAttribute("dipendente", mappa.get("CF"));
        ModelAndView m1 = new ModelAndView("/login");
        dipendenteService.creaDipendente(d.getCF(), d.getCodiceBadge(), d.getNome(), d.getCognome(), d.getIBAN(), d.getResidenza(), d.getRuolo(), d.getDataN());
        return m1;
    }
}
