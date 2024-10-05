# Book Management API

This project provides a RESTful API for managing books, allowing users to perform operations such as saving, updating, deleting, and retrieving book information. Additionally, it supports file uploads.

## Table of Contents

- [Overview](#overview)
- [Technologies](#technologies)
- [API Endpoints](#api-endpoints)
- [Setup Instructions](#setup-instructions)
- [Usage Examples](#usage-examples)
- [License](#license)

## Overview

The **Book Management API** is designed to manage book entities and facilitate file uploads. It allows users to:

- Save new books
- Retrieve all books or specific books by ID
- Update existing book information
- Delete books
- Upload files

## Technologies

- **Java** - Programming language used for backend development.
- **Quarkus** - A Kubernetes-native Java stack tailored for GraalVM and OpenJDK HotSpot.
- **Jakarta EE** - For building the RESTful API.
- **MicroProfile** - For configuration and REST client functionalities.
- **Jackson** - For JSON serialization and deserialization.

## API Endpoints

### Save a Book

- **Endpoint**: `/api/v1/basic/save`
- **Method**: `POST`
- **Request Body**: `BookDto` (contains book details)
- **Response**: Returns the saved book in a `ResponseDto`.

### Retrieve All Books

- **Endpoint**: `/api/v1/basic`
- **Method**: `GET`
- **Response**: Returns a list of all books in a `ResponseDto`.

### Retrieve a Book by ID (Query Parameter)

- **Endpoint**: `/api/v1/basic/by-param`
- **Method**: `GET`
- **Query Parameters**: `id` (ID of the book)
- **Response**: Returns the book details in a `ResponseDto`.

### Retrieve a Book by ID (Path Parameter)

- **Endpoint**: `/api/v1/basic/by-path/{id}`
- **Method**: `GET`
- **Path Parameter**: `id` (ID of the book)
- **Response**: Returns the book details in a `ResponseDto`.

### Update a Book

- **Endpoint**: `/api/v1/basic/update/{id}`
- **Method**: `PUT`
- **Path Parameter**: `id` (ID of the book to update)
- **Request Body**: `BookDto` (updated book details)
- **Response**: Returns the updated book in a `ResponseDto`.

### Delete a Book

- **Endpoint**: `/api/v1/basic/{id}`
- **Method**: `DELETE`
- **Path Parameter**: `id` (ID of the book to delete)
- **Response**: Indicates success of the operation in a `ResponseDto`.

### Upload a File

- **Endpoint**: `/api/v1/basic/upload`
- **Method**: `POST`
- **Request Body**: Multipart form data (includes the file and its name)
- **Response**: Returns the Base64-encoded content of the uploaded file in a `ResponseDto`.

## Setup Instructions

### Prerequisites

- Java 11 or higher
- Maven installed
- An IDE (e.g., IntelliJ IDEA, Eclipse)

### Clone the Repository

```bash
git clone <https://github.com/yusufprasatya/poc-quarkus.git>
cd <poc-quarkus>
```

### Build the Project
```bash
mvn clean install
```

### Run the Application
```bash
mvn quarkus:dev
```

The API will be accessible at http://localhost:8080.
## Usage Examples
### Save a Book
```bash
curl -X POST "http://localhost:8080/api/v1/basic/save" -H "Content-Type: application/json" -d '{
  "title": "Java Concurrency in Practice",
  "numberOfPage": 368,
  "author": {
    "name": "Brian Goetz",
    "email": "brian.goetz@example.com"
  }
}'
````
### Retrieve All Books
```bash
curl -X GET "http://localhost:8080/api/v1/basic"
````

### Retrieve a Book by ID
```bash
curl -X GET "http://localhost:8080/api/v1/basic/by-param?id=1"
````

### Upload a File
```bash
curl -X POST "http://localhost:8080/api/v1/basic/upload" -F "file=@/path/to/file.pdf" -F "fileName=file.pdf"
````

## License
### Changes Made:
- Added a **Bash Commands** section with code blocks for clarity, including commands for cloning the repository, building the project, and running the application.

Feel free to adjust any content as needed!


