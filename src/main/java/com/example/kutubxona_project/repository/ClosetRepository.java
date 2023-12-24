package com.example.kutubxona_project.repository;

import com.example.kutubxona_project.model.Closet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClosetRepository extends JpaRepository<Closet,Integer> {

}

