package org.acme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.dto.BookDto;
import org.acme.entity.Author;
import org.acme.entity.Book;
import org.acme.repository.AuthorRepository;
import org.acme.repository.BookRepository;

import java.util.List;

/**
 * Service class for managing books and authors in the application.
 *
 * <p>This class provides methods for performing CRUD operations on books, including retrieving,
 * saving, updating, and deleting books, along with handling the associated authors. The class
 * is annotated with {@link ApplicationScoped}, meaning it is a singleton within the application's
 * lifecycle.</p>
 */
@ApplicationScoped
public class BookService {
    private static final ObjectMapper mapper = new ObjectMapper();

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    /**
     * Constructs a new {@link BookService} with the specified repositories.
     *
     * @param bookRepository the repository used for accessing book data
     * @param authorRepository the repository used for accessing author data
     */
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * Retrieves a list of all books in the repository.
     *
     * @return a list of {@link Book} objects
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll().stream().toList();
    }

    /**
     * Retrieves the details of a specific book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return the {@link Book} object with the specified ID, or null if not found
     */
    public Book getABookDetail(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Saves a new book along with its author to the repository.
     *
     * <p>This method converts the provided {@link BookDto} into a {@link Book} and
     * {@link Author} objects and persists them in the respective repositories.</p>
     *
     * @param payload the data transfer object containing book and author information
     * @return the persisted {@link Book} object
     * @throws JsonProcessingException if there is an error processing the JSON
     */
    @Transactional
    public Book saveBook(BookDto payload) throws JsonProcessingException {
        Author author = mapper.readValue(mapper.writeValueAsString(payload.getAuthor()), Author.class);
        authorRepository.persist(author);

        Book book = mapper.readValue(mapper.writeValueAsString(payload), Book.class);
        book.setAuthor(author);
        bookRepository.persist(book);
        Log.infof("Successfully persisted book");
        return book;
    }

    /**
     * Updates an existing book's details.
     *
     * <p>This method retrieves a book by its ID, updates its fields based on the provided
     * {@link BookDto}, and persists the changes.</p>
     *
     * @param id the ID of the book to update
     * @param payload the data transfer object containing the updated book and author information
     * @return the updated {@link Book} object
     * @throws JsonProcessingException if there is an error processing the JSON
     * @throws IllegalArgumentException if the book with the specified ID does not exist
     */
    @Transactional
    public Book update(Long id, BookDto payload) throws JsonProcessingException {
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            throw new IllegalArgumentException("Book with ID " + id + " not found");
        }

        Author author = mapper.readValue(mapper.writeValueAsString(payload.getAuthor()), Author.class);
        authorRepository.persist(author);

        existingBook.setTitle(payload.getTitle());
        existingBook.setNumberOfPage(payload.getNumberOfPage());
        existingBook.setAuthor(author);

        bookRepository.persist(existingBook);
        Log.infof("Successfully updated book");
        return existingBook;
    }

    /**
     * Deletes a book from the repository.
     *
     * <p>This method retrieves a book by its ID and deletes it from the repository.</p>
     *
     * @param id the ID of the book to delete
     * @throws IllegalArgumentException if the book with the specified ID does not exist
     */
    @Transactional
    public void delete(Long id) {
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            throw new IllegalArgumentException("Book with ID " + id + " not found");
        }

        bookRepository.delete(existingBook);
        Log.infof("Successfully deleted book with ID %d", id);
    }
}
