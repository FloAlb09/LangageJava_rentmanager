package com.epf.rentmanager.modele;

public class Vehicle {
    //attributs
    private Long id;
    private String constructeur;
    private int nb_places;

    //constructeurs
    //vide
    public Vehicle() {
    }
    //par defaut
    public Vehicle(Long id, String constructeur, int nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        this.nb_places = nb_places;
    }

    public Vehicle(String constructeur, int nb_places) {
        this.constructeur = constructeur;
        this.nb_places = nb_places;
    }

    //getter and setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getConstructeur() {
        return constructeur;
    }
    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }
    public int getNb_places() {
        return nb_places;
    }
    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    //methode toString()

    @Override
    public String toString() {
        return "Constructeur : " + constructeur +
                ", Nombre de places : " + nb_places;
    }
}
