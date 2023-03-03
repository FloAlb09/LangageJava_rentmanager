package com.epf.rentmanager.modele;

//Importation
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

//Classe
public class Client {
    //attributs
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;

    //constructeurs
        //vide
    public Client(){
        this.id = 0L;
        this.nom = "Albertoli";
        this.prenom = "Florane";
        this.email = "florane.albertoli@epfedu.fr";
        this.naissance = LocalDate.of(2001, Month.NOVEMBER, 9);
    }
        //par defaut
    public Client(Long id, String nom, String prenom, String email, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }
        //sans id
    public Client(String nom, String prenom, String email, LocalDate naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    //getter and setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getNaissance() {
        return naissance;
    }
    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    //methode toString()
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
}
