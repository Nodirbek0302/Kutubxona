package com.example.kutubxona_project.repository;

import com.example.kutubxona_project.model.Polka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolkaRepository extends JpaRepository<Polka,Integer> {
}
