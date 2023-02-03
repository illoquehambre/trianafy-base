package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Artist;
import lombok.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateArtistDto {

    @NotEmpty(message = "{createArtistDto.name.notempty}")
    private String name;

    public static Artist toArtist(CreateArtistDto dto) {
        return Artist.builder()
                .artistName(dto.getName())
                .build();
    }



}
