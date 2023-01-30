package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyArtistListException extends EntityNotFoundException {

    public EmptyArtistListException() {
        super("No notes were found with the search criteria");
    }


}
