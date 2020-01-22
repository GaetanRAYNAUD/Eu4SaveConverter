package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.controller.dto.SaveDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SaveService {

    SaveDTO saveToData(MultipartFile multipartFile) throws IOException;
}
