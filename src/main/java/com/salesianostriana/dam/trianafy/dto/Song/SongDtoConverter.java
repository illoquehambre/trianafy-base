package com.salesianostriana.dam.trianafy.dto.Song;

import com.salesianostriana.dam.trianafy.model.Song;
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
                .id(m.getId())
                .title(m.getTitle())
                .album(m.getAlbum())
                .year(m.getYear())
                .build();
    }
}
