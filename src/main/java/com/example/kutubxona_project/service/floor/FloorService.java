package com.example.kutubxona_project.service.floor;

import com.example.kutubxona_project.dto.AddBookFloorDTO;
import com.example.kutubxona_project.dto.ApiResult;
import com.example.kutubxona_project.dto.FloorDTO;
import com.example.kutubxona_project.model.Floor;

import java.util.*;

public interface FloorService {

    ApiResult<Boolean> addFloor(String floorName);

    ApiResult<Boolean> delete(Integer florId);

    ApiResult<List<FloorDTO>> allFloor();

    ApiResult<Boolean> addBookToFloor(AddBookFloorDTO addBookFloor);
}
