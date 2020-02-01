package fr.graynaud.eu4saveconverter.controller;

import fr.graynaud.eu4saveconverter.controller.dto.SaveDTO;
import fr.graynaud.eu4saveconverter.service.SaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/save")
public class SaveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveController.class);

    private final SaveService saveService;

    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    @PostMapping(value = "/parse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveDTO> parse(@RequestPart(name = "save") MultipartFile save) throws IOException {
        return new ResponseEntity<>(saveService.saveToData(save), HttpStatus.OK);
    }
}
