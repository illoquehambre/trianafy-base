package com.salesianostriana.dam.trianafy.dto.PlayList;

import com.salesianostriana.dam.trianafy.dto.Song.SongResponse;
import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayListResponseDetails {
    private Long id;
    private String name;
    private String description;
    private List<SongResponse> songsList;

    /*
    private PlayListDtoConverter dto;

    public static PlayListResponseDetails of (Playlist m) {
        return PlayListResponseDetails
                .builder()
                .id(m.getId())
                .name(m.getName())
                .description(m.getDescription())
                .songsList(m.getSongs())
                .build();
    }
*/
}
