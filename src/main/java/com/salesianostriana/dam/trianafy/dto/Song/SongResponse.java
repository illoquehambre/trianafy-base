package com.salesianostriana.dam.trianafy.dto.Song;

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

    private Long id;
    private String title;
    private String album;
    private String year;
    private String artistName;

        public static SongResponse of (Song m) {
            return SongResponse
                    .builder()
                    .id(m.getId())
                    .title(m.getTitle())
                    .album(m.getAlbum())
                    .year(m.getYear())
                    .artistName(m.getArtist().getArtistName())
                    .build();
        }
}
