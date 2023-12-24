package com.example.kutubxona_project.utils;

import com.example.kutubxona_project.exceptions.RestException;
import com.example.kutubxona_project.model.Users;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class CommonUtils {

    public static Users getCurrentUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser"))
            throw RestException.restThrow("OKa yopiq yul", HttpStatus.UNAUTHORIZED);

        return (Users) authentication.getPrincipal();
    }
}
