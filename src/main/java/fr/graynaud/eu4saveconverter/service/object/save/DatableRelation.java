package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.Date;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class DatableRelation extends Eu4Object {

    private String first;

    private String second;

    private Date startDate;

    public DatableRelation(String content) {
        super(content);
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public void parse(String content) {
        this.first = ParseUtils.parseString(content, "first").orElse(null);
        this.second = ParseUtils.parseString(content, "second").orElse(null);
        this.startDate = ParseUtils.parseDate(content, "start_date").orElse(null);
    }
}
