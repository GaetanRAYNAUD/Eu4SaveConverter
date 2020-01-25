package fr.graynaud.eu4saveconverter.controller.dto;

import fr.graynaud.eu4saveconverter.service.object.save.Country;
import fr.graynaud.eu4saveconverter.service.object.save.CustomColors;
import fr.graynaud.eu4saveconverter.service.object.save.Province;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class CountryDTO {

    private String tag;

    private String player;

    private Boolean isRevolutionTarget;

    private Boolean isHreEmperor;

    private Boolean isCustom;

    private String govName;

    private Long govRank;

    private List<Boolean> institutions;

    private Map<LocalDate, String> ancientsTags;

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

    private Double greatPowerScore;

    private Double averageUnrest;

    private Double averageAutonomy;

    private Double income;

    private Double armyTradition;

    private Double navyTradition;

    private Long debt;

    private Double absolutism;

    private Double professionalism;

    private Map<String, Long> ideas;

    private Long totalIdeas;

    private String govType;

    private Long maxManpower;

    private Long maxSailors;

    private List<Long> tradeBonus;

    private Long losses;

    private Double innovativeness;

    private Long nbProvinces;

    private Double totalDev;

    private Long nbBuildings;

    private Long forceLimit;

    public CountryDTO(String tag, String player, Country country, Boolean isRevolutionTarget, List<Province> provinces,
                      Boolean isHreEmperor, Boolean isDependency, Boolean isMarch, List<Country> vassals,
                      List<Country> marches, List<Country> colonies) {
        this.tag = tag;
        this.player = player;
        this.govName = country.getGovName();
        this.govRank = country.getGovRank();
        this.institutions = country.getInstitutions();
        this.ancientsTags = country.getAncientsTags();
        this.capital = country.getCapital();
        this.mapColor = country.getMapColor();
        this.countryColor = country.getCountryColor();
        this.religion = country.getReligion();
        this.culture = country.getCulture();
        this.techGroup = country.getTechGroup();
        this.admTech = country.getAdmTech();
        this.dipTech = country.getDipTech();
        this.milTech = country.getMilTech();
        this.greatPowerScore = country.getGreatPowerScore();
        this.averageUnrest = country.getAverageUnrest();
        this.averageAutonomy = country.getAverageAutonomy();
        this.income = country.getIncome();
        this.armyTradition = country.getArmyTradition();
        this.navyTradition = country.getNavyTradition();
        this.debt = country.getDebt();
        this.absolutism = country.getAbsolutism();
        this.professionalism = country.getProfessionalism();
        this.ideas = country.getIdeas();
        this.totalIdeas = this.ideas.values().stream().mapToLong(Long::longValue).sum();
        this.govType = country.getGovType();
        this.maxManpower = country.getMaxManpower();
        this.maxSailors = country.getMaxSailors();
        this.tradeBonus = country.getTradeBonus();
        this.losses = country.getLosses();
        this.innovativeness = country.getInnovativeness();

        if (isHreEmperor) {
            this.isHreEmperor = true;
        }

        if (this.tag.startsWith("D")) { //Is custom
            this.isCustom = true;
            this.name = country.getName();
            this.customColors = country.getCustomColors();
        }

        if (isRevolutionTarget) {
            this.isRevolutionTarget = true;
            this.revolutionaryColors = country.getRevolutionaryColors();
        }

        this.nbProvinces = (long) provinces.size();
        this.totalDev = provinces.stream()
                                 .mapToDouble(p -> p.getBaseTax() + p.getBaseProd() + p.getBaseManpower())
                                 .sum();
        this.nbBuildings = provinces.stream()
                                    .filter(p -> p.getBuildings() != null)
                                    .mapToLong(p -> p.getBuildings().size())
                                    .sum();
        computeForceLimit(isDependency, isMarch, provinces, country, vassals, marches, colonies);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Boolean isRevolutionTarget() {
        return isRevolutionTarget;
    }

    public void setRevolutionTarget(Boolean revolutionTarget) {
        isRevolutionTarget = revolutionTarget;
    }

    public Boolean isHreEmperor() {
        return isHreEmperor;
    }

    public void setHreEmperor(Boolean hreEmperor) {
        isHreEmperor = hreEmperor;
    }

    public Boolean isCustom() {
        return isCustom;
    }

    public void setCustom(Boolean custom) {
        isCustom = custom;
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

    public List<Boolean> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Boolean> institutions) {
        this.institutions = institutions;
    }

    public Map<LocalDate, String> getAncientsTags() {
        return ancientsTags;
    }

    public void setAncientsTags(Map<LocalDate, String> ancientsTags) {
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

    public Double getGreatPowerScore() {
        return greatPowerScore;
    }

    public void setGreatPowerScore(Double greatPowerScore) {
        this.greatPowerScore = greatPowerScore;
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

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
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

    public String getGovType() {
        return govType;
    }

    public void setGovType(String govType) {
        this.govType = govType;
    }

    public Long getMaxManpower() {
        return maxManpower;
    }

    public void setMaxManpower(Long maxManpower) {
        this.maxManpower = maxManpower;
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

    public Long getTotalIdeas() {
        return totalIdeas;
    }

    public void setTotalIdeas(Long totalIdeas) {
        this.totalIdeas = totalIdeas;
    }

    public Long getNbProvinces() {
        return nbProvinces;
    }

    public void setNbProvinces(Long nbProvinces) {
        this.nbProvinces = nbProvinces;
    }

    public Double getTotalDev() {
        return totalDev;
    }

    public void setTotalDev(Double totalDev) {
        this.totalDev = totalDev;
    }

    public Long getNbBuildings() {
        return nbBuildings;
    }

    public void setNbBuildings(Long nbBuildings) {
        this.nbBuildings = nbBuildings;
    }

    private void computeForceLimit(Boolean isDependency, Boolean isMarch, List<Province> provinces, Country country,
                                   List<Country> vassals, List<Country> marches, List<Country> colonies) {
        long forceLimit = 6L; //Base for every nation
        AtomicLong forceLimitModifier = new AtomicLong(0L);
        AtomicLong subjectForceLimitModifier = new AtomicLong(0L);

        if (isDependency) {
            forceLimit -= 3;
        }

        if (isMarch) {
            forceLimitModifier.addAndGet(30);
        }

        if (Boolean.TRUE.equals(this.isRevolutionTarget)) {
            forceLimitModifier.addAndGet(40);
        }

        if (this.tag.equals("JMN")) {
            forceLimit += 100;
        }

        if (this.tradeBonus.contains(1L)) {
            forceLimitModifier.addAndGet(20);
        }

        this.ideas.forEach((idea, level) -> {
            switch (idea) {
                case "RUS_ideas":
                    if (level >= 4) {
                        forceLimitModifier.addAndGet(50);
                    }
                    break;

                case "quantity_ideas":
                    if (level == 7) {
                        forceLimitModifier.addAndGet(50);
                    }
                    break;

                case "SCO_ideas":
                    forceLimitModifier.addAndGet(33);
                    break;

                case "MOS_ideas":
                case "TUR_ideas":
                    if (level == 7) {
                        forceLimitModifier.addAndGet(33);
                    }

                    break;

                case "CHICK_ideas":
                case "daimyo_ideas":
                case "NPL_ideas":
                case "STK_ideas":
                case "SND_ideas":
                    forceLimitModifier.addAndGet(25);
                    break;

                case "RUM_ideas":
                case "SUK_ideas":
                case "WLS_ideas":
                case "PRM_ideas":
                    if (level == 7) {
                        forceLimitModifier.addAndGet(25);
                    }

                    break;

                case "HES_ideas":
                    if (level >= 6) {
                        forceLimitModifier.addAndGet(25);
                    }

                    break;

                case "MOL_ideas":
                    if (level >= 3) {
                        forceLimitModifier.addAndGet(25);
                    }

                    break;

                case "RYA_ideas":
                    if (level >= 1) {
                        forceLimitModifier.addAndGet(25);
                    }

                    break;

                case "TPR_ideas":
                    if (level >= 4) {
                        forceLimitModifier.addAndGet(25);
                    }

                    break;

                case "ARW_ideas":
                case "IMG_ideas":
                case "INC_ideas":
                case "MAZ_ideas":
                case "SHN_ideas":
                case "TKI_ideas":
                case "TVE_ideas":
                    forceLimitModifier.addAndGet(20);
                    break;

                case "BAL_ideas":
                case "LXA_ideas":
                case "laotian_ideas":
                case "somali_ideas":
                    if (level == 7) {
                        forceLimitModifier.addAndGet(20);
                    }

                    break;

                case "offensive_ideas":
                    if (level >= 6) {
                        forceLimitModifier.addAndGet(20);
                    }

                    break;

                case "ALB_ideas":
                    if (level >= 1) {
                        forceLimitModifier.addAndGet(20);
                    }

                    break;

                case "CRN_ideas":
                    if (level >= 5) {
                        forceLimitModifier.addAndGet(20);
                    }

                    break;

                case "AKT_ideas":
                    if (level >= 3) {
                        forceLimitModifier.addAndGet(20);
                    }

                    break;

                case "HSK_ideas":
                case "DEC_ideas":
                    if (level >= 4) {
                        forceLimitModifier.addAndGet(20);
                    }

                    break;

                case "MRI_ideas":
                case "MNS_ideas":
                case "AVA_ideas":
                    if (level >= 2) {
                        forceLimitModifier.addAndGet(20);
                    }

                    break;

                case "ROM_ideas":
                case "TYR_ideas":
                    if (level >= 4) {
                        forceLimitModifier.addAndGet(15);
                    }

                    break;

                case "influence_ideas":
                case "ASK_ideas":
                    if (level == 7) {
                        subjectForceLimitModifier.addAndGet(100);
                    }

                    break;

                case "BOS_ideas":
                    if (level >= 5) {
                        subjectForceLimitModifier.addAndGet(100);
                    }

                    break;

                case "ADU_ideas":
                    if (level >= 2) {
                        subjectForceLimitModifier.addAndGet(100);
                    }

                    break;
            }
        });

        forceLimitModifier.addAndGet(country.getPolicies()
                                            .stream()
                                            .filter(p -> "pen_rely_on_sword_act".equals(p) ||
                                                         "agricultural_cultivations".equals(p) ||
                                                         "colonial_garrisons".equals(p))
                                            .count() * 10);

        subjectForceLimitModifier.addAndGet(country.getPolicies()
                                                   .stream()
                                                   .filter(p -> "autonomous_estates".equals(p) ||
                                                                "vassal_obligations_act".equals(p) ||
                                                                "unified_army_command".equals(p))
                                                   .count() * 100);

        forceLimit += vassals.size() * (subjectForceLimitModifier.get() / 100);
        forceLimit += marches.size() * (subjectForceLimitModifier.get() / 100);

        forceLimit += provinces.stream()
                               .map(Province::getBuildings)
                               .filter(Objects::nonNull)
                               .flatMap(map -> map.keySet().stream())
                               .filter("native_fortified_house"::equals)
                               .count() * 10;

        this.forceLimit = forceLimit * (forceLimitModifier.get() / 100);
    }
}
