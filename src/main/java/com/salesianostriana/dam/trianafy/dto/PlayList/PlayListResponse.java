package com.salesianostriana.dam.trianafy.dto.PlayList;

import com.salesianostriana.dam.trianafy.dto.Song.SongResponse;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayListResponse {

    private Long id;
    private String name;
    private int numberOfSongs;

    public static PlayListResponse of (Playlist m) {
        return PlayListResponse
                .builder()
                .id(m.getId())
                .name(m.getName())
                .numberOfSongs(m.getSongs().size())
                .build();
    }
    

}
