package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.exception.ArtistNotFoundException;
import com.salesianostriana.dam.trianafy.exception.EmptyArtistListException;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository repository;


    public Artist edit(Artist artist) {
        return repository.save(artist);
    }

    public void delete(Artist artist) {
        repository.delete(artist);
    }



    public List<Artist> findAll() {
        List<Artist> result = repository.findAll();

        if (result.isEmpty()) {
            throw new EmptyArtistListException();
        }

        return result;

    }


    public Artist findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException(id));
    }

    public Artist add(Artist Artist) {
        // En este caso, por ahora, no necesitamos ninguna excepción
        // más allá del propio mecanismo de validación
        return repository.save(Artist);
    }

    public Artist edit(Long id, Artist edited) {
        return repository.findById(id)
                .map(Artist -> {
                    Artist.setArtistName(edited.getArtistName());
                    return repository.save(Artist);
                })
                .orElseThrow(() -> new ArtistNotFoundException());
    }


    public void deleteById(Long id) {
        // En este caso no queremos usar excepciones, sino directamente
        // prevenir el posible error
        if (repository.existsById(id))
            repository.deleteById(id);
    }

    public boolean artistExists(String artistName) {
        return repository.existsByArtistName(artistName);
    }
}
