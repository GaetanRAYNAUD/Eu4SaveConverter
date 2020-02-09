package fr.graynaud.eu4saveconverter.controller.campaign;

import fr.graynaud.eu4saveconverter.controller.ErrorCode;
import fr.graynaud.eu4saveconverter.controller.ErrorObject;
import fr.graynaud.eu4saveconverter.controller.dto.CampaignDTO;
import fr.graynaud.eu4saveconverter.service.CampaignService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/public/api/campaign")
@Tag(name = "Campaign")
public class PublicCampaignController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublicCampaignController.class);

    private final CampaignService campaignService;

    public PublicCampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping(value = "/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCampaign(@PathVariable String path) {
        Optional<CampaignDTO> campaign = campaignService.getDTOByPath(path);

        if (campaign.isEmpty()) {
            return new ResponseEntity<>(new ErrorObject(ErrorCode.CAMPAIGN_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(campaign.get(), HttpStatus.OK);
    }
}
