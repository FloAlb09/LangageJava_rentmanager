package com.epf.rentmanager.modele;

//Importation
import java.time.LocalDate;

//Classe
public class Reservation {
    //attributs
    private Long id;
    private Long client_id;
    private Long vehicle_id;
    private LocalDate debut;
    private LocalDate fin;

    //constructeurs
        //vide
    public Reservation() {
    }
        //par defaut
    public Reservation(Long id, Long client_id, Long vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(Long id, Long vehicleId, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    //getter and setter
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

    //methode toString()
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicle_id=" + vehicle_id +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}
