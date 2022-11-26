package com.salesianostriana.dam.trianafy.repos;

import com.salesianostriana.dam.trianafy.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findArtistByName(String name);
}
