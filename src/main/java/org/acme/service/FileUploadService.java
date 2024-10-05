package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.*;
import java.util.Base64;

@ApplicationScoped
public class FileUploadService {

    /**
     * Saves the contents of an input stream to a specified file path and returns the file's content as a Base64-encoded string.
     *
     * <p>This method reads data from the provided {@link InputStream} and writes it to a file at the given {@code filePath}.
     * After successfully saving the file, it reads the file's contents and encodes it to a Base64 string for easy transmission or storage.
     *
     * @param inputStream the input stream containing the data to be saved
     * @param filePath the path where the file should be saved
     * @return a Base64-encoded string representation of the file's contents
     * @throws IOException if an I/O error occurs while saving the file or reading from it
     * @throws FileNotFoundException if the file specified by {@code filePath} cannot be created or opened
     */
    public String saveFile(InputStream inputStream, String filePath) throws IOException, FileNotFoundException {
        byte[] buffer = new byte[1024];
        int bytesRead;

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        File savedFile = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(savedFile)) {
            byte[] fileBytes = fileInputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(fileBytes);
        }
    }
}
