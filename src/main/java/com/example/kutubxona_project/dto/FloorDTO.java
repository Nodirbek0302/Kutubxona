package com.example.kutubxona_project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FloorDTO {
    Integer id;
    String name;
    List<ClosetDTO> closetDTO;
}
