package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.Date;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Decree extends Eu4Object {

    private String decreeName;

    private Date decreeDate;

    public Decree(String content) {
        super(content);
    }

    public String getDecreeName() {
        return decreeName;
    }

    public void setDecreeName(String decreeName) {
        this.decreeName = decreeName;
    }

    public Date getDecreeDate() {
        return decreeDate;
    }

    public void setDecreeDate(Date decreeDate) {
        this.decreeDate = decreeDate;
    }

    @Override
    public void parse(String content) {
        this.decreeName = ParseUtils.parseString(content, "decree_name").orElse(null);
        this.decreeDate = ParseUtils.parseDate(content, "decree_date").orElse(null);
    }
}
