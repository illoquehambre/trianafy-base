package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.PlayList.PlayListDtoConverter;
import com.salesianostriana.dam.trianafy.dto.PlayList.PlayListResponse;
import com.salesianostriana.dam.trianafy.dto.PlayList.PlayListResponseDetails;
import com.salesianostriana.dam.trianafy.dto.Song.SongResponse;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class PlayListController {
    private final PlaylistRepository repo;
    private final PlayListDtoConverter dtoConverter;
    private final PlaylistService service;


    @GetMapping("/list")
    public ResponseEntity<List<PlayListResponse>> PlaylistsList(){

        List<Playlist> data = service.findAll();

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<PlayListResponse> result =
                    data.stream()
                            .map(PlayListResponse::of)
                            .collect(Collectors.toList());

            return ResponseEntity
                    .ok()
                    .body(result);

        }
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<PlayListResponseDetails> findPlayListById(@PathVariable Long id){
        return ResponseEntity.of(dtoConverter.playListToPlayListResponseDetails(service.findById(id)));
    }

    @PostMapping("/list")
    public  ResponseEntity<Playlist> createPlayList(@RequestBody Playlist playList){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(playList));
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<Playlist> deletePlayList(@PathVariable Long id){
        if(repo.existsById(id))
            service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/list/{id}")
    public ResponseEntity<Playlist> updatePlayList(@PathVariable Long id,
                                           @RequestBody Playlist playList){
        return ResponseEntity.of(
                repo.findById(id).map(old -> {
                            old.setName(playList.getName());
                            old.setDescription(playList.getDescription());
                            old.setSongs(playList.getSongs());
                            return Optional.of(service.add(old));
                        })
                        .orElse(Optional.empty())
        );
    }
}
