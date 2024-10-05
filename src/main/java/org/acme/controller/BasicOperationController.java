package org.acme.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.BookDto;
import org.acme.dto.ResponseDto;
import org.acme.entity.Book;
import org.acme.service.BookService;
import org.acme.service.FileUploadService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.RestForm;

import java.io.*;
import java.util.List;

/**
 * REST controller for performing basic operations related to books.
 *
 * <p>This controller provides endpoints for saving, updating, deleting, and retrieving books.
 * It also includes an endpoint for uploading files. The class is annotated with
 * {@link Path} to define the base URI for the RESTful web service.</p>
 */
@Path("/api/v1/basic")
@Produces(MediaType.APPLICATION_JSON)
public class BasicOperationController {

    @ConfigProperty(name = "file.upload.directory")
    String uploadDirectory;

    private final BookService bookService;
    private final FileUploadService fileUploadService;

    /**
     * Constructs a new {@link BasicOperationController} with the specified services.
     *
     * @param bookService the service for managing book operations
     * @param fileUploadService the service for handling file uploads
     */
    public BasicOperationController(BookService bookService, FileUploadService fileUploadService) {
        this.bookService = bookService;
        this.fileUploadService = fileUploadService;
    }

    /**
     * Saves a new book to the repository.
     *
     * <p>This endpoint accepts a {@link BookDto} object in the request body and returns
     * the saved book wrapped in a {@link ResponseDto} object.</p>
     *
     * @param payload the data transfer object containing book information
     * @return a {@link Response} containing the saved {@link Book}
     */
    @Path("/save")
    @POST
    public Response saveBook(BookDto payload) {
        try {
            Book bookSaved = bookService.saveBook(payload);
            return Response.ok(ResponseDto.success(bookSaved)).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(ResponseDto.error(e.getMessage()))
                    .build();
        }
    }

    /**
     * Retrieves a list of all books.
     *
     * <p>This endpoint returns a list of all books in the repository wrapped in a
     * {@link ResponseDto} object.</p>
     *
     * @return a {@link Response} containing a list of {@link Book}
     */
    @GET
    public Response findAllBooks() {
        try {
            List<Book> booksList = bookService.getAllBooks();
            return Response.ok(ResponseDto.success(booksList)).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(ResponseDto.error(e.getMessage()))
                    .build();
        }
    }

    /**
     * Retrieves the details of a specific book by its ID using a query parameter.
     *
     * @param id the ID of the book to retrieve
     * @return a {@link Response} containing the details of the specified {@link Book}
     */
    @GET
    @Path("/by-param")
    public Response findByParamId(@QueryParam("id") Long id) {
        try {
            Book booksDetail = bookService.getABookDetail(id);
            return Response.ok(ResponseDto.success(booksDetail)).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(ResponseDto.error(e.getMessage()))
                    .build();
        }
    }

    /**
     * Retrieves the details of a specific book by its ID using a path parameter.
     *
     * @param id the ID of the book to retrieve
     * @return a {@link Response} containing the details of the specified {@link Book}
     */
    @GET
    @Path("/by-path/{id}")
    public Response findByPathId(@PathParam("id") Long id) {
        try {
            Book booksDetail = bookService.getABookDetail(id);
            return Response.ok(ResponseDto.success(booksDetail)).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(ResponseDto.error(e.getMessage()))
                    .build();
        }
    }

    /**
     * Updates the details of an existing book by its ID.
     *
     * <p>This endpoint accepts a {@link BookDto} object in the request body and updates
     * the specified book, returning the updated book wrapped in a {@link ResponseDto} object.</p>
     *
     * @param id the ID of the book to update
     * @param payload the data transfer object containing updated book information
     * @return a {@link Response} containing the updated {@link Book}
     */
    @Path("/update/{id}")
    @PUT
    public Response update(@PathParam("id") Long id, BookDto payload) {
        try {
            Book bookUpdated = bookService.update(id, payload);
            return Response.ok(ResponseDto.success(bookUpdated)).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(ResponseDto.error(e.getMessage()))
                    .build();
        }
    }

    /**
     * Deletes a book from the repository by its ID.
     *
     * @param id the ID of the book to delete
     * @return a {@link Response} indicating the success of the operation
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            bookService.delete(id);
            return Response.ok(ResponseDto.success(true)).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(ResponseDto.error(e.getMessage()))
                    .build();
        }
    }

    /**
     * Uploads a file to the server.
     *
     * <p>This endpoint accepts a file and its name as multipart form data. It saves the file
     * to the configured upload directory and returns a response with the Base64-encoded file content.</p>
     *
     * @param file the input stream of the file to upload
     * @param fileName the name of the file to save
     * @return a {@link Response} indicating the result of the file upload
     */
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@RestForm("file") InputStream file, @RestForm String fileName) {
        if (file == null) {
            return Response.status(400)
                    .entity(ResponseDto.error("Bad Request"))
                    .build();
        }

        try {
            String filePath = uploadDirectory + File.separator + fileName;
            String response = fileUploadService.saveFile(file, filePath);
            return Response.status(Response.Status.OK)
                    .entity(ResponseDto.success(response))
                    .build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity(ResponseDto.error(e.getMessage()))
                    .build();
        }
    }
}
