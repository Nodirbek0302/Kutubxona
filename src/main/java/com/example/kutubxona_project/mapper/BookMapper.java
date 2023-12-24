package com.example.kutubxona_project.mapper;

import com.example.kutubxona_project.dto.AddBookDTO;
import com.example.kutubxona_project.dto.BookDTO;
import com.example.kutubxona_project.model.Books;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Books mapBooks(AddBookDTO bookDTO);

    BookDTO mapBookDto(Books books);

    List<BookDTO> mapBookDTOList(List<Books> all);
}
