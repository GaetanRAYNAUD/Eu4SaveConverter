package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.List;
import java.util.stream.Collectors;

public class War extends Eu4Object {

    private String name;

    private List<WarParticipant> participants;

    private List<String> persistentAttackers;

    private List<String> persistentDefenders;

    private String originalAttacker;

    private String originalDefender;

    public War(String content) {
        super(content);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WarParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<WarParticipant> participants) {
        this.participants = participants;
    }

    public List<String> getPersistentAttackers() {
        return persistentAttackers;
    }

    public void setPersistentAttackers(List<String> persistentAttackers) {
        this.persistentAttackers = persistentAttackers;
    }

    public List<String> getPersistentDefenders() {
        return persistentDefenders;
    }

    public void setPersistentDefenders(List<String> persistentDefenders) {
        this.persistentDefenders = persistentDefenders;
    }

    public String getOriginalAttacker() {
        return originalAttacker;
    }

    public void setOriginalAttacker(String originalAttacker) {
        this.originalAttacker = originalAttacker;
    }

    public String getOriginalDefender() {
        return originalDefender;
    }

    public void setOriginalDefender(String originalDefender) {
        this.originalDefender = originalDefender;
    }

    @Override
    public void parse(String content) {
        this.name = ParseUtils.parseString(content, "name").orElse(null);
        this.participants = ParseUtils.getListSameObject(content, "participants")
                                      .stream()
                                      .map(WarParticipant::new)
                                      .collect(Collectors.toList());
        this.persistentAttackers = ParseUtils.parseLineString(content, "persistent_attackers");
        this.persistentDefenders = ParseUtils.parseLineString(content, "persistent_defenders");
        this.originalAttacker = ParseUtils.parseString(content, "original_attacker").orElse(null);
        this.originalDefender = ParseUtils.parseString(content, "original_defender").orElse(null);
    }
}
