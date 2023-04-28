package com.epf.rentmanager.modele;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private Long client_id;
    private Client client;
    private String client_nom;
    private String client_email;
    private Long vehicle_id;
    private Vehicle vehicle;
    private String vehicle_constructeur;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(Long id, Client client, Vehicle vehicle, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client = client;
        this.vehicle = vehicle;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(Long id, Long client_id, Client client, Long vehicle_id, Vehicle vehicle, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.client = client;
        this.vehicle_id = vehicle_id;
        this.vehicle = vehicle;
        this.debut = debut;
        this.fin = fin;
    }


    public Reservation(Long id, Long client_id, String client_nom, String client_email, Long vehicle_id, String vehicle_constructeur, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.client_nom = client_nom;
        this.client_email = client_email;
        this.vehicle_id = vehicle_id;
        this.vehicle_constructeur = vehicle_constructeur;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(Long id, Long client_id, Long vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(Long id) {
        this.id = id;
    }

    public Reservation(Long id, Long vehicle_id, String client_nom, String client_email, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.vehicle_id = vehicle_id;
        this.client_nom = client_nom;
        this.client_email = client_email;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(Long client_id, Long vehicle_id, LocalDate debut, LocalDate fin) {
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(long id, long client_id, long vehicle_id, String client_nom, String vehicle_constructeur, LocalDate debut, LocalDate fin) {
        super();
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.client_nom = client_nom;
        this.vehicle_constructeur = vehicle_constructeur;
        this.debut = debut;
        this.fin = fin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public String getClient_nom() {
        return client_nom;
    }

    public void setClient_nom(String client_nom) {
        this.client_nom = client_nom;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getVehicle_constructeur() {
        return vehicle_constructeur;
    }

    public void setVehicle_constructeur(String vehicle_constructeur) {
        this.vehicle_constructeur = vehicle_constructeur;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", client_id=" + client_id + ", client_nom='" + client_nom + '\'' + ", client_email='" + client_email + '\'' + ", vehicle_id=" + vehicle_id + ", vehicle_constructeur='" + vehicle_constructeur + '\'' + ", debut=" + debut + ", fin=" + fin + '}';
    }
}
