package fr.graynaud.eu4saveconverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.graynaud.eu4saveconverter.common.Constants;
import fr.graynaud.eu4saveconverter.service.object.save.Dependency;
import fr.graynaud.eu4saveconverter.service.object.save.Gamestate;
import fr.graynaud.eu4saveconverter.service.object.save.Province;
import fr.graynaud.eu4saveconverter.service.object.save.SubjectType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Schema(name = "Save")
public class SaveDTO implements Comparable<SaveDTO> {

    private LocalDate currentDate;

    private LocalDate campaignStartDate;

    private List<CountryDTO> playerCountries = new ArrayList<>();

    private Long totalDevelopment;

    private Long totalLosses;

    @JsonIgnore
    private Map<String, CountryDTO> countries;

    public SaveDTO() {
    }

    public SaveDTO(Gamestate gamestate, LocalDate currentDate) {
        this.currentDate = currentDate;
        this.campaignStartDate = gamestate.getStartDate();
        this.countries = gamestate.getCountries()
                                  .values()
                                  .stream()
                                  .filter(country -> country.getCapital() != null && country.getCapital() != 0 &&
                                                     country.getContinents().contains(Boolean.TRUE) &&
                                                     gamestate.getProvinces().get(country.getTag()) !=
                                                     null) //Filtering dead countries
                                  .map(country -> new CountryDTO(country,
                                                                 country.getTag()
                                                                        .equals(gamestate.getRevolutionTarget()),
                                                                 country.getTag()
                                                                        .equals(gamestate.getEmpire().getEmperor()),
                                                                 gamestate.getProvinces().get(country.getTag()),
                                                                 gamestate.getAdvisors(),
                                                                 gamestate.getInvestments()
                                                                          .values()
                                                                          .stream()
                                                                          .flatMap(map -> map.entrySet().stream())
                                                                          .filter(entry -> entry.getKey()
                                                                                                .equals(country.getTag()))
                                                                          .flatMap(entry -> entry.getValue().stream())
                                                                          .collect(Collectors.toList()),
                                                                 gamestate.getStates()
                                                                          .entrySet()
                                                                          .stream()
                                                                          .filter(entry -> entry.getValue()
                                                                                                .get(country.getTag()) !=
                                                                                           null)
                                                                          .map(Map.Entry::getKey)
                                                                          .collect(Collectors.toList())))
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
                                            .filter(d -> !d.getSubjectType().equals(SubjectType.TRIBUTARY))
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

        AtomicReference<Optional<String>> eastSlavicProvincesSameTag = new AtomicReference<>(Optional.empty());
        gamestate.getProvinces()
                 .values()
                 .stream()
                 .flatMap(Collection::stream)
                 .filter(p -> Constants.EAST_SLAVIC_CULTURES.contains(p.getCulture()))
                 .findFirst()
                 .ifPresent(province -> {
                     if (gamestate.getProvinces()
                                  .values()
                                  .stream()
                                  .flatMap(Collection::stream)
                                  .filter(p -> Constants.EAST_SLAVIC_CULTURES.contains(p.getCulture()))
                                  .map(Province::getOwner)
                                  .allMatch(owner -> province.getOwner().equals(owner))) {
                         eastSlavicProvincesSameTag.set(Optional.of(province.getOwner()));
                     }
                 });

        long nbCountriesInHre = this.countries.values().stream().filter(c -> Boolean.TRUE.equals(c.isInHre())).count();
        this.countries.values()
                      .stream()
                      .filter(c -> c.isDependency() &&
                                   c.getDependencies().isEmpty()) //Process first dependencies that does not have any
                      .forEach(countryDTO -> countryDTO.computePostData(nbCountriesInHre, eastSlavicProvincesSameTag.get()
                                                                                                                    .orElse(null)));
        this.countries.values()
                      .stream()
                      .filter(c -> c.isDependency() &&
                                   !c.getDependencies().isEmpty()) //Then dependencies that have some
                      .forEach(countryDTO -> countryDTO.computePostData(nbCountriesInHre, eastSlavicProvincesSameTag.get()
                                                                                                                    .orElse(null)));
        this.countries.values()
                      .stream()
                      .filter(Predicate.not(CountryDTO::isDependency)) //Then not dependency
                      .forEach(countryDTO -> countryDTO.computePostData(nbCountriesInHre, eastSlavicProvincesSameTag.get()
                                                                                                                    .orElse(null)));


        gamestate.getPlayersCountries().forEach((tag, player) -> {
            CountryDTO countryDTO = this.countries.get(tag);

            if (countryDTO != null) { //null means dead country or changed tag
                countryDTO.setPlayer(player);
                this.playerCountries.add(countryDTO);
            }
        });

        this.playerCountries.sort(Comparator.comparing(CountryDTO::getGreatPowerScore).reversed());
        this.totalDevelopment = (long) this.playerCountries.stream().mapToDouble(CountryDTO::getTotalDevelopment).sum();
        this.totalLosses = (long) this.playerCountries.stream().mapToDouble(CountryDTO::getLosses).sum();
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public LocalDate getCampaignStartDate() {
        return campaignStartDate;
    }

    public void setCampaignStartDate(LocalDate campaignStartDate) {
        this.campaignStartDate = campaignStartDate;
    }

    public Long getTotalDevelopment() {
        return totalDevelopment;
    }

    public void setTotalDevelopment(Long totalDevelopment) {
        this.totalDevelopment = totalDevelopment;
    }

    public Long getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(Long totalLosses) {
        this.totalLosses = totalLosses;
    }

    public List<CountryDTO> getPlayerCountries() {
        return playerCountries;
    }

    public void setPlayerCountries(List<CountryDTO> playerCountries) {
        this.playerCountries = playerCountries;
    }

    @Override
    public int compareTo(SaveDTO other) {
        return this.currentDate.compareTo(other.currentDate);
    }
}
