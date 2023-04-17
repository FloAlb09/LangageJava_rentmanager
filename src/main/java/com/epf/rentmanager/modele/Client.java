package com.epf.rentmanager.modele;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

public class Client {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;

    public Client(){
        this.id = 0L;
        this.nom = "Albertoli";
        this.prenom = "Florane";
        this.email = "florane.albertoli@epfedu.fr";
        this.naissance = LocalDate.of(2001, Month.NOVEMBER, 9);
    }
    public Client(Long id, String nom, String prenom, String email, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }
    public Client(Long id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }
    public Client(String nom, String prenom, String email, LocalDate naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }
    public Client(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
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
        return "Nom : " + nom +
                ", Prenom : " + prenom +
                ", Email : " + email +
                ", Naissance : " + naissance;
    }

    public static int getAge(LocalDate naissance)
    {
        Calendar curr = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(Date.valueOf(naissance));
        int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        curr.add(Calendar.YEAR,-yeardiff);
        if(birth.after(curr))
        {
            yeardiff = yeardiff - 1;
        }
        return yeardiff;
    }
}
