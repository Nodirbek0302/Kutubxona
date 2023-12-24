package com.example.kutubxona_project.repository;

import com.example.kutubxona_project.model.Books;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Books,Integer> {

    Optional<Books> findByNameAndAuthorContainingIgnoreCase(@NotBlank String name, @NotBlank String author);

    List<Books> findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(@NotBlank String name, @NotBlank String author);
}
