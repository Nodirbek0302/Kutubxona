package com.example.kutubxona_project.utils;


public interface AppConstants {
    String BEARER_TYPE = "Bearer";
    String AUTH_HEADER = "Authorization";
    String REFRESH_AUTH_HEADER = "RefreshToken";
    String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#_$%^&+=])(?=\\S+$).{8,}$";

//    Auth Controller Path
    String AUTHE_CONTROLLER_BASE_PATH = "/api/auth";
    String AUTHE_CONTROLLER_LOGIN_PATH = "/login";
    String AUTHE_CONTROLLER_REFRESH_TOKEN_PATH = "/refresh-token";
    String AUTHE_CONTROLLER_REGISTER_PATH = "/register";
    String AUTHE_CONTROLLER_SEND_EMAIL = "/email";
    //    -----------------------------------------------
//    BookController path
    String BOOK_CONTROLLER_BASE_PATH = "/book";
    String BOOK_CONTROLLER_ALL_BOOKS = "/books";
    String BOOK_CONTROLLER_ADD_BOOK = "/add";
    String BOOK_CONTROLLER_UPDATE = "/update/{id}";
    String BOOK_CONTROLLER_ONE_BOOK= "/{id}";
    String BOOK_CONTROLLER_DELETE = "/delete/{id}";
    String BOOK_CONTROLLER_BRON = "/bron";
    String BOOK_CONTROLLER_BRON_RECEIVER = "/receiver";
    String BOOK_CONTROLLER_BRON_BOOKS = "/bron-books";
    String BOOK_CONTROLLER_SEARCH = "/search";

//    -----------------------------------------------


}
