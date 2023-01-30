package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.dto.Song.SongResponse;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Artist", description = "Controller of requests of Artist entity")


public class ArtistController {

    private final ArtistRepository repo;
    private final ArtistService service;
    private final SongService songService;


    @Operation(summary = "Obtiene todos los Artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado artistas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "nombre": "Metallica"},
                                                {"id": 2, "nombre": "Iron Maiden"}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun artista",
                    content = @Content),
    })
    @GetMapping("/artist")
    public List<Artist> artistsList(){

            return service.findAll();

    }
    @Operation(summary = "Obtiene un artista por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado al artista",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "nombre": "Metallica"},
                                                {"id": 2, "nombre": "Iron Maiden"}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun artista",
                    content = @Content),
    })


    @GetMapping("/artist/{id}")
    public Artist findArtistById(@PathVariable Long id){
        return service.findById(id);
    }

    @Operation(summary = "Crea un nuevo Artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Artista creado",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "nombre": "Metallica"},
                                                {"id": 2, "nombre": "Iron Maiden"}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear un artista",
                    content = @Content),
    })

    @PostMapping("/artist")
    public  ResponseEntity<Artist> createArtist(@Valid @RequestBody Artist artist){
        Artist created = service.add(artist);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();


        return ResponseEntity
                .created(createdURI)
                .body(created);
    }
    @Operation(summary = "Elimina un artista y setea a null en las canciones que tiene")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminada la canción ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido encontrar la canción",
                    content = @Content),
    })
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable Long id){

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Modifica un Artista ya existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Artista modifcado",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "nombre": "Metallica"},
                                                {"id": 2, "nombre": "Iron Maiden"}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido encontrar el artista indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar el artista indicado",
                    content = @Content),
    })
    @PutMapping("/artist/{id}")
    public Artist updateArtist(@PathVariable Long id,
                                               @RequestBody Artist artist){
        return service.edit(id, artist);
    }

}
