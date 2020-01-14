package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public abstract class Eu4Object {

    Eu4Object(String content) {
        parse(content);
    }

    public abstract void parse(String content);
}
