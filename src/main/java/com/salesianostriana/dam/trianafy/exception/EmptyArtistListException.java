package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyArtistListException extends EntityNotFoundException {

    public EmptyArtistListException() {
        super("No artists were found with the search criteria");
    }


}
