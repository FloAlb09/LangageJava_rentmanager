package com.epf.rentmanager.util;


import com.epf.rentmanager.validator.ClientValidator;
import com.epf.rentmanager.modele.Client;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

public class ClientsTest {
    @Test
    void isLegal_should_return_true_when_age_is_over_18() {
        //Given
        Client legalClient = new Client("Albertoli", "Florane", "florane.albertoli@epfedu.fr", LocalDate.of(2001, Month.NOVEMBER, 9));
        //Then
        assertTrue(ClientValidator.isLegal(legalClient));
    }

    @Test
    void isLegal_should_return_false_when_age_is_over_18() {
        //Given
        Client illegalClient = new Client("Albertoli", "Florane", "florane.albertoli@epfedu.fr", LocalDate.of(2023, Month.NOVEMBER, 9));
        //Then
        assertFalse(ClientValidator.isLegal(illegalClient));
    }

    @Test
    void should_return_true_when_name_length_is_atLeast_3() {
        // Given
        Client client = new Client("Albertoli", "Florane", "florane.albertoli@epfedu.fr", LocalDate.of(2001, Month.NOVEMBER, 9));;
        // Then
        assertTrue(ClientValidator.isLenghtNameAtLeastThree(client), "Nom du client assez long");
    }

    @Test
    void should_return_false_when_name_lenght_is_under_3() {
        // Given
        Client client = new Client("Albertoli", "Florane", "florane.albertoli@epfedu.fr", LocalDate.of(2001, Month.NOVEMBER, 9));;
        // Then
        assertTrue(ClientValidator.isLenghtNameAtLeastThree(client), "Nom du client trop court");
    }

}


