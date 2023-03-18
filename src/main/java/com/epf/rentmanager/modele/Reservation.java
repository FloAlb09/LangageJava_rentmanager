package com.epf.rentmanager.modele;

//Importation
import java.time.LocalDate;

//Classe
public class Reservation {
    //attributs
    private Long id;
    private Client client;
    private Long client_id;
    private Vehicle vehicle;
    private Long vehicle_id;
    private LocalDate debut;
    private LocalDate fin;

    //constructeurs
        //vide
        public Reservation(long id, Client client, long client_id, Vehicle vehicle, long vehicle_id, LocalDate debut, LocalDate fin) {
        }
        //par defaut
    public Reservation(Long id, Long client_id, Long vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(Long client_id, Long vehicle_id, LocalDate debut, LocalDate fin) {
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(long id, Client client, Vehicle vehicle, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client = client;
        this.vehicle = vehicle;
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
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Long getClient_id() {
        return client_id;
    }
    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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

    //methode to_string
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client=" + client +
                ", client_id=" + client_id +
                ", vehicle=" + vehicle +
                ", vehicle_id=" + vehicle_id +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}
