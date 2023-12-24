package com.example.kutubxona_project.service.polka;

import com.example.kutubxona_project.model.Closet;
import com.example.kutubxona_project.model.Polka;
import com.example.kutubxona_project.repository.PolkaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolkaServiceImpl implements PolkaService {
    private final PolkaRepository repository;

    @Override
    public boolean addPolka(Closet closet) {
        for (int i = 0; i < 10; i++) {
            Polka build = Polka.builder()
                    .closet(closet)
                    .build();
            repository.save(build);
        }

        return true;
    }
}
