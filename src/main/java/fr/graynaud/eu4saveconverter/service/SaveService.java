package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.service.object.save.Gamestate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SaveService {

    Gamestate saveToData(MultipartFile multipartFile) throws IOException;
}
