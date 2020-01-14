package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Hre extends Empire {

    private List<String> electors;

    public Hre(String content) {
        super(content);
    }

    public List<String> getElectors() {
        return electors;
    }

    public void setElectors(List<String> electors) {
        this.electors = electors;
    }

    @Override
    public void parse(String content) {
        int i = content.indexOf("\nempire={");
        content = ParseUtils.getNextObject(content, i + 1);
        super.parse(content);
        this.electors = ParseUtils.parseLineString(content, "electors");
    }
}
