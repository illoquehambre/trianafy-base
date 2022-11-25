package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongResponse {

    private long id;
    private String title;
    private String album;
    @Column(name = "year_of_song")
    private String year;
    private String artistName;

    public static SongResponse of (Song m) {
        return SongResponse
                .builder()
                .title(m.getTitle())
                .album(m.getAlbum())
                .year(m.getYear())
                .artistName(m.getArtist().getName())
                .build();
    }
}
