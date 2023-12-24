package com.example.kutubxona_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookBron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @JoinColumn(nullable = false)
    @OneToOne
    Users users;

    @NotNull
    @JoinColumn(nullable = false)
    @OneToOne
    Books books;

    LocalDateTime bookPeriod;

    boolean received;

    boolean active = true;

    LocalDateTime returnTime;

    boolean returned;

}
