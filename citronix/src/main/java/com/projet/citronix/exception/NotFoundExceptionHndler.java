package com.projet.citronix.exception;


import lombok.Getter;

@Getter
public class NotFoundExceptionHndler extends RuntimeException{

public NotFoundExceptionHndler(String message){
    super(message);
}
}
