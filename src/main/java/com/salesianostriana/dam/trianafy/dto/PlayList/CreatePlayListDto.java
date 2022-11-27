package com.salesianostriana.dam.trianafy.dto.PlayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlayListDto {

    private Long id;
    private String name;
    private String description;
}
