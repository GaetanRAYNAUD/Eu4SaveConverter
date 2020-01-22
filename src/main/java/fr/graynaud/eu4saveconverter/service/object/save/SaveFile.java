package fr.graynaud.eu4saveconverter.service.object.save;

import java.util.Optional;

public enum SaveFile {
    AI, GAMESTATE, META;

    public static Optional<SaveFile> getByName(String name) {
        for (SaveFile saveFile : values()) {
            if (saveFile.name().equalsIgnoreCase(name)) {
                return Optional.of(saveFile);
            }
        }

        return Optional.empty();
    }

}
