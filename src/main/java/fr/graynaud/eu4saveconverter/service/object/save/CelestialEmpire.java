package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class CelestialEmpire extends Empire {

    private Decree decree;

    public CelestialEmpire(String content) {
        super(content);
    }

    public Decree getDecree() {
        return decree;
    }

    public void setDecree(Decree decree) {
        this.decree = decree;
    }

    @Override
    public void parse(String content) {
        int i = content.indexOf("\ncelestial_empire={");
        content = ParseUtils.getNextObject(content, i + 1);
        super.parse(content);

        if (content.contains("decree={")) {
            this.decree = new Decree(content);
        }
    }
}
