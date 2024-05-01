package at.unio.admin.data.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class LocalStorage {
    public static String storeFile(String path, String filename, InputStream inputStream) throws IOException {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File newFile = new File(directory, filename);
        Files.copy(inputStream, newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return newFile.getAbsolutePath();
    }
}
