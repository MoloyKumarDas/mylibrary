package com.das.mylibrary.controller;

import com.das.mylibrary.dto.BookCreateRequest;
import com.das.mylibrary.dto.BookResponse;
import com.das.mylibrary.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/paged")
    public Page<BookResponse> getBooksPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return bookService.getBooksPaged(page, size);
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

//    @GetMapping("/search")
//    public List<BookResponse> searchBooks(@RequestParam String query) {
//        return bookService.searchBooks(query);
//    }

    @GetMapping("/search")
    public List<BookResponse> searchBooks(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Integer publishedYear
    ) {
        return bookService.searchBooks(query, author, bookName, genre, publisher, publishedYear);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(
            @PathVariable Long id,
            @RequestPart("book") @Valid BookCreateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image           // required = false // update possible with or without image
    ) throws IOException {
        return bookService.updateBook(id, request, image);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
