package fr.graynaud.eu4saveconverter.service.object.save;

public abstract class Eu4Object {

    Eu4Object(String content) {
        parse(content);
    }

    public abstract void parse(String content);
}
