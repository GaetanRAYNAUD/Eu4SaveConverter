package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

public class State extends Eu4Object {

    private String country;

    private Double prosperity;

    private String edit;

    public State(String content) {
        super(content);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getProsperity() {
        return prosperity;
    }

    public void setProsperity(Double prosperity) {
        this.prosperity = prosperity;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    @Override
    public void parse(String content) {
        this.country = ParseUtils.parseString(content, "country").orElse(null);
        this.prosperity = ParseUtils.parseDouble(content, "prosperity").orElse(null);
        this.edit = ParseUtils.parseString(content, "which").orElse(null);
    }
}
