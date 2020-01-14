package fr.graynaud.eu4saveconverter.service.object.save;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Gamestate extends Eu4Object {

    private Map<String, String> playersCountries;

    private String currentAge;

    private Date startDate;

    private Map<String, Map<String, State>> states;

    private List<Long> institutionOrigin;

    private List<Boolean> institutions;

    private List<String> productionLeaderTag;

    private List<GreatPower> greatPowers;

    private Hre empire;

    private CelestialEmpire celestialEmpire;

    private String pope;

    private Object provinces;

    private Object countries;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Map<String, Map<String, State>> getStates() {
        return states;
    }

    public void setStates(Map<String, Map<String, State>> states) {
        this.states = states;
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

    public Object getProvinces() {
        return provinces;
    }

    public void setProvinces(Object provinces) {
        this.provinces = provinces;
    }

    public Object getCountries() {
        return countries;
    }

    public void setCountries(Object countries) {
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
            playersCountries.put(playerCountriesList.get(i), playerCountriesList.get(i + 1));
        }

        this.currentAge = ParseUtils.parseString(startContent, "current_age").orElse(null);
        this.startDate = ParseUtils.parseDate(startContent, "start_date").orElse(null);
        this.states = parseStates(startContent);
        this.institutionOrigin = ParseUtils.parseLineLong(startContent, "institution_origin");
        this.institutions = ParseUtils.parseLineLongToBoolean(startContent, "institutions");
        this.productionLeaderTag = ParseUtils.parseLineString(startContent, "production_leader_tag");
        this.greatPowers = parseGreatPowers(startContent);
        this.empire = new Hre(startContent);
        this.celestialEmpire = new CelestialEmpire(startContent);
        //        this.provinces = ParseUtils.parseObjectData(startContent, "");
        //        this.countries = ParseUtils.parseObjectData(startContent, "");
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

    private Map<String, Map<String, State>> parseStates(String content) {
        String subContent = ParseUtils.getNextObject(content, content.indexOf("map_area_data{"));
        subContent = subContent.substring(subContent.indexOf("\n"), subContent.lastIndexOf("\n"));

        List<String> areas = Arrays.stream(subContent.split("\\n\\w.*=\\{"))
                                   .skip(1)
                                   .map(ParseUtils::formatStringValue)
                                   .filter(Objects::nonNull)
                                   .filter(s -> s.contains("state={"))
                                   .collect(Collectors.toList());
        return areas.stream()
                    .collect(Collectors.toMap(s -> ParseUtils.parseString(s, "area").orElse(null), this::parseArea));
    }

    private Map<String, State> parseArea(String content) {
        return Arrays.stream(content.split("country_state=\\{"))
                     .skip(1)
                     .map(State::new)
                     .collect(Collectors.toMap(State::getCountry, Function.identity()));
    }
}
