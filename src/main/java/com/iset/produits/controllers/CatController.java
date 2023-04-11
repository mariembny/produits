package com.iset.produits.controllers;

import com.iset.produits.entities.Produit;
import com.iset.produits.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class CatController {
    @Autowired
    ProduitService produitService;
    @RequestMapping("/showCreate")
    public String showCreate()
    {
        return "createProduit";
    }
    @RequestMapping("/saveProduit")
    public String saveProduit(@ModelAttribute("produit") Produit produit,
                              @RequestParam("date") String date,
                              ModelMap modelMap) throws ParseException
    {
        //conversion de la date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation = dateformat.parse(String.valueOf(date));
        produit.setDateCreation(dateCreation);
        Produit saveProduit = produitService.saveProduit(produit);
        String msg ="produit enregistré avec Id "+saveProduit.getIdProduit();
        modelMap.addAttribute("msg", msg);
        return "createProduit";
    }
    @RequestMapping("/ListeProduits")
    public String listeProduits(
            ModelMap modelMap,@RequestParam(name = "nom", defaultValue = "") String nom,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size)
    {
        Page<Produit> prods = produitService.getAllProduitsParPage(nom,page, size);
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("nom", nom);
        return "listeProduits";
    }
    @RequestMapping("/supprimerProduit")
    public String supprimerProduit(@RequestParam("id") Long id, ModelMap
            modelMap, @RequestParam(name = "nom", defaultValue = "") String nom,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "2") int size) {
        produitService.deleteProduitById(id);
        Page<Produit> prods = produitService.getAllProduitsParPage(nom,page, size);
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        modelMap.addAttribute("nom", nom);
        return "listeProduits";
    }

    @RequestMapping("/modifierProduit")
    public String editerProduit(@RequestParam("id") Long id,ModelMap modelMap)
    {
        Produit p= produitService.getProduit(id);
        modelMap.addAttribute("produit", p);
        return "editerProduit";
    }
    @RequestMapping("/updateProduit")
    public String updateProduit(@ModelAttribute("produit") Produit produit,@RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "2") int size,
                                @RequestParam("date") String date, @RequestParam(name = "nom", defaultValue = "") String nom,
                                ModelMap modelMap) throws ParseException
    {  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation = dateformat.parse(String.valueOf(date));
        produit.setDateCreation(dateCreation);
        produitService.updateProduit(produit);
        Page<Produit> prods = produitService.getAllProduitsParPage(nom,page, size);
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        modelMap.addAttribute("nom", nom);
        return "listeProduits";
    }
    @RequestMapping("/rechercherProduit")
    public String rechercherProduit(@RequestParam("nom") String nom, ModelMap modelMap,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "2") int size) {
        Page<Produit> prods = produitService.findByNomProduit(nom,PageRequest.of(page, size));
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        modelMap.addAttribute("nom", nom);
        return "listeProduits";
    }
}
