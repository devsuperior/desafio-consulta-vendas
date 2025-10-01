package com.devsuperior.dsmeta.services;

public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String msg){
        super(msg);
    }
}
