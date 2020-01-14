package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.Date;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class EndDatableRelation extends DatableRelation {

    private Date endDate;

    public EndDatableRelation(String content) {
        super(content);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public void parse(String content) {
        super.parse(content);
        this.endDate = ParseUtils.parseDate(content, "end_date").orElse(null);
    }
}
