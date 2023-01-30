package com.salesianostriana.dam.trianafy.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Song {

    @JsonView(Views.PublicSong.class)
    @Id@GeneratedValue
    private Long id;
    @JsonView(Views.PublicSong.class)
    private String title;
    @JsonView(Views.PublicSong.class)
    private String album;
    @JsonView(Views.PublicSong.class)
    @Column(name = "year_of_song")
    private String year;
    @JsonView(Views.DetailSong.class)

    @ManyToOne(fetch = FetchType.EAGER)
    private Artist artist;
    @JsonView(Views.ResponseSong.class)
    private String artistName;


    public Song(String title, String album, String year) {
        this.title = title;
        this.album = album;
        this.year = year;
    }
}
