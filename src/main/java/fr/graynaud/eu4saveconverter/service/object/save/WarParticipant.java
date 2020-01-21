package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.List;

public class WarParticipant extends Eu4Object {

    private Double value;

    private String tag;

    private Boolean promisedLand;

    private List<Long> losses;

    public WarParticipant(String content) {
        super(content);
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getPromisedLand() {
        return promisedLand;
    }

    public void setPromisedLand(Boolean promisedLand) {
        this.promisedLand = promisedLand;
    }

    public List<Long> getLosses() {
        return losses;
    }

    public void setLosses(List<Long> losses) {
        this.losses = losses;
    }

    @Override
    public void parse(String content) {
        this.value = ParseUtils.parseDouble(content, "value").orElse(null);
        this.tag = ParseUtils.parseString(content, "tag").orElse(null);
        this.promisedLand = ParseUtils.parseBoolean(content, "promised_land").orElse(null);
        this.losses = ParseUtils.parseLineLong(content, "members");
    }
}
