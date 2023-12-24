package com.example.kutubxona_project.repository;

import com.example.kutubxona_project.model.BookBron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookBronRepository extends JpaRepository<BookBron,Integer> {

    List<BookBron> findByUsersIdAndActiveTrue(Integer users_id);

    List<BookBron> findByActiveTrue();
}
