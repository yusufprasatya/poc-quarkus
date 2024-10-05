package org.acme.restclient;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * A REST client for communicating with external microservices.
 *
 * <p>This class is annotated with {@link RestClient} to enable MicroProfile Rest Client
 * features for making HTTP requests to external services. It is intended to be used
 * for sending and receiving data from RESTful APIs.</p>
 *
 * <p>The class should include methods for defining specific endpoints and handling
 * the data transfer objects (DTOs) associated with the requests and responses.</p>
 */
@ApplicationScoped
@RestClient
public class MicroServiceClient {
    // Add methods for invoking REST endpoints here
}
