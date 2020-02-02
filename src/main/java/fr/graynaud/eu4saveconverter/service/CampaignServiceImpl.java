package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.model.Campaign;
import fr.graynaud.eu4saveconverter.repository.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CampaignServiceImpl extends BaseServiceImpl<Campaign, CampaignRepository> implements CampaignService {

    public CampaignServiceImpl(CampaignRepository repository) {
        super(repository);
    }
}
