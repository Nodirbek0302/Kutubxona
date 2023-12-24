package com.example.kutubxona_project.controller;

import com.example.kutubxona_project.dto.AddBookFloorDTO;
import com.example.kutubxona_project.dto.ApiResult;
import com.example.kutubxona_project.dto.FloorDTO;
import com.example.kutubxona_project.service.floor.FloorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/floor")
@RequiredArgsConstructor
public class FloorController {
    public final FloorService floorService;

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @PostMapping
    public HttpEntity<ApiResult<Boolean>> addFloor(@RequestParam String floorName) {
        return ResponseEntity.ok(floorService.addFloor(floorName));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping
    public HttpEntity<ApiResult<List<FloorDTO>>> all() {
        return ResponseEntity.ok(floorService.allFloor());
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @DeleteMapping
    public HttpEntity<ApiResult<Boolean>> delete(@RequestParam Integer floorId) {
        return ResponseEntity.ok(floorService.delete(floorId));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @PostMapping("/add-book-floor")
    public HttpEntity<ApiResult<Boolean>> addBookFloor(@Valid @RequestBody AddBookFloorDTO addBookFloor) {
        return ResponseEntity.ok(floorService.addBookToFloor(addBookFloor));
    }

}
