package com.salesianostriana.dam.trianafy.dto.PlayList;

import com.salesianostriana.dam.trianafy.dto.Song.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.Song.SongResponse;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

@Component

public class PlayListDtoConverter {

    public Playlist createPlayListDtoToPlayList(CreatePlayListDto c) {
        return new Playlist(
                c.getId(),
                c.getName(),
                c.getDescription()
        );
    }

    public PlayListResponse playListToGetPlayListDto(Playlist m) {
        return PlayListResponse
                .builder()
                .id(m.getId())
                .name(m.getName())
                .numberOfSongs(m.getSongs().size())
                .build();
    }
}
