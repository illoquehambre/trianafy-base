package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.dto.SongResponse;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class SongController {

    private final SongRepository repo;
    private final ArtistService artistService;
    private final SongDtoConverter dtoConverter;
    private final SongService service;



    @GetMapping("/song")
    public ResponseEntity<List<SongResponse>> songsList(){

        List<Song> data = service.findAll();

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<SongResponse> result =
                    data.stream()
                            .map(SongResponse::of)
                            .collect(Collectors.toList());

            return ResponseEntity
                    .ok()
                    .body(result);

        }
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findSongById(@PathVariable Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @PostMapping("/song")
    public  ResponseEntity<Song> createSong(@RequestBody CreateSongDto dto){
        //return ResponseEntity.status(HttpStatus.CREATED).body(service.add(song));

        if (dto.getId()==null) {
            return ResponseEntity.badRequest().build();
        }

        Song nuevo = dtoConverter.createSongDtoToSong(dto);

        Artist artist = artistService.findById(dto.getId()).orElse(null);

        nuevo.setArtist(artist);

        //nuevo.setCategoria(categoriaRepository.getById(dto.getCategoriaId()));

        nuevo = service.add(nuevo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

    @DeleteMapping("/song/{id}")
    public ResponseEntity<Song> deleteSong(@PathVariable Long id){
        if(repo.existsById(id))
            service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/song/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id,
                                               @RequestBody Song song){
        return ResponseEntity.of(
                repo.findById(id).map(old -> {
                            old.setTitle(song.getTitle());
                            old.setYear(song.getYear());
                            old.setAlbum(song.getAlbum());
                            old.setArtist(song.getArtist());
                            return Optional.of(service.add(old));
                        })
                        .orElse(Optional.empty())
        );
    }

}
