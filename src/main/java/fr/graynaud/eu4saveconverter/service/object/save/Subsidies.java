package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Subsidies extends QuantifyDatableRelation {

    private Long duration;

    public Subsidies(String content) {
        super(content);
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public void parse(String content) {
        super.parse(content);
        this.duration = ParseUtils.parseLong(content, "duration").orElse(null);
    }
}
