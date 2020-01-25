package fr.graynaud.eu4saveconverter.controller.dto;

import fr.graynaud.eu4saveconverter.common.Constants;
import fr.graynaud.eu4saveconverter.service.object.save.Country;
import fr.graynaud.eu4saveconverter.service.object.save.Dependency;
import fr.graynaud.eu4saveconverter.service.object.save.Gamestate;
import fr.graynaud.eu4saveconverter.service.object.save.Province;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SaveDTO {

    private LocalDate date;

    private List<CountryDTO> countries;

    public SaveDTO(Gamestate gamestate, LocalDate date) {
        this.date = date;
        this.countries = gamestate.getCountries()
                                  .keySet()
                                  .stream()
                                  .filter(country -> gamestate.getPlayersCountries().containsKey(country))
                                  .map(tag -> {
                                      String player = gamestate.getPlayersCountries().get(tag);
                                      Country country = gamestate.getCountries().get(tag);
                                      boolean isRevolutionTarget = tag.equals(gamestate.getRevolutionTarget());
                                      boolean isHreEmperor = tag.equals(gamestate.getEmpire().getEmperor());
                                      List<Province> provinces = gamestate.getProvinces().get(tag);
                                      boolean isDependency = gamestate.getDiplomacy()
                                                                      .getDependencies()
                                                                      .stream()
                                                                      .anyMatch(d -> tag.equals(d.getSecond()));
                                      boolean isMarch = isDependency && gamestate.getDiplomacy()
                                                                                 .getDependencies()
                                                                                 .stream()
                                                                                 .anyMatch(d -> Constants.MARCH_DEPENDENCY_TYPE
                                                                                                        .equals(d.getSubjectType()) &&
                                                                                                tag.equals(d.getSecond()));
                                      List<Dependency> dependencies = gamestate.getDiplomacy()
                                                                               .getDependencies()
                                                                               .stream()
                                                                               .filter(d -> tag.equals(d.getFirst()))
                                                                               .collect(Collectors.toList());
                                      List<Country> vassals = dependencies.stream()
                                              .filter(d -> "vassal".equals(d.getSubjectType()) || "client_vassal".equals(d.getSubjectType()))
                                              .map(d -> gamestate.getCountries().get(d.getSecond()))
                                              .collect(Collectors.toList());
                                      List<Country> marches = dependencies.stream()
                                              .filter(d -> "march".equals(d.getSubjectType()))
                                              .map(d -> gamestate.getCountries().get(d.getSecond()))
                                              .collect(Collectors.toList());
                                      List<Country> colonies = dependencies.stream()
                                              .filter(d -> "colony".equals(d.getSubjectType()))
                                              .map(d -> gamestate.getCountries().get(d.getSecond()))
                                              .collect(Collectors.toList());

                                      return new CountryDTO(tag, player, country, isRevolutionTarget, provinces, isHreEmperor,
                                                     isDependency, isMarch, vassals, marches, colonies);
                                  })
                                  .sorted(Comparator.comparing(CountryDTO::getGreatPowerScore).reversed())
                                  .collect(Collectors.toList());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<CountryDTO> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryDTO> countries) {
        this.countries = countries;
    }
}
