package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.common.Utils;
import fr.graynaud.eu4saveconverter.common.exception.PathAlreadyExistException;
import fr.graynaud.eu4saveconverter.model.Campaign;
import fr.graynaud.eu4saveconverter.repository.CampaignRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class CampaignServiceImpl extends BaseServiceImpl<Campaign, CampaignRepository> implements CampaignService {

    private final SecurityService securityService;

    public CampaignServiceImpl(CampaignRepository repository, SecurityService securityService) {
        super(repository);
        this.securityService = securityService;
    }

    @Override
    public void create(String name, MultipartFile save) {
        Campaign campaign = new Campaign(name, Utils.generatePath(name), securityService.getCurrentUser());

        try {
            create(campaign);
        } catch (DataIntegrityViolationException e) {
            throw new PathAlreadyExistException(campaign.getPath());
        }
    }
}
