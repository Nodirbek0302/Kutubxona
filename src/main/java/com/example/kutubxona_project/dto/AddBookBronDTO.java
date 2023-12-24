package com.example.kutubxona_project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBookBronDTO {
    private Integer userId;
    private Integer bookId;
}
