package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.model.Campaign;
import org.springframework.web.multipart.MultipartFile;

public interface CampaignService extends BaseService<Campaign> {

    void create(String name, MultipartFile save);
}
