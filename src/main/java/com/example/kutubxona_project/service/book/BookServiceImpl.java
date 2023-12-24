package com.example.kutubxona_project.service.book;

import com.example.kutubxona_project.dto.*;
import com.example.kutubxona_project.exceptions.RestException;
import com.example.kutubxona_project.mapper.BookMapper;
import com.example.kutubxona_project.mapper.UserMapper;
import com.example.kutubxona_project.model.BookBron;
import com.example.kutubxona_project.model.Books;
import com.example.kutubxona_project.model.Users;
import com.example.kutubxona_project.repository.BookBronRepository;
import com.example.kutubxona_project.repository.BookRepository;
import com.example.kutubxona_project.repository.UserRepository;
import com.example.kutubxona_project.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final BookBronRepository bookBronRepository;
    private final UserMapper userMapper;

    @Override
    public ApiResult<BookDTO> add(AddBookDTO bookDTO) {
        Optional<Books> optionalBooks = bookRepository.findByNameAndAuthorContainingIgnoreCase(bookDTO.getName(), bookDTO.getAuthor());
        if (optionalBooks.isPresent()) throw RestException.restThrow("Bunday kitob mavjud", HttpStatus.BAD_REQUEST);

        Books books = bookMapper.mapBooks(bookDTO);
        bookRepository.save(books);
        return ApiResult.successResponse(bookMapper.mapBookDto(books));
    }

    @Override
    public ApiResult<BookDTO> update(Integer id, AddBookDTO bookDTO) {
        Books books = bookRepository.findById(id).orElseThrow(() -> RestException.restThrow("Bunday kitob mavjud emas", HttpStatus.NO_CONTENT));
        books.setAuthor(bookDTO.getAuthor());
        books.setName(bookDTO.getName());
        bookRepository.save(books);
        BookDTO dto = bookMapper.mapBookDto(books);
        return ApiResult.successResponse(dto);
    }

    @Override
    public ApiResult<List<BookDTO>> list() {
        List<Books> all = bookRepository.findAll();
        return ApiResult.successResponse(bookMapper.mapBookDTOList(all));
    }

    @Override
    public ApiResult<BookDTO> getById(Integer id) {
        Books books = bookRepository.findById(id).orElseThrow(() -> RestException.restThrow("Bunday kitob mavjud emas", HttpStatus.BAD_REQUEST));
       return ApiResult.successResponse(bookMapper.mapBookDto(books));
    }

    @Override
    public ApiResult<Boolean> delete(Integer id) {
        bookRepository.deleteById(id);
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<Boolean> bookBron(AddBookBronDTO bookBronDTO) {
        Books books = bookRepository.findById(bookBronDTO.getBookId()).orElseThrow(() -> RestException.restThrow("Bunday kitob mavjud emas", HttpStatus.NO_CONTENT));
        Users us = userRepository.findById(bookBronDTO.getUserId()).orElseThrow(() -> RestException.restThrow("Bunday user vajud emas", HttpStatus.BAD_REQUEST));

        BookBron bookBron = BookBron.builder()
                .users(us)
                .books(books)
                .active(true)
                .bookPeriod(LocalDateTime.now().minusDays(3))
                .build();
        bookBronRepository.save(bookBron);
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<List<ReceivedBookDTO>> receiveBook() {
        Users user = CommonUtils.getCurrentUserFromContext();

        List<BookBron> activeBooks = new ArrayList<>();

        List<BookBron> bookBrons = bookBronRepository.findByUsersIdAndActiveTrue(user.getId());

        for (BookBron bookBron : bookBrons) {
            if (bookBron.getBookPeriod().isBefore(LocalDateTime.now())){
                activeBooks.add(bookBron);
                bookBron.setReceived(true);
                bookBron.setReturnTime(LocalDateTime.now().plusWeeks(1));
                bookBron.setActive(false);
            }
                bookBron.setActive(false);
        }

        bookBronRepository.saveAll(bookBrons);

        List<ReceivedBookDTO> response = new ArrayList<>();

        for (BookBron activeBook : activeBooks) {
            ReceivedBookDTO receivedBookDTO = ReceivedBookDTO.builder()
                    .userEmail(activeBook.getUsers().getEmail())
                    .bookDTO(bookMapper.mapBookDto(activeBook.getBooks()))
                    .build();
            response.add(receivedBookDTO);
        }
        return ApiResult.successResponse(response);
    }

    @Override
    public ApiResult<List<BookBronDTO>> allBookBron() {
        List<BookBron> all = bookBronRepository.findByActiveTrue();

        List<BookBronDTO> bookBronDTOS = new ArrayList<>();

        for (BookBron bookBron : all) {
            ResUserDTO userDTO = userMapper.mapResUserDTO(bookBron.getUsers());
            BookDTO bookDTO = bookMapper.mapBookDto(bookBron.getBooks());
            BookBronDTO bookBronDTO = BookBronDTO.builder()
                    .id(bookBron.getId())
                    .bookDTO(bookDTO)
                    .userDTO(userDTO)
                    .bookPeriod(bookBron.getBookPeriod())
                    .active(bookBron.isActive())
                    .build();
            bookBronDTOS.add(bookBronDTO);
        }
        return ApiResult.successResponse(bookBronDTOS);
    }

    @Override
    public ApiResult<List<Books>> search(String name, String author) {
        List<Books> booksList = bookRepository.findByNameContainingIgnoreCaseOrAuthorContainingIgnoreCase(name, author);
        return ApiResult.successResponse(booksList);
    }
}
