package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Author;

@ApplicationScoped
public class AuthorRepository implements PanacheRepository<Author> {
}
