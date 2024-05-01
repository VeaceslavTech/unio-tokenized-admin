package at.unio.admin.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@Controller
public class FileServingController {

    @RequestMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) throws MalformedURLException {
        Path file = Paths.get(System.getProperty("user.dir") + "/storage/images", filename);
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok().body(resource);
    }

    @RequestMapping("/documents/{filename:.+}")
    public ResponseEntity<Resource> serveDocument(@PathVariable String filename) throws MalformedURLException {
        Path file = Paths.get(System.getProperty("user.dir") + "/storage/documents/", filename);
        Resource resource = new UrlResource(file.toUri());

        Map<String, String> mimeTypes = new HashMap<>();
        mimeTypes.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mimeTypes.put(".pdf", "application/pdf");

        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i);
        }

        String mimeType = mimeTypes.get(extension);
        if (mimeType == null) {
            try {
                mimeType = Files.probeContentType(file);
            } catch (IOException e) {
                throw new RuntimeException("Could not determine file type.", e);
            }
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + filename + "\"")  // Enable inline view
                .body(resource);
    }
}
