package com.example.kutubxona_project.controller;

import com.example.kutubxona_project.dto.ApiResult;
import com.example.kutubxona_project.service.closet.ClosetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/closet")
@RequiredArgsConstructor
public class ClosetController {

    private final ClosetService closetService;

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @PostMapping
    public HttpEntity<ApiResult<Boolean>> addCloset(@RequestParam Integer floorId) {
        return ResponseEntity.ok(closetService.addCloset(floorId));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @DeleteMapping
    public HttpEntity<ApiResult<Boolean>> delete(@RequestParam Integer closetId) {
        return ResponseEntity.ok(closetService.delete(closetId));
    }
}
