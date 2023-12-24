package com.example.kutubxona_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivedBookDTO {
    private BookDTO bookDTO;
    private String userEmail;
}
