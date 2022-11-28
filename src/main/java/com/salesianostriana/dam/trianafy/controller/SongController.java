package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.PlayList.PlayListResponseDetails;
import com.salesianostriana.dam.trianafy.dto.Song.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.Song.SongDtoConverter;
import com.salesianostriana.dam.trianafy.dto.Song.SongResponse;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Tag(name = "Song", description = "Controller of requests of Song entity")

public class SongController {

    private final SongRepository repo;
    private final ArtistService artistService;
    private final PlaylistService playlistService;
    private final SongDtoConverter dtoConverter;

    private final SongService service;



    @Operation(summary = "Obtiene todas las Canciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado canciones",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SongResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "title": "Master of puppets", "album": "Master of Puppets", "year":"2001", "artistName": "Metallica"},
                                                {"id": 2, "title": "The Trooper", "album": ".....", "year":"2001", "artistName": "Iron Maiden"}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna canción",
                    content = @Content),
    })
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
    @Operation(summary = "Obtiene una canción por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la canción ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Song.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "title": "Master of puppets", "album": "Master of Puppets", "year":"2001", "artist": {"id": 1, "name": "Metallica"} },
                                                {"id": 2, "title": "The Trooper", "album": ".....", "year":"2001", "artist": {"id": 1, "name": "Metallica"}}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción",
                    content = @Content),
    })
    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findSongById(@PathVariable Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @Operation(summary = "Crea una nueva canción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado una nueva canción ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SongResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "title": "Master of puppets", "album": "Master of Puppets", "year":"2001", "artistName": "Metallica" },
                                                {"id": 2, "title": "The Trooper", "album": ".....", "year":"2001", "artistName": "Iron Maiden"}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear la canción",
                    content = @Content),
    })
    @PostMapping("/song")
    public  ResponseEntity<SongResponse> createSong(@RequestBody CreateSongDto dto){
        //return ResponseEntity.status(HttpStatus.CREATED).body(service.add(song));

        if (dto.getArtistId()==null) {
            return ResponseEntity.badRequest().build();
        }

        Song nuevo = dtoConverter.createSongDtoToSong(dto);

        Artist artist = artistService.findById(dto.getArtistId()).orElse(null);

        nuevo.setArtist(artist);

        //nuevo.setCategoria(categoriaRepository.getById(dto.getCategoriaId()));

        SongResponse result = dtoConverter.songToGetSongDto(service.add(nuevo));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @Operation(summary = "Elimina una canción y las borra de las playlist en las que se encuentre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminada la canción ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SongResponse.class)),
                            examples = {@ExampleObject(
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido encontrar la canción",
                    content = @Content),
    })
    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id){
        if(service.findById(id).isPresent()) {
            Song song = service.findById(id).get();
            playlistService.findAll()
                    .stream()
                    .filter(playlist -> playlist.getSongs().contains(song)).forEach(playlist -> {
                        while (playlist.getSongs().contains(song)){
                            playlist.deleteSong(song);
                        }
                        playlistService.edit(playlist);
                    });

            service.delete(song);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Crea una nueva canción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado una nueva canción ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SongResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "title": "Master of puppets", "album": "Master of Puppets", "year":"2001", "artistName": "Metallica" },
                                                {"id": 2, "title": "The Trooper", "album": ".....", "year":"2001", "artistName": "Iron Maiden"}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido encontrar la canción",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar la canción",
                    content = @Content),
    })
    @PutMapping("/song/{id}")
    public ResponseEntity<SongResponse> updateSong(@PathVariable Long id,
                                               @RequestBody CreateSongDto dto){
        return ResponseEntity.of(
                service.findById(id).map(old -> {
                            old.setTitle(dto.getTitle());
                            old.setYear(dto.getYear());
                            old.setAlbum(dto.getAlbum());
                            old.setArtist(artistService.findById(dto.getArtistId()).orElse(null));
                            return Optional.of(dtoConverter.songToGetSongDto(service.add(old)));
                        })
                        .orElse(Optional.empty())
        );
    }



}
