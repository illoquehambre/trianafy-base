package com.salesianostriana.dam.trianafy.dto.PlayList;

import com.salesianostriana.dam.trianafy.dto.Song.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.Song.SongDtoConverter;
import com.salesianostriana.dam.trianafy.dto.Song.SongResponse;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PlayListDtoConverter {

    private final SongDtoConverter songDto;

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

    public Optional<PlayListResponseDetails> playListToPlayListResponseDetails(Optional<Playlist> m) {
            PlayListResponseDetails result =  PlayListResponseDetails
                    .builder()
                    .id(m.get().getId())
                    .name(m.get().getName())
                    .description(m.get().getDescription())
                    .songsList(conversionResponse(m.get().getSongs()))
                    .build();
        Optional<PlayListResponseDetails> Response = Optional.of(result);
            return Response;

    }

    public List<SongResponse> conversionResponse(List<Song> songList){

        List<SongResponse> songResponseList=new ArrayList<>();
        songList.stream().forEach((s)->{
            songResponseList.add(songDto.songToGetSongDto(s));
        });
        return songResponseList;
    }
}
