package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ArtistController {

    private final ArtistRepository repo;
    private final ArtistService service;



    @GetMapping("/artist")
    public ResponseEntity<List<Artist>> artistsList(){
        List<Artist> data = service.findAll();

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(data);
        }

    }



    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findArtistById(@PathVariable Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @PostMapping("/artist")
    public  ResponseEntity<Artist> createArtist(@RequestBody Artist artist){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(artist));
    }

    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable Long id){
        if(repo.existsById(id))
            service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id,
                                               @RequestBody Artist artist){
        return ResponseEntity.of(
                repo.findById(id).map(old -> {
                    old.setName(artist.getName());
                    return Optional.of(service.add(old));
                })
                        .orElse(Optional.empty())
        );
    }

}
