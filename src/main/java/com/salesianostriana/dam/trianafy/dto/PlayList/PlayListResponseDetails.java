package com.salesianostriana.dam.trianafy.dto.PlayList;

import com.salesianostriana.dam.trianafy.dto.Song.SongDtoConverter;
import com.salesianostriana.dam.trianafy.dto.Song.SongResponse;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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




}
