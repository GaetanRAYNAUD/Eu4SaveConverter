package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class GreatPower extends Eu4Object {

    private Long rank;

    private String country;

    private Double value;

    public GreatPower(String content) {
        super(content);
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public void parse(String content) {
        this.rank = ParseUtils.parseLong(content, "rank").orElse(null);
        this.country = ParseUtils.parseString(content, "country").orElse(null);
        this.value = ParseUtils.parseDouble(content, "value").orElse(null);
    }
}
