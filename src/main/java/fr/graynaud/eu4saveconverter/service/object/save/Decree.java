package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.time.LocalDate;

public class Decree extends Eu4Object {

    private String decreeName;

    private LocalDate decreeDate;

    public Decree(String content) {
        super(content);
    }

    public String getDecreeName() {
        return decreeName;
    }

    public void setDecreeName(String decreeName) {
        this.decreeName = decreeName;
    }

    public LocalDate getDecreeDate() {
        return decreeDate;
    }

    public void setDecreeDate(LocalDate decreeDate) {
        this.decreeDate = decreeDate;
    }

    @Override
    public void parse(String content) {
        this.decreeName = ParseUtils.parseString(content, "decree_name").orElse(null);
        this.decreeDate = ParseUtils.parseDate(content, "decree_date").orElse(null);
    }
}
