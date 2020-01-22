package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.time.LocalDate;

public class Emperor extends Eu4Object {

    private Long id;

    private String country;

    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public void parse(String content) {
        this.id = ParseUtils.parseLong(content, "id").orElse(null);
        this.country = ParseUtils.parseString(content, "country").orElse(null);
        this.date = ParseUtils.parseDate(content, "date").orElse(null);
    }
}
