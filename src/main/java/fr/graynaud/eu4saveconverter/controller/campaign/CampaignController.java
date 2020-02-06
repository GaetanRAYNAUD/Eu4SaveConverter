package fr.graynaud.eu4saveconverter.controller.campaign;

import fr.graynaud.eu4saveconverter.controller.dto.CampaignCreateForm;
import fr.graynaud.eu4saveconverter.controller.dto.SaveDTO;
import fr.graynaud.eu4saveconverter.service.CampaignService;
import fr.graynaud.eu4saveconverter.service.SaveService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/api/campaign")
@Tag(name = "Campaign", description = "Manage campaigns")
public class CampaignController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignController.class);

    private final SaveService saveService;

    private final CampaignService campaignService;

    public CampaignController(SaveService saveService, CampaignService campaignService) {
        this.saveService = saveService;
        this.campaignService = campaignService;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestPart(name = "save") MultipartFile save, CampaignCreateForm campaignCreateForm) throws IOException {
        campaignService.create(campaignCreateForm.getName(), save);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/parse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveDTO> parse(@RequestPart(name = "save") MultipartFile save) throws IOException {
        return new ResponseEntity<>(saveService.saveToData(save), HttpStatus.OK);
    }
}
