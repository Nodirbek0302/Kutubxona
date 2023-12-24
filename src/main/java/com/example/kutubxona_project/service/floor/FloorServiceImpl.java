package com.example.kutubxona_project.service.floor;

import com.example.kutubxona_project.dto.*;
import com.example.kutubxona_project.exceptions.RestException;
import com.example.kutubxona_project.mapper.BookMapper;
import com.example.kutubxona_project.model.Books;
import com.example.kutubxona_project.model.Closet;
import com.example.kutubxona_project.model.Floor;
import com.example.kutubxona_project.model.Polka;
import com.example.kutubxona_project.repository.BookRepository;
import com.example.kutubxona_project.repository.FloorRepository;
import com.example.kutubxona_project.service.closet.ClosetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;
    private final ClosetService closetService;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public ApiResult<Boolean> addFloor(String floorName) {
        Floor build = Floor.builder().name(floorName).build();
        floorRepository.save(build);
        for (int i = 0; i < 20; i++) {
            closetService.addCloset(build.getId());
        }
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<Boolean> delete(Integer florId) {
        floorRepository.deleteById(florId);
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<List<FloorDTO>> allFloor() {
        List<Floor> all = floorRepository.findAll();

        List<FloorDTO> floorDTOS = new ArrayList<>();

        for (Floor floor : all) {
            FloorDTO floorDTO = FloorDTO.builder()
                    .id(floor.getId())
                    .name(floor.getName())
                    .closetDTO(mapClosetDTO(floor.getClosetList()))
                    .build();
            floorDTOS.add(floorDTO);
        }

        return ApiResult.successResponse(floorDTOS);
    }

    @Override
    public ApiResult<Boolean> addBookToFloor(AddBookFloorDTO addBookFloor) {
        Floor floor = floorRepository.findById(addBookFloor.getFloorId()).orElseThrow(() -> RestException.restThrow("Bunday Qavat mavjud emas", HttpStatus.BAD_REQUEST));
        Closet closet = floor.getClosetList().stream()
                .filter(closet1 -> closet1.getId().equals(addBookFloor.getClosetId()))
                .findFirst()
                .orElseThrow(() -> RestException.restThrow("Bunday Shkaf bu qavatda mavjud emas", HttpStatus.BAD_REQUEST));
        Polka polka = closet.getPolka().stream().filter(polka1 -> polka1.getId().equals(addBookFloor.getPolkaId()))
                .findFirst().orElseThrow(() -> RestException.restThrow("bunday polka yuq", HttpStatus.BAD_REQUEST));

        if (polka.getBooksList().size() >= 20) {
            throw RestException.restThrow("Iltimos boshqa polkaga joylashtiring bu polkada joy yuq", HttpStatus.BAD_REQUEST);
        }
        List<Books> books = null;
        try {
            books = bookRepository.findAllById(addBookFloor.getBooksId());
        } catch (Exception e) {
            throw RestException.restThrow("Berilgan kitoblarni hammasi mavjud emas", HttpStatus.BAD_REQUEST);
        }

        for (Books book : books) {
            book.setPolka(polka);
        }
        bookRepository.saveAll(books);
        return ApiResult.successResponse(true);
    }



    private List<ClosetDTO> mapClosetDTO(List<Closet> clos) {
        List<ClosetDTO> closetDTOS = new ArrayList<>();
        for (Closet clo : clos) {
            ClosetDTO build = ClosetDTO.builder()
                    .id(clo.getId())
                    .floorId(clo.getFloor().getId())
                    .polkaDTO(mapPolkaDTO(clo.getPolka()))
                    .build();
            closetDTOS.add(build);
        }
        return closetDTOS;
    }

    private List<PolkaDTO> mapPolkaDTO(List<Polka> polka) {

        List<PolkaDTO> polkaDTOS = new ArrayList<>();

        for (Polka p : polka) {
            PolkaDTO build = PolkaDTO.builder()
                    .id(p.getId())
                    .closetId(p.getCloset().getId())
                    .bookDTO(bookMapper.mapBookDTOList(p.getBooksList()))
                    .build();
            polkaDTOS.add(build);
        }
        return polkaDTOS;
    }
}
