package fr.graynaud.eu4saveconverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.graynaud.eu4saveconverter.model.Campaign;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

@Schema(name = "Campaign")
public class CampaignDTO {

    private Integer id;

    private String name;

    private String path;

    private SortedSet<SaveDTO> saves;

    public CampaignDTO() {
    }

    public CampaignDTO(Campaign campaign) {
        this.id = campaign.getId();
        this.name = campaign.getName();
        this.path = campaign.getPath();
    }

    public CampaignDTO(Campaign campaign, SaveDTO save) {
        this(campaign);
        this.saves = new TreeSet<>(Collections.singletonList(save));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SortedSet<SaveDTO> getSaves() {
        return saves;
    }

    public void setSaves(SortedSet<SaveDTO> saves) {
        this.saves = saves;
    }

    public LocalDate getCurrentDate() {
        return this.saves == null ? null : this.saves.first().getCurrentDate();
    }

    public LocalDate getStartDate() {
        return this.saves == null ? null : this.saves.first().getCampaignStartDate();
    }

    public Integer getNbSaves() {
        return this.saves.size();
    }

    @JsonIgnore
    public String toFileName() {
        return this.path + "_" + getNbSaves() + ".json";
    }
}
