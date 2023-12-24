package com.example.kutubxona_project.service.book;

import com.example.kutubxona_project.dto.*;
import com.example.kutubxona_project.model.BookBron;
import com.example.kutubxona_project.model.Books;

import java.util.*;

public interface BookService {

    ApiResult<BookDTO> add(AddBookDTO bookDTO);

    ApiResult<BookDTO> update(Integer id,AddBookDTO bookDTO);

    ApiResult<List<BookDTO>> list();

    ApiResult<BookDTO> getById(Integer id);

    ApiResult<Boolean> delete(Integer id);

    ApiResult<Boolean> bookBron(AddBookBronDTO bookBronDTO);

    ApiResult<List<ReceivedBookDTO>> receiveBook();

    ApiResult<List<BookBronDTO>> allBookBron();

    ApiResult<List<Books>> search(String name, String author);
}
