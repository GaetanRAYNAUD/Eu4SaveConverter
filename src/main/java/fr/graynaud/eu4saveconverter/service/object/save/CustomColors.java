package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.List;

public class CustomColors extends Eu4Object {

    private Long flag;

    private Long color;

    private Long symbolIndex;

    private List<Long> flagColors;

    public CustomColors(String content) {
        super(content);
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public Long getColor() {
        return color;
    }

    public void setColor(Long color) {
        this.color = color;
    }

    public Long getSymbolIndex() {
        return symbolIndex;
    }

    public void setSymbolIndex(Long symbolIndex) {
        this.symbolIndex = symbolIndex;
    }

    public List<Long> getFlagColors() {
        return flagColors;
    }

    public void setFlagColors(List<Long> flagColors) {
        this.flagColors = flagColors;
    }

    @Override
    public void parse(String content) {
        this.flag = ParseUtils.parseLong(content, "flag").orElse(0L);
        this.color = ParseUtils.parseLong(content, "color").orElse(0L);
        this.symbolIndex = ParseUtils.parseLong(content, "symbol_index").orElse(0L);
        this.flagColors = ParseUtils.parseLineLong(content, "flag_colors");
    }
}
