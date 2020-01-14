package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class WarHistory extends Eu4Object {

    public WarHistory(String content) {
        super(content);
    }

    @Override
    public void parse(String content) {

    }
}
