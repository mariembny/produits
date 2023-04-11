package com.iset.produits;

import com.iset.produits.entities.Produit;
import com.iset.produits.service.ProduitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class ProduitsApplication implements CommandLineRunner {
    @Autowired
    private ProduitServiceImpl service;
    public static void main(String[] args) {
        SpringApplication.run(ProduitsApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        Produit prod1 = new Produit("PC Asus",1500.500,new Date());
        Produit prod2 = new Produit("PC Dell",2000.500,new Date());
        Produit prod3 = new Produit("PC Toshiba",2500.500,new Date());
        service.saveProduit(prod1);
        service.saveProduit(prod2);
        service.saveProduit(prod3);
    }
}