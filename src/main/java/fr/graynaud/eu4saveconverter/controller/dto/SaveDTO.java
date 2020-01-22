package fr.graynaud.eu4saveconverter.controller.dto;

import fr.graynaud.eu4saveconverter.service.object.save.Gamestate;

import java.time.LocalDate;

public class SaveDTO {

    private Gamestate gamestate;

    private LocalDate date;

    public SaveDTO(Gamestate gamestate, LocalDate date) {
        this.gamestate = gamestate;
        this.date = date;
    }

    public Gamestate getGamestate() {
        return gamestate;
    }

    public void setGamestate(Gamestate gamestate) {
        this.gamestate = gamestate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
