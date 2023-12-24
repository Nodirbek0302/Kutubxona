package com.example.kutubxona_project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClosetDTO {
  Integer id;
  Integer floorId;
  List<PolkaDTO> polkaDTO;
}
