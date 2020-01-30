package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.Constants;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Gamestate extends Eu4Object {

    private Map<String, String> playersCountries;

    private String currentAge;

    private String revolutionTarget;

    private LocalDate startDate;

    private Map<String, Map<String, State>> states;

    private Map<String, Map<String, List<String>>> investments;

    private List<Long> institutionOrigin;

    private List<Boolean> institutions;

    private List<String> productionLeaderTag;

    private List<GreatPower> greatPowers;

    private Hre empire;

    private CelestialEmpire celestialEmpire;

    private String pope;

    private Map<String, List<Province>> provinces;

    private Map<Long, String> advisors;

    private Map<String, Country> countries;

    private Diplomacy diplomacy;

    private List<War> activeWars;

    private List<War> previousWars;

    public Gamestate(String content) {
        super(content);
    }

    public Map<String, String> getPlayersCountries() {
        return playersCountries;
    }

    public void setPlayersCountries(Map<String, String> playersCountries) {
        this.playersCountries = playersCountries;
    }

    public String getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(String currentAge) {
        this.currentAge = currentAge;
    }

    public String getRevolutionTarget() {
        return revolutionTarget;
    }

    public void setRevolutionTarget(String revolutionTarget) {
        this.revolutionTarget = revolutionTarget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Map<String, Map<String, State>> getStates() {
        return states;
    }

    public void setStates(Map<String, Map<String, State>> states) {
        this.states = states;
    }

    public Map<String, Map<String, List<String>>> getInvestments() {
        return investments;
    }

    public void setInvestments(Map<String, Map<String, List<String>>> investments) {
        this.investments = investments;
    }

    public List<Long> getInstitutionOrigin() {
        return institutionOrigin;
    }

    public void setInstitutionOrigin(List<Long> institutionOrigin) {
        this.institutionOrigin = institutionOrigin;
    }

    public List<Boolean> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Boolean> institutions) {
        this.institutions = institutions;
    }

    public List<String> getProductionLeaderTag() {
        return productionLeaderTag;
    }

    public void setProductionLeaderTag(List<String> productionLeaderTag) {
        this.productionLeaderTag = productionLeaderTag;
    }

    public List<GreatPower> getGreatPowers() {
        return greatPowers;
    }

    public void setGreatPowers(List<GreatPower> greatPowers) {
        this.greatPowers = greatPowers;
    }

    public Hre getEmpire() {
        return empire;
    }

    public void setEmpire(Hre empire) {
        this.empire = empire;
    }

    public CelestialEmpire getCelestialEmpire() {
        return celestialEmpire;
    }

    public void setCelestialEmpire(CelestialEmpire celestialEmpire) {
        this.celestialEmpire = celestialEmpire;
    }

    public String getPope() {
        return pope;
    }

    public void setPope(String pope) {
        this.pope = pope;
    }

    public Map<Long, String> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(Map<Long, String> advisors) {
        this.advisors = advisors;
    }

    public Map<String, List<Province>> getProvinces() {
        return provinces;
    }

    public void setProvinces(Map<String, List<Province>> provinces) {
        this.provinces = provinces;
    }

    public Map<String, Country> getCountries() {
        return countries;
    }

    public void setCountries(Map<String, Country> countries) {
        this.countries = countries;
    }

    public Diplomacy getDiplomacy() {
        return diplomacy;
    }

    public void setDiplomacy(Diplomacy diplomacy) {
        this.diplomacy = diplomacy;
    }

    public List<War> getActiveWars() {
        return activeWars;
    }

    public void setActiveWars(List<War> activeWars) {
        this.activeWars = activeWars;
    }

    public List<War> getPreviousWars() {
        return previousWars;
    }

    public void setPreviousWars(List<War> previousWars) {
        this.previousWars = previousWars;
    }

    @Override
    public void parse(String content) {
        int indexOfProvinces = content.indexOf("\nprovinces={");
        int indexOfCountries = content.indexOf("\ncountries={", indexOfProvinces);
        int indexOfEnd = content.indexOf("\nactive_advisors={", indexOfCountries);
        String startContent = content.substring(0, indexOfProvinces).trim();
        String provincesContent = content.substring(indexOfProvinces, indexOfCountries - 1).trim();
        String countriesContent = content.substring(indexOfCountries, indexOfEnd - 1).trim();
        String endContent = content.substring(indexOfEnd).trim();

        this.playersCountries = new HashMap<>();
        List<String> playerCountriesList = ParseUtils.parseListString(startContent, "players_countries");
        for (int i = 0; i < playerCountriesList.size(); i += 2) {
            playersCountries.put(playerCountriesList.get(i + 1), playerCountriesList.get(i));
        }

        this.currentAge = ParseUtils.parseString(startContent, "current_age").orElse(null);
        this.revolutionTarget = ParseUtils.parseString(startContent, "revolution_target").orElse(null);
        this.startDate = ParseUtils.parseDate(startContent, "start_date").orElse(null);
        parseStates(startContent);
        this.institutionOrigin = ParseUtils.parseLineLong(startContent, "institution_origin");
        this.institutions = ParseUtils.parseLineLongToBoolean(startContent, "institutions");
        this.productionLeaderTag = ParseUtils.parseLineString(startContent, "production_leader_tag");
        this.greatPowers = parseGreatPowers(startContent);
        this.empire = new Hre(startContent);
        this.celestialEmpire = new CelestialEmpire(startContent);
        this.provinces = Arrays.stream(provincesContent.split("(?=\\n-\\d+=\\{)"))
                               .skip(1)
                               .map(ParseUtils::cleanString)
                               .map(Province::new)
                               .filter(p -> p.getOwner() != null)
                               .collect(Collectors.groupingBy(Province::getOwner));
        this.advisors = this.provinces.values()
                                      .stream()
                                      .flatMap(Collection::stream)
                                      .filter(p -> p.getAdvisors() != null)
                                      .flatMap(p -> p.getAdvisors().entrySet().stream())
                                      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.countries = Arrays.stream(countriesContent.split("(?=\\n\\t(([A-Z]{3})|([A-Z][0-9]{2}))=\\{)"))
                               .skip(4)
                               .map(ParseUtils::cleanString)
                               .map(country -> new Country(country, Constants.DATE_FORMAT.format(this.startDate)))
                               .collect(Collectors.toMap(Country::getTag, Function.identity()));
        this.diplomacy = new Diplomacy(endContent);
        this.activeWars = ParseUtils.getListSameObject(endContent, "\nactive_war={")
                                    .stream()
                                    .map(War::new)
                                    .collect(Collectors.toList());
        this.previousWars = ParseUtils.getListSameObject(endContent, "\nprevious_war={")
                                      .stream()
                                      .map(War::new)
                                      .collect(Collectors.toList());
    }

    private List<GreatPower> parseGreatPowers(String content) {
        String subContent = ParseUtils.getNextObject(content, content.indexOf("great_powers={"));
        List<String> originals = ParseUtils.getListSameObject(subContent, "original");

        if (originals != null && !originals.isEmpty()) {
            return originals.stream().filter(Objects::nonNull).map(GreatPower::new).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    private void parseStates(String content) {
        String subContent = ParseUtils.getNextObject(content, content.indexOf("map_area_data{"));
        subContent = subContent.substring(subContent.indexOf("\n"), subContent.lastIndexOf("\n"));

        List<String> areas = Arrays.stream(subContent.split("(?=\\n\\w.*=\\{)"))
                                   .skip(1)
                                   .map(ParseUtils::cleanString)
                                   .filter(Objects::nonNull)
                                   .filter(s -> s.contains("state={") || s.contains("investments={"))
                                   .collect(Collectors.toList());

        this.states = areas.stream()
                           .filter(s -> s.contains("state={"))
                           .collect(Collectors.toMap(s -> ParseUtils.cleanString(s.substring(0, s.indexOf("="))),
                                                     this::parseArea));

        this.investments = areas.stream()
                                .filter(s -> s.contains("investments={"))
                                .collect(Collectors.toMap(s -> ParseUtils.cleanString(s.substring(0, s.indexOf("="))),
                                                          this::parseInvestments));
    }

    private Map<String, State> parseArea(String content) {
        return Arrays.stream(content.split("country_state=\\{"))
                     .skip(1)
                     .map(State::new)
                     .collect(Collectors.toMap(State::getCountry, Function.identity()));
    }

    private Map<String, List<String>> parseInvestments(String content) {
        return ParseUtils.getListSameObject(content, "\n\t\tinvestments")
                         .stream()
                         .collect(Collectors.toMap(s -> ParseUtils.parseString(s, "tag")
                                                                  .orElse(null), s -> ParseUtils.getCleanLineString(s, "\t\t\tinvestments")));
    }
}
