package com.salesianostriana.dam.trianafy.dto.PlayList;


import com.salesianostriana.dam.trianafy.model.Playlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



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
