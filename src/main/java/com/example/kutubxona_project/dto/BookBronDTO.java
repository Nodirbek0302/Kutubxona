package com.example.kutubxona_project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookBronDTO {
   Integer id;
   ResUserDTO userDTO;
   BookDTO bookDTO;
   LocalDateTime bookPeriod;
   boolean active;
}
