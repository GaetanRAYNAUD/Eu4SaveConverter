package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.Constants;
import fr.graynaud.eu4saveconverter.common.ParseUtils;
import fr.graynaud.eu4saveconverter.common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    private String tag;

    private String govName;

    private Long govRank;

    private List<Boolean> continents;

    private List<Boolean> institutions;

    private Date goldenAge;

    private Map<Date, String> ancientsTags;

    private Long capital;

    private CustomColors customColors;

    private List<Long> revolutionaryColors;

    private List<Long> mapColor;

    private List<Long> countryColor;

    private String name;

    private String religion;

    private String culture;

    private String techGroup;

    private Long admTech;

    private Long dipTech;

    private Long milTech;

    private List<String> rivals;

    private Double powerProjection;

    private Double greatPowerScore;

    private Long army;

    private Long mercenaries;

    private Double averageUnrest;

    private Double averageAutonomy;

    private List<String> allies;

    private Double prestige;

    private Double stability;

    private Double cash;

    private Double income;

    private Double inflation;

    private Double warExhaustion;

    private Double armyTradition;

    private Double navyTradition;

    private Long debt;

    private Double republicanTradition;

    private Double corruption;

    private Double legitimacy;

    private Double mercantilism;

    private Double absolutism;

    private Double professionalism;

    private Map<String, Long> ideas;

    private List<Long> advisors;

    private String govType;

    private List<String> govReforms;

    private Long manpower;

    private Long maxManpower;

    private Long sailors;

    private Long maxSailors;

    private List<Long> tradeBonus;

    private Map<Long, Long> customIdeas;

    private Long losses;

    private Double innovativeness;

    private List<String> policies;

    private Map<String, Double> factions;

    public Country(String content, String startDate) {
        parse(content, startDate);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public Long getGovRank() {
        return govRank;
    }

    public void setGovRank(Long govRank) {
        this.govRank = govRank;
    }

    public List<Boolean> getContinents() {
        return continents;
    }

    public void setContinents(List<Boolean> continents) {
        this.continents = continents;
    }

    public List<Boolean> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Boolean> institutions) {
        this.institutions = institutions;
    }

    public Date getGoldenAge() {
        return goldenAge;
    }

    public void setGoldenAge(Date goldenAge) {
        this.goldenAge = goldenAge;
    }

    public Map<Date, String> getAncientsTags() {
        return ancientsTags;
    }

    public void setAncientsTags(Map<Date, String> ancientsTags) {
        this.ancientsTags = ancientsTags;
    }

    public Long getCapital() {
        return capital;
    }

    public void setCapital(Long capital) {
        this.capital = capital;
    }

    public CustomColors getCustomColors() {
        return customColors;
    }

    public void setCustomColors(CustomColors customColors) {
        this.customColors = customColors;
    }

    public List<Long> getRevolutionaryColors() {
        return revolutionaryColors;
    }

    public void setRevolutionaryColors(List<Long> revolutionaryColors) {
        this.revolutionaryColors = revolutionaryColors;
    }

    public List<Long> getMapColor() {
        return mapColor;
    }

    public void setMapColor(List<Long> mapColor) {
        this.mapColor = mapColor;
    }

    public List<Long> getCountryColor() {
        return countryColor;
    }

    public void setCountryColor(List<Long> countryColor) {
        this.countryColor = countryColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getTechGroup() {
        return techGroup;
    }

    public void setTechGroup(String techGroup) {
        this.techGroup = techGroup;
    }

    public Long getAdmTech() {
        return admTech;
    }

    public void setAdmTech(Long admTech) {
        this.admTech = admTech;
    }

    public Long getDipTech() {
        return dipTech;
    }

    public void setDipTech(Long dipTech) {
        this.dipTech = dipTech;
    }

    public Long getMilTech() {
        return milTech;
    }

    public void setMilTech(Long milTech) {
        this.milTech = milTech;
    }

    public List<String> getRivals() {
        return rivals;
    }

    public void setRivals(List<String> rivals) {
        this.rivals = rivals;
    }

    public Double getPowerProjection() {
        return powerProjection;
    }

    public void setPowerProjection(Double powerProjection) {
        this.powerProjection = powerProjection;
    }

    public Double getGreatPowerScore() {
        return greatPowerScore;
    }

    public void setGreatPowerScore(Double greatPowerScore) {
        this.greatPowerScore = greatPowerScore;
    }

    public Long getArmy() {
        return army;
    }

    public void setArmy(Long army) {
        this.army = army;
    }

    public Long getMercenaries() {
        return mercenaries;
    }

    public void setMercenaries(Long mercenaries) {
        this.mercenaries = mercenaries;
    }

    public Double getAverageUnrest() {
        return averageUnrest;
    }

    public void setAverageUnrest(Double averageUnrest) {
        this.averageUnrest = averageUnrest;
    }

    public Double getAverageAutonomy() {
        return averageAutonomy;
    }

    public void setAverageAutonomy(Double averageAutonomy) {
        this.averageAutonomy = averageAutonomy;
    }

    public List<String> getAllies() {
        return allies;
    }

    public void setAllies(List<String> allies) {
        this.allies = allies;
    }

    public Double getPrestige() {
        return prestige;
    }

    public void setPrestige(Double prestige) {
        this.prestige = prestige;
    }

    public Double getStability() {
        return stability;
    }

    public void setStability(Double stability) {
        this.stability = stability;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getInflation() {
        return inflation;
    }

    public void setInflation(Double inflation) {
        this.inflation = inflation;
    }

    public Double getWarExhaustion() {
        return warExhaustion;
    }

    public void setWarExhaustion(Double warExhaustion) {
        this.warExhaustion = warExhaustion;
    }

    public Double getArmyTradition() {
        return armyTradition;
    }

    public void setArmyTradition(Double armyTradition) {
        this.armyTradition = armyTradition;
    }

    public Double getNavyTradition() {
        return navyTradition;
    }

    public void setNavyTradition(Double navyTradition) {
        this.navyTradition = navyTradition;
    }

    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }

    public Double getRepublicanTradition() {
        return republicanTradition;
    }

    public void setRepublicanTradition(Double republicanTradition) {
        this.republicanTradition = republicanTradition;
    }

    public Double getCorruption() {
        return corruption;
    }

    public void setCorruption(Double corruption) {
        this.corruption = corruption;
    }

    public Double getLegitimacy() {
        return legitimacy;
    }

    public void setLegitimacy(Double legitimacy) {
        this.legitimacy = legitimacy;
    }

    public Double getMercantilism() {
        return mercantilism;
    }

    public void setMercantilism(Double mercantilism) {
        this.mercantilism = mercantilism;
    }

    public Double getAbsolutism() {
        return absolutism;
    }

    public void setAbsolutism(Double absolutism) {
        this.absolutism = absolutism;
    }

    public Double getProfessionalism() {
        return professionalism;
    }

    public void setProfessionalism(Double professionalism) {
        this.professionalism = professionalism;
    }

    public Map<String, Long> getIdeas() {
        return ideas;
    }

    public void setIdeas(Map<String, Long> ideas) {
        this.ideas = ideas;
    }

    public List<Long> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<Long> advisors) {
        this.advisors = advisors;
    }

    public String getGovType() {
        return govType;
    }

    public void setGovType(String govType) {
        this.govType = govType;
    }

    public List<String> getGovReforms() {
        return govReforms;
    }

    public void setGovReforms(List<String> govReforms) {
        this.govReforms = govReforms;
    }

    public Long getManpower() {
        return manpower;
    }

    public void setManpower(Long manpower) {
        this.manpower = manpower;
    }

    public Long getMaxManpower() {
        return maxManpower;
    }

    public void setMaxManpower(Long maxManpower) {
        this.maxManpower = maxManpower;
    }

    public Long getSailors() {
        return sailors;
    }

    public void setSailors(Long sailors) {
        this.sailors = sailors;
    }

    public Long getMaxSailors() {
        return maxSailors;
    }

    public void setMaxSailors(Long maxSailors) {
        this.maxSailors = maxSailors;
    }

    public List<Long> getTradeBonus() {
        return tradeBonus;
    }

    public void setTradeBonus(List<Long> tradeBonus) {
        this.tradeBonus = tradeBonus;
    }

    public Map<Long, Long> getCustomIdeas() {
        return customIdeas;
    }

    public void setCustomIdeas(Map<Long, Long> customIdeas) {
        this.customIdeas = customIdeas;
    }

    public Long getLosses() {
        return losses;
    }

    public void setLosses(Long losses) {
        this.losses = losses;
    }

    public Double getInnovativeness() {
        return innovativeness;
    }

    public void setInnovativeness(Double innovativeness) {
        this.innovativeness = innovativeness;
    }

    public List<String> getPolicies() {
        return policies;
    }

    public void setPolicies(List<String> policies) {
        this.policies = policies;
    }

    public Map<String, Double> getFactions() {
        return factions;
    }

    public void setFactions(Map<String, Double> factions) {
        this.factions = factions;
    }

    public void parse(String content, String startDate) {
        startDate = startDate.isBlank() ? Constants.DEFAULT_START_DATE : startDate;
        String finalStartDate = startDate;
        this.tag = ParseUtils.cleanString(content.substring(0, content.indexOf("=")));

        try {
            this.govRank = ParseUtils.parseLong(content, "government_rank").orElse(1L);
            this.continents = ParseUtils.parseLineLongToBoolean(content, "continent");
            this.institutions = ParseUtils.parseLineLongToBoolean(content, "institutions");
            this.goldenAge = ParseUtils.parseDate(content, "golden_era_date").orElse(null);
            this.capital = ParseUtils.parseLong(content, "\n\t\tcapital").orElse(null);
            this.revolutionaryColors = ParseUtils.parseLineLong(content, "revolutionary_colors");
            this.mapColor = ParseUtils.parseLineLong(content, "map_color");
            this.countryColor = ParseUtils.parseLineLong(content, "country_color");
            this.name = ParseUtils.parseString(content, "\n\t\tname").orElse(null);
            this.religion = ParseUtils.parseString(content, "religion").orElse(null);
            this.culture = ParseUtils.parseString(content, "culture").orElse(null);
            this.techGroup = ParseUtils.parseString(content, "\n\t\ttechnology_group").orElse(null);
            this.admTech = ParseUtils.parseLong(content, "\n\t\t\tadm_tech").orElse(0L);
            this.dipTech = ParseUtils.parseLong(content, "\n\t\t\tdip_tech").orElse(0L);
            this.milTech = ParseUtils.parseLong(content, "\n\t\t\tmil_tech").orElse(0L);
            this.rivals = ParseUtils.getListSameObject(content, "\n\t\trival")
                                    .stream()
                                    .map(rival -> ParseUtils.parseString(rival, "country").orElse(null))
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());
            this.powerProjection = ParseUtils.parseDouble(content, "current_power_projection").orElse(0d);
            this.greatPowerScore = ParseUtils.parseDouble(content, "great_power_score").orElse(0d);
            this.averageUnrest = ParseUtils.parseDouble(content, "average_unrest").orElse(0d);
            this.averageAutonomy = ParseUtils.parseDouble(content, "average_autonomy").orElse(0d);
            this.allies = ParseUtils.parseLineString(content, "allies");
            this.prestige = ParseUtils.parseDouble(content, "prestige").orElse(0d);
            this.stability = ParseUtils.parseDouble(content, "stability").orElse(0d);
            this.cash = ParseUtils.parseDouble(content, "treasury").orElse(0d);
            this.income = ParseUtils.parseDouble(content, "estimated_monthly_income").orElse(0d);
            this.inflation = ParseUtils.parseDouble(content, "inflation").orElse(0d);
            this.warExhaustion = ParseUtils.parseDouble(content, "war_exhaustion").orElse(0d);
            this.armyTradition = ParseUtils.parseDouble(content, "army_tradition").orElse(0d);
            this.navyTradition = ParseUtils.parseDouble(content, "navy_tradition").orElse(0d);
            this.debt = ParseUtils.getListSameObject(content, "loan={")
                                  .stream()
                                  .mapToLong(loan -> ParseUtils.parseLong(loan, "amount").orElse(0L))
                                  .sum();
            this.republicanTradition = ParseUtils.parseDouble(content, "republican_tradition").orElse(0d);
            this.corruption = ParseUtils.parseDouble(content, "corruption").orElse(0d);
            this.legitimacy = ParseUtils.parseDouble(content, "legitimacy").orElse(0d);
            this.mercantilism = ParseUtils.parseDouble(content, "mercantilism").orElse(0d);
            this.absolutism = ParseUtils.parseDouble(content, "\tabsolutism").orElse(0d);
            this.professionalism = ParseUtils.parseDouble(content, "army_professionalism").orElse(0d) * 100;
            this.advisors = ParseUtils.getListSameObject(content, "\n\t\tadvisor")
                                      .stream()
                                      .map(advisor -> ParseUtils.parseLong(advisor, "id"))
                                      .filter(Optional::isPresent)
                                      .map(Optional::get)
                                      .collect(Collectors.toList());
            this.manpower = ParseUtils.parseDouble(content, "\tmanpower").orElse(0d).longValue() * 1000;
            this.maxManpower = ParseUtils.parseDouble(content, "max_manpower").orElse(0d).longValue() * 1000;
            this.sailors = ParseUtils.parseDouble(content, "sailors").orElse(0d).longValue() * 1000;
            this.maxSailors = ParseUtils.parseDouble(content, "max_sailors").orElse(0d).longValue() * 1000;
            this.tradeBonus = ParseUtils.parseLineLong(content, "traded_bonus");
            this.losses = ParseUtils.parseLineLong(content, "members").stream().mapToLong(Long::longValue).sum();
            this.innovativeness = ParseUtils.parseDouble(content, "innovativeness").orElse(0d);
            this.policies = ParseUtils.getListSameObject(content, "active_policy")
                                      .stream()
                                      .map(policy -> ParseUtils.parseString(policy, "policy"))
                                      .filter(Optional::isPresent)
                                      .map(Optional::get)
                                      .collect(Collectors.toList());
            this.factions = ParseUtils.getListSameObject(content, "faction")
                                      .stream()
                                      .flatMap(faction -> ParseUtils.parseString(faction, "type")
                                                                    .stream()
                                                                    .map(type -> Map.entry(type, ParseUtils.parseDouble(faction, "influence")
                                                                                                           .orElse(0d))))
                                      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            int govNameIndex = content.indexOf("government_name=\"");
            if (govNameIndex >= 0) {
                this.govName = ParseUtils.cleanString(content.substring(
                        govNameIndex + 17, content.indexOf("\n", govNameIndex) - 1));
            }

            int customColorsIndex = content.indexOf("custom_colors");
            if (customColorsIndex >= 0) {
                this.customColors = new CustomColors(ParseUtils.getNextObject(content, customColorsIndex));
            }

            int ideasIndex = content.indexOf("active_idea_groups");
            if (ideasIndex >= 0) {
                String[] ideasStrings = ParseUtils.getNextObject(content, ideasIndex).split("\n");
                this.ideas = Arrays.stream(ideasStrings)
                                   .skip(1)
                                   .limit(ideasStrings.length - 2)
                                   .map(ParseUtils::cleanString)
                                   .filter(Objects::nonNull)
                                   .collect(Collectors.toMap(s -> s.split("=")[0], s -> Utils.nullSafeParseLong(s.split("=")[1])
                                                                                             .orElse(0L)));
            }

            int govIndex = content.indexOf("\n\t\tgovernment={");
            if (govIndex >= 0) {
                String subContent = ParseUtils.getNextObject(content, govIndex);
                this.govType = ParseUtils.parseString(subContent, "\n\t\t\tgovernment").orElse(null);
                this.govReforms = ParseUtils.parseListString(subContent, "reforms");
            }

            int customNationalIdeasIndex = content.indexOf("custom_national_ideas={");
            if (customNationalIdeasIndex >= 0) {
                String subContent = ParseUtils.getNextObject(content, customNationalIdeasIndex);
                String[] ideasStrings = subContent.split("}");
                List<Long> levels = Arrays.stream(ideasStrings)
                                          .map(idea -> ParseUtils.parseLong(idea, "level"))
                                          .filter(Optional::isPresent)
                                          .map(Optional::get)
                                          .collect(Collectors.toList());
                List<Long> indexes = Arrays.stream(ideasStrings)
                                           .map(idea -> ParseUtils.parseLong(idea, "index"))
                                           .filter(Optional::isPresent)
                                           .map(Optional::get)
                                           .collect(Collectors.toList());

                if (levels.size() == indexes.size()) {
                    this.customIdeas = new LinkedHashMap<>();
                    for (int i = 0; i < levels.size(); i++) {
                        this.customIdeas.put(indexes.get(i), levels.get(i));
                    }
                }
            }

            ParseUtils.parseLong(content, "custom_national_ideas_level")
                      .ifPresent(customIdeasLevel -> ideas.put("custom_national_ideas_level", customIdeasLevel));

            if (content.contains("changed_tag_from")) {
                String historyContent = ParseUtils.getNextObject(content, content.indexOf("\n\t\thistory"));
                this.ancientsTags = new LinkedHashMap<>();
                Arrays.stream(historyContent.split("(?=\\n\\t\\t\\t[0-9]{4}\\.(1[0-2]?|[1-9])\\.([1-9]|[1-2][0-9]|3[0-1])=\\{)"))
                      .skip(1)
                      .map(ParseUtils::cleanString)
                      .filter(Objects::nonNull)
                      .filter(history -> !history.startsWith(finalStartDate) &&
                                         history.contains("changed_tag_from")) //For changed_tag don't want the change at the start of the game (custom country)
                      .forEachOrdered(s -> {
                          try {
                              this.ancientsTags.put(Constants.DATE_FORMAT.parse(s.substring(0, s.indexOf("="))),
                                                    ParseUtils.parseString(s, "changed_tag_from").orElse(""));
                          } catch (ParseException e) {
                              LOGGER.error("Can't parse date for changed tag from for {}: {}", this.tag, e.getMessage());
                          }
                      });
            }
        } catch (Exception e) {
            LOGGER.error("An exception occurred for country {}: {}", this.tag, e.getMessage());
            throw e;
        }
    }
}
