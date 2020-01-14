package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public abstract class Empire extends Eu4Object {

    private String emperor;

    private Double imperialInfluence;

    private Long reformLevel;

    private List<Emperor> oldEmperors;

    public Empire(String content) {
        super(content);
    }

    public String getEmperor() {
        return emperor;
    }

    public void setEmperor(String emperor) {
        this.emperor = emperor;
    }

    public Double getImperialInfluence() {
        return imperialInfluence;
    }

    public void setImperialInfluence(Double imperialInfluence) {
        this.imperialInfluence = imperialInfluence;
    }

    public Long getReformLevel() {
        return reformLevel;
    }

    public void setReformLevel(Long reformLevel) {
        this.reformLevel = reformLevel;
    }

    public List<Emperor> getOldEmperors() {
        return oldEmperors;
    }

    public void setOldEmperors(List<Emperor> oldEmperors) {
        this.oldEmperors = oldEmperors;
    }

    @Override
    public void parse(String content) {
        this.emperor = ParseUtils.parseString(content, "emperor").orElse(null);
        this.imperialInfluence = ParseUtils.parseDouble(content, "imperial_influence").orElse(null);
        this.reformLevel = ParseUtils.parseLong(content, "reform_level").orElse(null);
        this.oldEmperors = ParseUtils.getListSameObject(content, "old_emperor")
                                     .stream()
                                     .map(Emperor::new)
                                     .collect(Collectors.toList());
    }
}
