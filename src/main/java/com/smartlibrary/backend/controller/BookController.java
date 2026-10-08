package com.smartlibrary.backend.controller;

import com.smartlibrary.backend.model.Book;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    // Constructor - adding sample books
    public BookController() {

        books.add(new Book(
                1L,
                "Java Programming",
                "Herbert Schildt",
                "Programming",
                true
        ));

        books.add(new Book(
                2L,
                "Database Management Systems",
                "Raghu Ramakrishnan",
                "Database",
                true
        ));

        books.add(new Book(
                3L,
                "Computer Networks",
                "Andrew S. Tanenbaum",
                "Networking",
                false
        ));
    }

    // GET - Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    // GET - Get one book by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {

        for (Book book : books) {

            if (book.getId().equals(id)) {
                return book;
            }
        }

        return null;
    }

    // POST - Add a new book
    @PostMapping
    public Book addBook(@RequestBody Book book) {

        Long newId = books.stream()
                .mapToLong(Book::getId)
                .max()
                .orElse(0) + 1;

        book.setId(newId);

        books.add(book);

        return book;
    }

    // PUT - Update an existing book
    @PutMapping("/{id}")
    public Book updateBook(
            @PathVariable Long id,
            @RequestBody Book updatedBook) {

        for (Book book : books) {

            if (book.getId().equals(id)) {

                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setCategory(updatedBook.getCategory());
                book.setAvailable(updatedBook.isAvailable());

                return book;
            }
        }

        return null;
    }

    // DELETE - Delete a book
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {

        boolean removed = books.removeIf(
                book -> book.getId().equals(id)
        );

        if (removed) {
            return "Book deleted successfully";
        }

        return "Book not found";
    }
}
