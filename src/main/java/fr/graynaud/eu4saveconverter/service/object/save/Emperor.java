package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.Date;

public class Emperor extends Eu4Object {

    private Long id;

    private String country;

    private Date date;

    public Emperor(String content) {
        super(content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void parse(String content) {
        this.id = ParseUtils.parseLong(content, "id").orElse(null);
        this.country = ParseUtils.parseString(content, "country").orElse(null);
        this.date = ParseUtils.parseDate(content, "date").orElse(null);
    }
}
