package com.example.kutubxona_project.repository;

import com.example.kutubxona_project.model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<Floor,Integer> {
}
