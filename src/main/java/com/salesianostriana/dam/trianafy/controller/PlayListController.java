package com.salesianostriana.dam.trianafy.controller;

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

@RestController
@AllArgsConstructor
public class PlayListController {
    private final PlaylistRepository repo;
    private final PlaylistService service;


    @GetMapping("/playList")
    public ResponseEntity<List<Playlist>> playListsList(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/playList/{id}")
    public ResponseEntity<Playlist> findPlayListById(@PathVariable Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @PostMapping("/playList")
    public  ResponseEntity<Playlist> createPlayList(@RequestBody Playlist playList){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(playList));
    }

    @DeleteMapping("/playList/{id}")
    public ResponseEntity<Playlist> deletePlayList(@PathVariable Long id){
        if(repo.existsById(id))
            service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/playList/{id}")
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
