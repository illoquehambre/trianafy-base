package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.PlayList.CreatePlayListDto;
import com.salesianostriana.dam.trianafy.dto.PlayList.PlayListDtoConverter;
import com.salesianostriana.dam.trianafy.dto.PlayList.PlayListResponse;
import com.salesianostriana.dam.trianafy.dto.PlayList.PlayListResponseDetails;
import com.salesianostriana.dam.trianafy.dto.Song.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.Song.SongResponse;
import com.salesianostriana.dam.trianafy.model.Artist;
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
    public  ResponseEntity<CreatePlayListDto> createPlayList(@RequestBody CreatePlayListDto dto){
        //return ResponseEntity.status(HttpStatus.CREATED).body(service.add(song));

        /*if (dto.getArtistId()==null) {
            return ResponseEntity.badRequest().build();
        }*/

        Playlist nuevo = dtoConverter.createPlayListDtoToPlayList(dto);

        //Artist artist = artistService.findById(dto.getArtistId()).orElse(null);

        //nuevo.setArtist(artist);

        //nuevo.setCategoria(categoriaRepository.getById(dto.getCategoriaId()));
        service.add(nuevo);
       //PlayListResponse result = dtoConverter.playListToGetPlayListDto();
       dto.setId(nuevo.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }


    @DeleteMapping("/list/{id}")
    public ResponseEntity<Playlist> deletePlayList(@PathVariable Long id){
        if(repo.existsById(id))
            service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/list/{id}")
    public ResponseEntity<PlayListResponse> updatePlayList(@PathVariable Long id,
                                           @RequestBody CreatePlayListDto dto){
        return ResponseEntity.of(
                repo.findById(id).map(old -> {
                            old.setName(dto.getName());
                            old.setDescription(dto.getDescription());
                            return Optional.of(dtoConverter.playListToGetPlayListDto(service.add(old)));
                        })
                        .orElse(Optional.empty())
        );
    }
}
