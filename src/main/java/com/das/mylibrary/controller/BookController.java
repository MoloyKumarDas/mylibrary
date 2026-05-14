package com.das.mylibrary.controller;

import com.das.mylibrary.dto.BookCreateRequest;
import com.das.mylibrary.dto.BookResponse;
import com.das.mylibrary.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookResponse createBook(  @RequestPart("book") @Valid BookCreateRequest request,
                                     @RequestPart("image") MultipartFile image) throws IOException {
        return bookService.createBook(request,image);

    }
}
