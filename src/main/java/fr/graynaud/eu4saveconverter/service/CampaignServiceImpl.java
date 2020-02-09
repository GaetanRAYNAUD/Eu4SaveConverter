package fr.graynaud.eu4saveconverter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.graynaud.eu4saveconverter.common.Utils;
import fr.graynaud.eu4saveconverter.common.exception.CampaignReadException;
import fr.graynaud.eu4saveconverter.common.exception.PathAlreadyExistException;
import fr.graynaud.eu4saveconverter.config.properties.WebProperties;
import fr.graynaud.eu4saveconverter.controller.dto.CampaignDTO;
import fr.graynaud.eu4saveconverter.controller.dto.SaveDTO;
import fr.graynaud.eu4saveconverter.model.Campaign;
import fr.graynaud.eu4saveconverter.repository.CampaignRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class CampaignServiceImpl extends BaseServiceImpl<Campaign, CampaignRepository> implements CampaignService {

    private final WebProperties webProperties;

    private final ObjectMapper objectMapper;

    private final SecurityService securityService;

    private final SaveService saveService;

    public CampaignServiceImpl(CampaignRepository repository, WebProperties webProperties, ObjectMapper objectMapper, SecurityService securityService, SaveService saveService) {
        super(repository);
        this.webProperties = webProperties;
        this.objectMapper = objectMapper;
        this.securityService = securityService;
        this.saveService = saveService;
    }

    @Override
    public Optional<Campaign> getByPath(String path) {
        return this.repository.getFirstByPath(path);
    }

    @Override
    public Optional<CampaignDTO> getDTOByPath(String path) {
        Optional<Campaign> campaign = getByPath(path);

        if (campaign.isEmpty()) {
            return Optional.empty();
        }

        File campaignFile = new File(
                this.webProperties.getDataFolderPath() + File.separator + campaign.get().toFileName());

        if (!campaignFile.exists() || !campaignFile.canRead()) {
            return Optional.empty();
        }

        try {
            return Optional.of(this.objectMapper.readValue(campaignFile, CampaignDTO.class));
        } catch (IOException e) {
            throw new CampaignReadException(e);
        }
    }

    @Override
    public String create(String name, MultipartFile save) throws IOException {
        Campaign campaign = new Campaign(name, Utils.generatePath(name), securityService.getCurrentUser());

        try {
            create(campaign);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null &&
                (e.getMessage().contains("campaign.name") || e.getMessage().contains("campaign.path"))) {
                throw new PathAlreadyExistException(campaign.getPath());
            }
        }

        SaveDTO saveDTO = saveService.saveToData(save);
        campaign.addSave();
        update(campaign);

        CampaignDTO campaignDTO = new CampaignDTO(campaign, saveDTO);
        saveToDataFile(campaignDTO);

        return campaign.getPath();
    }

    private synchronized void saveToDataFile(CampaignDTO campaignDTO) throws IOException {
        ObjectWriter writer = this.objectMapper.writer();
        writer.writeValue(new File(
                this.webProperties.getDataFolderPath() + File.separator + campaignDTO.toFileName()), campaignDTO);
    }
}
