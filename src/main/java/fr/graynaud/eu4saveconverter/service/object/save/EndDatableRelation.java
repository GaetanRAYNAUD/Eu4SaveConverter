package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.time.LocalDate;

public class EndDatableRelation extends DatableRelation {

    private LocalDate endDate;

    public EndDatableRelation(String content) {
        super(content);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public void parse(String content) {
        super.parse(content);
        this.endDate = ParseUtils.parseDate(content, "end_date").orElse(null);
    }
}
