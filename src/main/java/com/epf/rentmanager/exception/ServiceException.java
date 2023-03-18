package com.epf.rentmanager.exception;

public class ServiceException extends Exception{
    public ServiceException(String message){super();}
    public ServiceException() {
        super("Une erreur liée au Service a eu lieu");
    }
}
