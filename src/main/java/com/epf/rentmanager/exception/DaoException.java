package com.epf.rentmanager.exception;

public class DaoException extends Exception{
    public DaoException(String message) {
        super("Une erreur liée au DAO a eu lieu");
    }
}
