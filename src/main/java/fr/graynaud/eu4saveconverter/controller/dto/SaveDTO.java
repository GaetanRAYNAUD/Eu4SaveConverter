package fr.graynaud.eu4saveconverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.graynaud.eu4saveconverter.common.Constants;
import fr.graynaud.eu4saveconverter.service.object.save.*;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SaveDTO {

    private LocalDate date;

    private LocalDate startDate;

    private List<CountryDTO> playerCountries = new ArrayList<>();

    @JsonIgnore
    private Map<String, CountryDTO> countries;

    public SaveDTO(Gamestate gamestate, LocalDate date) {
        this.date = date;
        this.startDate = gamestate.getStartDate();
        this.countries = gamestate.getCountries()
                                  .values()
                                  .stream()
                                  .filter(country -> country.getCapital() != null && country.getCapital() != 0 &&
                                                     country.getContinents()
                                                            .contains(Boolean.TRUE)) //Filtering dead countries
                                  .map(country -> new CountryDTO(country,
                                                                 country.getTag()
                                                                        .equals(gamestate.getRevolutionTarget()),
                                                                 country.getTag()
                                                                        .equals(gamestate.getEmpire().getEmperor()),
                                                                 gamestate.getProvinces().get(country.getTag()),
                                                                 gamestate.getAdvisors()))
                                  .collect(Collectors.toMap(CountryDTO::getTag, Function.identity()));

        this.countries.forEach((tag, countryDTO) -> {
            Map<SubjectType, List<CountryDTO>> dependencies = gamestate.getDiplomacy()
                                                                       .getDependencies()
                                                                       .stream()
                                                                       .filter(d -> tag.equals(d.getFirst()))
                                                                       .collect(Collectors.groupingBy(Dependency::getSubjectType))
                                                                       .entrySet()
                                                                       .stream()
                                                                       .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry
                                                                               .getValue()
                                                                               .stream()
                                                                               .map(d -> this.countries.get(d.getSecond()))
                                                                               .collect(Collectors.toList())))
                                                                       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            boolean isDependency = gamestate.getDiplomacy()
                                            .getDependencies()
                                            .stream()
                                            .anyMatch(d -> tag.equals(d.getSecond()));
            boolean isMarch = isDependency && gamestate.getDiplomacy()
                                                       .getDependencies()
                                                       .stream()
                                                       .anyMatch(d -> SubjectType.MARCH.equals(d.getSubjectType()) &&
                                                                      tag.equals(d.getSecond()));
            countryDTO.setDependencies(dependencies);
            countryDTO.setIsDependency(isDependency);
            countryDTO.setIsMarch(isMarch);
        });

        long nbCountriesInHre = this.countries.values().stream().filter(c -> Boolean.TRUE.equals(c.isInHre())).count();
        this.countries.values()
                      .stream()
                      .filter(c -> c.isDependency() && c.getDependencies().isEmpty()) //Process first dependencies that does not have any
                      .forEach(countryDTO -> countryDTO.computePostData(nbCountriesInHre));
        this.countries.values()
                      .stream()
                      .filter(c -> c.isDependency() && !c.getDependencies().isEmpty()) //Then dependencies that have some
                      .forEach(countryDTO -> countryDTO.computePostData(nbCountriesInHre));
        this.countries.values()
                      .stream()
                      .filter(Predicate.not(CountryDTO::isDependency)) //Then not dependency
                      .forEach(countryDTO -> countryDTO.computePostData(nbCountriesInHre));


        gamestate.getPlayersCountries().forEach((tag, player) -> {
            CountryDTO countryDTO = this.countries.get(tag);
            countryDTO.setPlayer(player);
            this.playerCountries.add(countryDTO);
        });

        this.playerCountries.sort(Comparator.comparing(CountryDTO::getGreatPowerScore).reversed());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<CountryDTO> getPlayerCountries() {
        return playerCountries;
    }

    public void setPlayerCountries(List<CountryDTO> playerCountries) {
        this.playerCountries = playerCountries;
    }
}
