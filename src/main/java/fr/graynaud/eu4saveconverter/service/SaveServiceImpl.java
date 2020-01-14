package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.common.Constants;
import fr.graynaud.eu4saveconverter.common.FileUtils;
import fr.graynaud.eu4saveconverter.service.object.save.Gamestate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SaveServiceImpl implements SaveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveServiceImpl.class);

    //Todo war's history !!!!

    @Override
    public Gamestate saveToData(MultipartFile multipartFile) throws IOException {
        String content = FileUtils.unZipSave(multipartFile);

        if (content == null) {
            return null;
        }

        if (content.startsWith(Constants.STARTING_TEXT)) {
            content = content.substring(Constants.STARTING_TEXT.length());
        }

        return new Gamestate(content);
    }
}
