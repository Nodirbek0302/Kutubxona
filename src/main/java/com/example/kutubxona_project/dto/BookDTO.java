package com.example.kutubxona_project.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDTO {
   private Integer id;
   private String name;
   private String author;
}
