package fr.graynaud.eu4saveconverter.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class FileUtils {

    private FileUtils() {
    }

    public static String unZipSave(MultipartFile multipartFile) throws IOException {

        try (ZipInputStream zipInputStream = new ZipInputStream(multipartFile.getInputStream())) {
            ZipEntry entry = zipInputStream.getNextEntry();

            while (entry != null) {
                if (!entry.isDirectory() && entry.getName().equals("gamestate")) {
                    return new String(zipInputStream.readNBytes((int) entry.getSize()), "Windows-1252");
                }

                entry = zipInputStream.getNextEntry();
            }

            return null;
        }
    }
}
