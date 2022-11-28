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
@Tag(name = "PlayList", description = "Controller of requests of PlayList entity")
public class PlayListController {
    private final PlaylistRepository repo;
    private final PlayListDtoConverter dtoConverter;
    private final PlaylistService service;
    private final SongService songService;


    @Operation(summary = "Obtiene todas las playlists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado playlists",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayListResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
[
	{{"id": 1, "name": "Primera Lista", "numberOfSongs": 4},
	{"id": 2, "name": "Segunda Lista", "numberOfSongs": 7}
]
"""
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist",
                    content = @Content),
    })
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
    @Operation(summary = "Obtiene una de las playlists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la playlist",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayListResponseDetails.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "name": "Primera Lista", "description": "...", songs{“id”: 1, “title”: “The song”,\s
                                                                                                                      “artist”: “Artist name”,\s
                                                                                                                      “album” : “The album”, “year”: 2000},
                                                                                                                    { “id”: 2, “title”: “Another song”,\s
                                                                                                                      “artist”: “Another Artist name”,\s
                                                                                                                      “album” : “Another album”, “year”: 2020}}
                                                
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la playlist",
                    content = @Content),
    })
    @GetMapping("/list/{id}")
    public ResponseEntity<PlayListResponseDetails> findPlayListById(@PathVariable Long id){
        return ResponseEntity.of(dtoConverter.playListToPlayListResponseDetails(service.findById(id)));
    }
    @Operation(summary = "crea una nueva playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Nueva playlist creada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CreatePlayListDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "name": "Primera Lista", "description": "..."}                                                
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear la playlist",
                    content = @Content),
    })
    @PostMapping("/list")
    public  ResponseEntity<CreatePlayListDto> createPlayList(@RequestBody CreatePlayListDto dto){

        Playlist nuevo = dtoConverter.createPlayListDtoToPlayList(dto);
        service.add(nuevo);
       dto.setId(nuevo.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @Operation(summary = "Elimina una lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminada la lista ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Playlist.class)),
                            examples = {@ExampleObject(
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido encontrar la lista",
                    content = @Content),
    })
    @DeleteMapping("/list/{id}")
    public ResponseEntity<Playlist> deletePlayList(@PathVariable Long id){
        if(repo.existsById(id))
            service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Modifica una playlist ya existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Playist modifcada con éxito",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayListResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "name": "Primera Lista", "numberOfSongs": 4}                                                
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar la playlist",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido encontrar la playlist",
                    content = @Content),
    })


    @PutMapping("/list/{id}")
    public ResponseEntity<PlayListResponse> updatePlayList(@PathVariable Long id,
                                           @RequestBody CreatePlayListDto dto){
        return ResponseEntity.of(
                service.findById(id).map(old -> {
                            old.setName(dto.getName());
                            old.setDescription(dto.getDescription());
                            return Optional.of(dtoConverter.playListToGetPlayListDto(service.add(old)));
                        })
                        .orElse(Optional.empty())
        );
    }
    @Operation(summary = "Obtiene todas las canciones de una playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Canciones y playlist encontradas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayListResponseDetails.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "name": "Primera Lista", "description": "...", "songsList": [ {"id": 1, "title": "Master of puppets", "album": "Master of Puppets", "year":"2001", "artistName": "Metallica" },
                                                {"id": 2, "title": "The Trooper", "album": ".....", "year":"2001", "artistName": "Iron Maiden"}} ]                                        
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido encontrar la playlist o sus canciones",
                    content = @Content),
    })
    @GetMapping("/list/{id}/song")
    public ResponseEntity<PlayListResponseDetails> cancionesDeUnaLista(@PathVariable Long id){
        Optional<PlayListResponseDetails> response = dtoConverter.playListToPlayListResponseDetails(service.findById(id));
        return ResponseEntity.of(response);
    }


    @Operation(summary = "Obtiene una canción de una playlist por su id")
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
    @GetMapping("/list/{id1}/song/{id2}") //no funciona
    public ResponseEntity<Song> cancionDeUnaPlayList(@PathVariable Long id1,
                                                     @PathVariable Long id2){
        Optional<Playlist> playlist = service.findById(id1);

        if (playlist.get().getSongs().contains(songService.findById(id2).get())){
            return ResponseEntity.of(songService.findById(id2));
        }else{
            return ResponseEntity.of(Optional.empty());
        }


        /*playlist.get().getSongs().stream().forEach((s)->{
            if (songService.findById(id2).==s)
                return ResponseEntity.of(songService.findById(id2);
        });*/
    }
    @Operation(summary = "Añade una canción a una playlist por sus id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha añadido la canción ",
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
                    description = "No se ha encontrado la canción o la playlist",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido añadir la canción o la playlist",
                    content = @Content),
    })
    @PostMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<PlayListResponseDetails> añadirCancionALista(@PathVariable Long id1,
                                                                       @PathVariable Long id2){
        Optional<Playlist> playlist = service.findById(id1);
        Optional<Song> song = songService.findById(id2);

        if(playlist.isPresent()&&song.isPresent()){
            playlist.get().addSong(song.get());
            repo.save(playlist.get());

            return ResponseEntity.ok(dtoConverter.playListToPlayListResponseDetails(service.findById(id1)).get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }
    @Operation(summary = "Borra una canción a una playlist por sus id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la canción ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayListResponseDetails.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción o la playlist",
                    content = @Content)

    })
    @DeleteMapping("/list/{id1}/song/{id2}")
    public  ResponseEntity<?> borrarCancionLista(@PathVariable Long id1,
                                                                       @PathVariable Long id2){
/*
            if(repo.existsById(id1)&&songService.findById(id2).isPresent())
                service.findById(id1).get().deleteSong(songService.findById(id2).get());


            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            */
        if(songService.findById(id2).isPresent() && service.findById(id1).get().getSongs().contains(songService.findById(id2).get())) {
            Song song = songService.findById(id2).get();
            service.findAll()
                    .stream()
                    .filter(playlist -> playlist.getSongs().contains(song)).forEach(playlist -> {
                        while (playlist.getSongs().contains(song)){
                            playlist.deleteSong(song);
                        }
                        service.edit(playlist);
                    });
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


}
