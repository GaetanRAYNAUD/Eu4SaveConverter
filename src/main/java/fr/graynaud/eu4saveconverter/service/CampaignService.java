package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.controller.dto.CampaignDTO;
import fr.graynaud.eu4saveconverter.model.Campaign;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface CampaignService extends BaseService<Campaign> {

    Optional<Campaign> getByPath(String path);

    Optional<CampaignDTO> getDTOByPath(String path);

    String create(String name, MultipartFile save) throws IOException;
}
