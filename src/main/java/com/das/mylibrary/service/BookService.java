package com.das.mylibrary.service;

import com.das.mylibrary.dto.BookCreateRequest;
import com.das.mylibrary.dto.BookResponse;
import com.das.mylibrary.entity.Book;
import com.das.mylibrary.entity.User;
import com.das.mylibrary.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final UserService userService;

    public List<BookResponse> getAllBooks() {

        User user = userService.getLoggedInUser();

        return bookRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Page<BookResponse> getBooksPaged(int page, int size) {

        User user = userService.getLoggedInUser();
        Pageable pageable = PageRequest.of(page, size);

        return bookRepository.findByUserId(user.getId(), pageable)
                .map(this::toResponse);
    }

    public BookResponse getBookById(Long id) {

        User user = userService.getLoggedInUser();

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        return toResponse(book);
    }

    public BookResponse createBook(BookCreateRequest request, MultipartFile image) throws IOException {

        User user = userService.getLoggedInUser();

        validatePublishedYear(request.getPublishedYear());

        String uploadDir = "uploads/";
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

        Path path = Paths.get(uploadDir + fileName);
        Files.createDirectories(path.getParent());
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
                .user(user)
                .build();

        Book savedBook = bookRepository.save(book);

        return toResponse(savedBook);
    }

    public List<BookResponse> searchBooks(
            String query,
            String author,
            String bookName,
            String genre,
            String publisher,
            Integer publishedYear
    ) {

        User user = userService.getLoggedInUser();
        Long userId = user.getId();

        List<Book> books;

        if (query != null && !query.isBlank()) {
            books = bookRepository.searchByUser(userId, query);
        }
        else if (author != null) {
            books = bookRepository.findByUserIdAndAuthorContainingIgnoreCase(userId, author);
        }
        else if (bookName != null) {
            books = bookRepository.findByUserIdAndBookNameContainingIgnoreCase(userId, bookName);
        }
        else if (genre != null) {
            books = bookRepository.findByUserIdAndGenreContainingIgnoreCase(userId, genre);
        }
        else if (publisher != null) {
            books = bookRepository.findByUserIdAndPublisherContainingIgnoreCase(userId, publisher);
        }
        else if (publishedYear != null) {
            books = bookRepository.findByUserIdAndPublishedYearGreaterThanEqual(userId, publishedYear);
        }
        else {
            books = bookRepository.findByUserId(userId);
        }

        return books.stream()
                .map(this::toResponse)
                .toList();
    }

    public BookResponse updateBook(Long id, BookCreateRequest request, MultipartFile image) throws IOException {

        User user = userService.getLoggedInUser();

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        validatePublishedYear(request.getPublishedYear());

        if (image != null && !image.isEmpty()) {

            String uploadDir = "uploads/";
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(image.getInputStream(), path);

            String imageUrl = "/uploads/" + fileName;

            book.setCoverImageUrl(imageUrl);
        }

        book.setBookName(request.getBookName());
        book.setAuthor(request.getAuthor());
        book.setPublisher(request.getPublisher());
        book.setGenre(request.getGenre());
        book.setLanguage(request.getLanguage());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getTotalCopies());
        book.setDescription(request.getDescription());
        book.setPublishedYear(request.getPublishedYear());
        book.setIsbn(request.getIsbn());

        Book updated = bookRepository.save(book);

        return toResponse(updated);
    }

    public void deleteBook(Long id) {

        User user = userService.getLoggedInUser();

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        bookRepository.delete(book);
    }

    private void validatePublishedYear(Integer year) {
        int currentYear = Year.now().getValue();

        if (year > currentYear) {
            throw new IllegalArgumentException("Enter Valid Year");
        }
    }

    private BookResponse toResponse(Book book) {
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
}



