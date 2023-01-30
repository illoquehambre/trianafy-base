package com.salesianostriana.dam.trianafy.dto.newSong;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.Views;
import com.salesianostriana.dam.trianafy.model.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class dto {

    @JsonView(Views.PublicSong.class)
    private Long id;
    @JsonView(Views.PublicSong.class)
    private String title;
    @JsonView(Views.PublicSong.class)
    private String album;
    @JsonView(Views.PublicSong.class)
    private String year;
    @JsonView(Views.CreateSong.class)
    private String artistId;
    @JsonView(Views.ResponseSong.class)
    private String artistName;




}
