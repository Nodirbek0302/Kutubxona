package com.example.kutubxona_project.service.closet;

import com.example.kutubxona_project.dto.ApiResult;
import com.example.kutubxona_project.exceptions.RestException;
import com.example.kutubxona_project.model.Closet;
import com.example.kutubxona_project.model.Floor;
import com.example.kutubxona_project.repository.ClosetRepository;
import com.example.kutubxona_project.repository.FloorRepository;
import com.example.kutubxona_project.service.polka.PolkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClosetServiceImpl implements ClosetService {
   private final ClosetRepository closetRepository;
   private final FloorRepository floorRepository;
   private final PolkaService polkaService;

    @Override
    public ApiResult<Boolean> addCloset(Integer floorId) {
        Floor floor = floorRepository.findById(floorId).orElseThrow(() -> RestException.restThrow("Bunday qavat mavjud emas", HttpStatus.BAD_REQUEST));
        Closet closet = Closet.builder()
                .floor(floor)
                .build();
        closetRepository.save(closet);
        polkaService.addPolka(closet);
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<Boolean> delete(Integer closetId) {
        closetRepository.deleteById(closetId);
        return ApiResult.successResponse(true);
    }
}
