package fr.graynaud.eu4saveconverter.repository;

import fr.graynaud.eu4saveconverter.model.Campaign;

import java.util.Optional;

public interface CampaignRepository extends BaseRepository<Campaign> {

    Optional<Campaign> getFirstByPath(String path);
}
