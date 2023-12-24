package com.example.kutubxona_project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddBookFloorDTO {
    @NotNull
    Integer floorId;
    @NotNull
    Integer closetId;
    @NotNull
    Integer polkaId;
    @NotNull
    List<Integer> booksId;
}
