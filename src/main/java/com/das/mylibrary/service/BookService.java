package com.das.mylibrary.service;

import com.das.mylibrary.dto.BookCreateRequest;
import com.das.mylibrary.dto.BookResponse;
import com.das.mylibrary.entity.Book;
import com.das.mylibrary.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }
public List<BookResponse> getAllBooks() {
    return bookRepository.findAll()
            .stream()
            .map(book -> BookResponse.builder()
                    .id(book.getId())
                    .bookName(book.getBookName())
                    .author(book.getAuthor())
                    .genre(book.getGenre())
                    .totalCopies(book.getTotalCopies())
                    .availableCopies(book.getAvailableCopies())
                    .build())
            .toList();
}

    public BookResponse getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return BookResponse.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .coverImageUrl(book.getCoverImageUrl())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .genre(book.getGenre())
                .language(book.getLanguage())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .description(book.getDescription())
                .publishedYear(book.getPublishedYear())
                .isbn(book.getIsbn())
                .build();
    }

    public BookResponse createBook(BookCreateRequest request, MultipartFile image)throws IOException {

        validatePublishedYear(request.getPublishedYear());

        String uploadDir = "uploads/";
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

        Path path = Paths.get(uploadDir + fileName);
        Files.createDirectories(path.getParent());
//        Files.write(path, image.getBytes());
        Files.copy(image.getInputStream(), path);

        String imageUrl = "/uploads/" + fileName;

        Book book = Book.builder()
                .bookName(request.getBookName())
                .coverImageUrl(imageUrl)
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .totalCopies(request.getTotalCopies())
                .availableCopies(request.getTotalCopies())
                .description(request.getDescription())
                .publishedYear(request.getPublishedYear())
                .isbn(request.getIsbn())
                .build();

        Book savedBook = bookRepository.save(book);

        return BookResponse.builder()
                .id(savedBook.getId())
                .bookName(savedBook.getBookName())
                .author(savedBook.getAuthor())
                .genre(savedBook.getGenre())
                .totalCopies(savedBook.getTotalCopies())
                .availableCopies(savedBook.getAvailableCopies())
                .build();
    }


    private void validatePublishedYear(Integer year) {
        int currentYear = Year.now().getValue();
//        if (year == null) {
//            throw new IllegalArgumentException("");
//        }

        if (year > currentYear) {
            throw new IllegalArgumentException("Enter Valid Year");
        }
//        if (year < 1000) {
//            throw new IllegalArgumentException("Published year is too old");
//        }
    }




}