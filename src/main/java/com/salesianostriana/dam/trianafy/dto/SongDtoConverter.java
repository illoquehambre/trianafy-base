package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import org.springframework.stereotype.Component;

@Component

public class SongDtoConverter {





    public Song createSongDtoToSong(CreateSongDto c) {
        return new Song(
                c.getTitle(),
                c.getAlbum(),
                c.getYear()

        );
    }




    public SongResponse songToGetSongDto(Song m) {
        return SongResponse
                .builder()
                .title(m.getTitle())
                .album(m.getAlbum())
                .year(m.getYear())
                .artistName(m.getArtist().getName())
                .id(m.getId())
                .build();
    }
}
