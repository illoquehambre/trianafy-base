package com.salesianostriana.dam.trianafy.dto.Song;

import com.salesianostriana.dam.trianafy.model.Artist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateSongDto {
    private Long id;
    private String title;
    private String album;
    @Column(name = "year_of_song")
    private String year;
    private String artistName;


}
