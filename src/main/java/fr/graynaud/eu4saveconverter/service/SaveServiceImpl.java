package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.common.Constants;
import fr.graynaud.eu4saveconverter.common.FileUtils;
import fr.graynaud.eu4saveconverter.common.ParseUtils;
import fr.graynaud.eu4saveconverter.common.exception.MissingFileException;
import fr.graynaud.eu4saveconverter.controller.dto.SaveDTO;
import fr.graynaud.eu4saveconverter.service.object.save.Gamestate;
import fr.graynaud.eu4saveconverter.service.object.save.SaveFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Service
public class SaveServiceImpl implements SaveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveServiceImpl.class);

    //Todo custom nations ideas index: 81 = 7.5% FL/lvl

    @Override
    public SaveDTO saveToData(MultipartFile multipartFile) throws IOException {
        Map<SaveFile, String> contents = FileUtils.unZipSave(multipartFile);
        String gamestateContent = contents.get(SaveFile.GAMESTATE);
        String metaContent = contents.get(SaveFile.META);

        if (gamestateContent == null) {
            throw new MissingFileException(SaveFile.GAMESTATE.name());
        }

        if (metaContent == null) {
            throw new MissingFileException(SaveFile.META.name());
        }


        if (gamestateContent.startsWith(Constants.STARTING_TEXT)) {
            gamestateContent = gamestateContent.substring(Constants.STARTING_TEXT.length());
        }

        Gamestate gamestate = new Gamestate(gamestateContent);
        LocalDate date = ParseUtils.parseDate(metaContent, "date").orElse(null);

        return new SaveDTO(gamestate, date);
    }
}
