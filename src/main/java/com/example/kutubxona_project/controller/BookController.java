package com.example.kutubxona_project.controller;

import com.example.kutubxona_project.dto.*;
import com.example.kutubxona_project.model.BookBron;
import com.example.kutubxona_project.model.Books;
import com.example.kutubxona_project.service.book.BookService;
import com.example.kutubxona_project.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(AppConstants.BOOK_CONTROLLER_BASE_PATH)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @PostMapping(AppConstants.BOOK_CONTROLLER_ADD_BOOK)
    public HttpEntity<ApiResult<BookDTO>> add(@Valid @RequestBody AddBookDTO bookDTO) {
        return ResponseEntity.ok(bookService.add(bookDTO));
    }

    @GetMapping(AppConstants.BOOK_CONTROLLER_ALL_BOOKS)
    public HttpEntity<ApiResult<List<BookDTO>>> getAll() {
        return ResponseEntity.ok(bookService.list());
    }

    @GetMapping(AppConstants.BOOK_CONTROLLER_ONE_BOOK)
    public HttpEntity<ApiResult<BookDTO>> getOneBook(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @PutMapping(AppConstants.BOOK_CONTROLLER_UPDATE)
    public HttpEntity<ApiResult<BookDTO>> update(@PathVariable Integer id, @Valid @RequestBody AddBookDTO addBookDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookService.update(id, addBookDTO));
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @DeleteMapping(AppConstants.BOOK_CONTROLLER_DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpEntity<ApiResult<Boolean>> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.delete(id));
    }
    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN','MODERATOR')")
    @PostMapping(AppConstants.BOOK_CONTROLLER_BRON)
    public HttpEntity<ApiResult<Boolean>> bookBron(@Valid @RequestBody AddBookBronDTO bookBronDTO) {
        return ResponseEntity.ok(bookService.bookBron(bookBronDTO));
    }

    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN','MODERATOR')")
    @GetMapping(AppConstants.BOOK_CONTROLLER_BRON_RECEIVER)
    public HttpEntity<ApiResult<List<ReceivedBookDTO>>> receivedBook(){
       return ResponseEntity.ok(bookService.receiveBook());
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping(AppConstants.BOOK_CONTROLLER_BRON_BOOKS)
    public HttpEntity<ApiResult<List<BookBronDTO>>> getAllBookBron(){
        return ResponseEntity.ok(bookService.allBookBron());
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping(AppConstants.BOOK_CONTROLLER_SEARCH)
    public HttpEntity<ApiResult<List<Books>>> getSearchBook(@RequestParam String name,@RequestParam String author){
        return ResponseEntity.ok(bookService.search(name, author));
    }

}
