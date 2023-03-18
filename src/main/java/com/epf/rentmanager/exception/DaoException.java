package com.epf.rentmanager.exception;

public class DaoException extends Exception{
    public DaoException() {
        super("Une erreur liée au Dao a eu lieu");
    }
}
