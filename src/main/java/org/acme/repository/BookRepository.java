package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Book;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {
}
