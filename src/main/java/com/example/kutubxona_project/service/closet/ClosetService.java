package com.example.kutubxona_project.service.closet;


import com.example.kutubxona_project.dto.ApiResult;

public interface ClosetService {

    ApiResult<Boolean> addCloset(Integer floorId);

    ApiResult<Boolean> delete(Integer closetId);
}
