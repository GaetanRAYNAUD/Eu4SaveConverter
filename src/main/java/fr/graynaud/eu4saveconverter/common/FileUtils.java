package fr.graynaud.eu4saveconverter.common;

import fr.graynaud.eu4saveconverter.service.object.save.SaveFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class FileUtils {

    private FileUtils() {
    }

    public static Map<SaveFile, String> unZipSave(MultipartFile multipartFile) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(multipartFile.getInputStream())) {
            ZipEntry entry = zipInputStream.getNextEntry();
            Map<SaveFile, String> contents = new HashMap<>();

            while (entry != null) {
                if (!entry.isDirectory()) {
                    Optional<SaveFile> saveFile = SaveFile.getByName(entry.getName());

                    if (saveFile.isPresent()) {
                        contents.put(saveFile.get(), new String(zipInputStream.readNBytes((int) entry.getSize()), "Windows-1252"));
                    }
                }

                entry = zipInputStream.getNextEntry();
            }

            return contents;
        }
    }
}
