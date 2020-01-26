package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;
import fr.graynaud.eu4saveconverter.common.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Province extends Eu4Object {

    private Long id;

    private String name;

    private String owner;

    private String controller;

    private Boolean parliament;

    private List<Double> institutions;

    private String culture;

    private String religion;

    private Double baseTax;

    private Double baseProd;

    private Double baseManpower;

    private String good;

    private Map<String, String> buildings;

    private Long centerOfTrade;

    private Double autonomy;

    private Long estate;

    private Boolean isPartHRE;

    private Map<Long, String> advisors;

    public Province(String content) {
        super(content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public Boolean getParliament() {
        return parliament;
    }

    public void setParliament(Boolean parliament) {
        this.parliament = parliament;
    }

    public List<Double> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Double> institutions) {
        this.institutions = institutions;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Double getBaseTax() {
        return baseTax;
    }

    public void setBaseTax(Double baseTax) {
        this.baseTax = baseTax;
    }

    public Double getBaseProd() {
        return baseProd;
    }

    public void setBaseProd(Double baseProd) {
        this.baseProd = baseProd;
    }

    public Double getBaseManpower() {
        return baseManpower;
    }

    public void setBaseManpower(Double baseManpower) {
        this.baseManpower = baseManpower;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public Map<String, String> getBuildings() {
        return buildings;
    }

    public void setBuildings(Map<String, String> buildings) {
        this.buildings = buildings;
    }

    public Long getCenterOfTrade() {
        return centerOfTrade;
    }

    public void setCenterOfTrade(Long centerOfTrade) {
        this.centerOfTrade = centerOfTrade;
    }

    public Double getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(Double autonomy) {
        this.autonomy = autonomy;
    }

    public Long getEstate() {
        return estate;
    }

    public void setEstate(Long estate) {
        this.estate = estate;
    }

    public Boolean isPartHRE() {
        return isPartHRE;
    }

    public void setPartHRE(Boolean partHRE) {
        isPartHRE = partHRE;
    }

    public Map<Long, String> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(Map<Long, String> advisors) {
        this.advisors = advisors;
    }

    @Override
    public void parse(String content) {
        this.id = Utils.nullSafeParseLong(content.substring(1, content.indexOf("="))).orElse(null);
        this.name = ParseUtils.parseString(content, "name").orElse(null);
        this.owner = ParseUtils.parseString(content, "owner").orElse(null);
        this.controller = ParseUtils.parseString(content, "controller").orElse(null);
        this.parliament = content.contains("seat_in_parliament={");
        this.institutions = ParseUtils.parseLineDouble(content, "institutions");
        this.culture = ParseUtils.parseString(content, "culture").orElse(null);
        this.religion = ParseUtils.parseString(content, "religion").orElse(null);
        this.baseTax = ParseUtils.parseDouble(content, "base_tax").orElse(0d);
        this.baseProd = ParseUtils.parseDouble(content, "base_production").orElse(0d);
        this.baseManpower = ParseUtils.parseDouble(content, "base_manpower").orElse(0d);
        this.good = ParseUtils.parseString(content, "trade_goods").orElse(null);
        this.centerOfTrade = ParseUtils.parseLong(content, "center_of_trade").orElse(null);
        this.autonomy = ParseUtils.parseDouble(content, "local_autonomy").orElse(0d);
        this.estate = ParseUtils.parseLong(content, "\n\t\testate").orElse(null);
        this.isPartHRE = ParseUtils.parseBoolean(content, "hre").orElse(null);

        int buildingsIndex = content.indexOf("building_builders={");
        if (buildingsIndex >= 0) {
            String buildingsContent = ParseUtils.getNextObject(content, buildingsIndex);
            String[] buildingsString = buildingsContent.split("\n");
            this.buildings = Arrays.stream(buildingsString)
                                   .skip(1)
                                   .limit(buildingsString.length - 2)
                                   .collect(Collectors.toMap(s -> ParseUtils.cleanString(s.split("=")[0]),
                                                             s -> ParseUtils.cleanString(s.split("=")[1])));
        }

        int advisorIndex = content.indexOf("advisor={");
        if (advisorIndex >= 0) {
            this.advisors = ParseUtils.getListSameObject(content.substring(advisorIndex), "advisor")
                                      .stream()
                                      .filter(advisor -> advisor.contains("hire_date="))
                                      .collect(Collectors.toMap(advisor -> ParseUtils.parseLong(advisor.substring(
                                              advisor.indexOf("id={") + 1), "id").orElse(0L),
                                                                advisor -> ParseUtils.parseString(advisor, "type").orElse("")));
        }
    }
}
