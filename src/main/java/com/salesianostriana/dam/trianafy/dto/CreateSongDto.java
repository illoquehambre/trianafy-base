package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Artist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateSongDto {
    @Id@GeneratedValue
    private Long id;
    private String title;
    private String album;
    @Column(name = "year_of_song")
    private String year;
    private Long artistId;


}
