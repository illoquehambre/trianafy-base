package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class ArtistNotFoundException extends EntityNotFoundException {

    public ArtistNotFoundException() {
        super("The artist could not be found");
    }

    public ArtistNotFoundException(Long id) {
        super(String.format("The artist with id %d could not be found", id));
    }

}
