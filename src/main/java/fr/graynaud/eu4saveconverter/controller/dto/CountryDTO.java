package fr.graynaud.eu4saveconverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.graynaud.eu4saveconverter.common.Areas;
import fr.graynaud.eu4saveconverter.service.object.save.Country;
import fr.graynaud.eu4saveconverter.service.object.save.CustomColors;
import fr.graynaud.eu4saveconverter.service.object.save.Province;
import fr.graynaud.eu4saveconverter.service.object.save.SubjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Schema(name = "Country")
public class CountryDTO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDTO.class);

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

    private Double totalDevelopment;

    private Long nbBuildings;

    private Long forceLimit;

    @JsonIgnore
    private Boolean isInHre;

    @JsonIgnore
    private Map<Long, Province> provinces;

    @JsonIgnore
    private List<String> policies;

    @JsonIgnore
    private Map<SubjectType, List<CountryDTO>> dependencies;

    @JsonIgnore
    private Boolean isDependency;

    @JsonIgnore
    private Boolean isMarch;

    @JsonIgnore
    private List<String> advisors;

    @JsonIgnore
    private Map<Long, Long> customIdeas;

    @JsonIgnore
    private List<String> govReforms;

    @JsonIgnore
    private Double statistsVsMonarchists;

    @JsonIgnore
    private List<String> investments;

    @JsonIgnore
    private List<String> states;

    public CountryDTO() {
    }

    public CountryDTO(Country country, Boolean isRevolutionTarget, Boolean isHreEmperor, List<Province> provinces, Map<Long, String> advisors, List<String> investments, List<String> states) {
        this.tag = country.getTag();
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
        this.policies = country.getPolicies();
        this.provinces = provinces.stream().collect(Collectors.toMap(Province::getId, Function.identity()));
        this.advisors = country.getAdvisors().stream().map(advisors::get).collect(Collectors.toList());
        this.customIdeas = country.getCustomIdeas();
        this.govReforms = country.getGovReforms();
        this.statistsVsMonarchists = country.getStatistsVsMonarchists();
        this.investments = investments;
        this.states = states;

        if (isHreEmperor) {
            this.isHreEmperor = true;
        }

        if (this.provinces.get(this.capital).isPartHRE()) {
            this.isInHre = true;
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

        this.nbProvinces = (long) this.provinces.size();
        this.totalDevelopment = this.provinces.values()
                                              .stream()
                                              .mapToDouble(p -> p.getBaseTax() + p.getBaseProd() + p.getBaseManpower())
                                              .sum();
        this.nbBuildings = this.provinces.values()
                                         .stream()
                                         .filter(p -> p.getBuildings() != null)
                                         .mapToLong(p -> p.getBuildings().size())
                                         .sum();
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

    public Double getTotalDevelopment() {
        return totalDevelopment;
    }

    public void setTotalDevelopment(Double totalDevelopment) {
        this.totalDevelopment = totalDevelopment;
    }

    public Long getNbBuildings() {
        return nbBuildings;
    }

    public void setNbBuildings(Long nbBuildings) {
        this.nbBuildings = nbBuildings;
    }

    public Long getForceLimit() {
        return forceLimit;
    }

    public void setForceLimit(Long forceLimit) {
        this.forceLimit = forceLimit;
    }

    public Map<SubjectType, List<CountryDTO>> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Map<SubjectType, List<CountryDTO>> dependencies) {
        this.dependencies = dependencies;
    }

    public Boolean isDependency() {
        return isDependency;
    }

    public void setIsDependency(Boolean isDependency) {
        this.isDependency = isDependency;
    }

    public Boolean isMarch() {
        return isMarch;
    }

    public void setIsMarch(Boolean isMarch) {
        this.isMarch = isMarch;
    }

    public Boolean isInHre() {
        return isInHre;
    }

    public void setIsInHre(Boolean isInHre) {
        this.isInHre = isInHre;
    }

    public void computePostData(long nbCountriesInHre, String eastSlavicCultureFullController) {
        computeForceLimit(nbCountriesInHre, eastSlavicCultureFullController);
    }

    private void computeForceLimit(long nbCountriesInHre, String eastSlavicCultureFullController) {
        AtomicReference<BigDecimal> forceLimit = new AtomicReference<>(BigDecimal.valueOf(6d)); //Base for every nation
        AtomicLong forceLimitModifier = new AtomicLong(100L);
        AtomicLong subjectForceLimitModifier = new AtomicLong(100L);

        if (Boolean.TRUE.equals(this.isDependency)) {
            forceLimit.set(forceLimit.get().subtract(BigDecimal.valueOf(3)));
        }

        if (Boolean.TRUE.equals(this.isMarch)) { //Need to compute the liberty desire because if more than 50% this bonus is not active
            forceLimitModifier.addAndGet(30);
        }

        if (Boolean.TRUE.equals(this.isRevolutionTarget)) {
            forceLimitModifier.addAndGet(40);
        }

        if (Boolean.TRUE.equals(this.isHreEmperor)) {
            forceLimit.set(forceLimit.get().add(BigDecimal.valueOf((double) nbCountriesInHre / 2)));
        }

        if (this.tag.equals("JMN")) {
            forceLimit.set(forceLimit.get().add(BigDecimal.valueOf(100)));
        }

        if (this.tradeBonus.contains(1L)) {
            forceLimitModifier.addAndGet(20);
        }

        if (this.advisors.contains("army_organiser")) {
            forceLimitModifier.addAndGet(10);
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

                case "custom_national_ideas_level":
                    if (this.customIdeas.containsKey(81L)) { //81 is custom idea for FL
                        if (level == 7 || new ArrayList<>(this.customIdeas.keySet()).subList(7, 9)
                                                                                    .contains(81L)) { //Pos 7/8 are traditions
                            forceLimitModifier.addAndGet((long) (this.customIdeas.get(81L) *
                                                                 7.5)); //Each level of custom idea gives 7.5%
                        }

                        if (level != 0 &&
                            new ArrayList<>(this.customIdeas.keySet()).subList(0, Math.toIntExact(level))
                                                                      .contains(81L)) { //If idea is in the level's first ideas
                            forceLimitModifier.addAndGet((long) (this.customIdeas.get(81L) * 7.5));
                        }
                    }
            }
        });

        forceLimitModifier.addAndGet(this.policies
                                             .stream()
                                             .filter(p -> "pen_rely_on_sword_act".equals(p) ||
                                                          "agricultural_cultivations".equals(p) ||
                                                          "colonial_garrisons".equals(p))
                                             .count() * 10);

        subjectForceLimitModifier.addAndGet(this.policies
                                                    .stream()
                                                    .filter(p -> "autonomous_estates".equals(p) ||
                                                                 "vassal_obligations_act".equals(p) ||
                                                                 "unified_army_command".equals(p))
                                                    .count() * 100);

        List<CountryDTO> vassals = this.dependencies.getOrDefault(SubjectType.VASSAL, new ArrayList<>());
        List<CountryDTO> clients = this.dependencies.getOrDefault(SubjectType.CLIENT, new ArrayList<>());
        List<CountryDTO> marches = this.dependencies.getOrDefault(SubjectType.MARCH, new ArrayList<>());
        forceLimit.set(forceLimit.get()
                                 .add(
                                         (BigDecimal.valueOf(vassals.size())) // +1 per vassal
                                                                              .add(BigDecimal.valueOf(vassals.stream()
                                                                                                             .mapToLong(CountryDTO::getForceLimit)
                                                                                                             .sum())
                                                                                             .divide(BigDecimal.TEN, MathContext.DECIMAL64)) // +10% of vassal's FL
                                                                              .multiply(BigDecimal.valueOf(subjectForceLimitModifier
                                                                                                                   .get())
                                                                                                  .divide(BigDecimal.valueOf(100), MathContext.DECIMAL64)) // x subject modifier
                                     )
                      );
        forceLimit.set(forceLimit.get()
                                 .add(
                                         (BigDecimal.valueOf(clients.size())) // +1 per client
                                                                              .add(BigDecimal.valueOf(clients.stream()
                                                                                                             .mapToLong(CountryDTO::getForceLimit)
                                                                                                             .sum())
                                                                                             .divide(BigDecimal.TEN, MathContext.DECIMAL64)) // +10% of client's FL
                                                                              .multiply(BigDecimal.valueOf(subjectForceLimitModifier
                                                                                                                   .get())
                                                                                                  .divide(BigDecimal.valueOf(100), MathContext.DECIMAL64)) // x subject modifier
                                     )
                      );
        forceLimit.set(forceLimit.get()
                                 .add(
                                         (BigDecimal.valueOf(marches.size())) // +1 per march
                                                                              .add(BigDecimal.valueOf(marches.stream()
                                                                                                             .mapToLong(CountryDTO::getForceLimit)
                                                                                                             .sum())
                                                                                             .divide(BigDecimal.valueOf(5), MathContext.DECIMAL64)) // +20% of march's FL
                                                                              .multiply(BigDecimal.valueOf(subjectForceLimitModifier
                                                                                                                   .get())
                                                                                                  .divide(BigDecimal.valueOf(100), MathContext.DECIMAL64)) // x subject modifier
                                     )
                      );

        forceLimit.set(forceLimit.get()
                                 .add(
                                         BigDecimal.valueOf(
                                                 this.dependencies.getOrDefault(SubjectType.COLONY, new ArrayList<>())
                                                                  .stream()
                                                                  .filter(c -> c.getNbProvinces() >= 10)
                                                                  .count()
                                                           )
                                                   .multiply(BigDecimal.valueOf(5)) // +5 per colony that have at least 10 provinces
                                     )
                      );

        forceLimit.set(forceLimit.get()
                                 .add(
                                         BigDecimal.valueOf(provinces.values()
                                                                     .stream()
                                                                     .map(Province::getBuildings)
                                                                     .filter(Objects::nonNull)
                                                                     .flatMap(map -> map.keySet().stream())
                                                                     .filter("native_fortified_house"::equals)
                                                                     .count()
                                                           )
                                                   .multiply(BigDecimal.TEN)) //+10 per native_fortified_house
                      ); //Not in provinces because is not affected by autonomy

        forceLimitModifier.addAndGet(this.govReforms.stream()
                                                    .filter(reform -> "steppe_horde".equals(reform) ||
                                                                      "great_mongol_state_reform".equals(reform) ||
                                                                      "steppe_horde_legacy".equals(reform) ||
                                                                      "great_mongol_state_legacy".equals(reform))
                                                    .count() * 20);

        if ((this.govReforms.contains("dutch_republic") || this.govReforms.contains("dutch_republic_legacy")) &&
            this.statistsVsMonarchists != null && this.statistsVsMonarchists > 0) {
            forceLimitModifier.addAndGet(25);
        }

        if (eastSlavicCultureFullController != null && this.govReforms.contains("mughal_government") &&
            eastSlavicCultureFullController.equals(this.tag)) {
            forceLimitModifier.addAndGet(10);
        }

        forceLimit.set(forceLimit.get()
                                 .add(
                                         BigDecimal.valueOf(this.investments.stream()
                                                                            .filter("officers_mess"::equals)
                                                                            .count()
                                                           )
                                                   .multiply(BigDecimal.valueOf(5))
                                     )
                      );

        AtomicReference<BigDecimal> provincesForceLimit = new AtomicReference<>(BigDecimal.ZERO);
        this.provinces.values().forEach(province -> {
            BigDecimal localForceLimit = BigDecimal.ZERO;
            localForceLimit = localForceLimit.add(BigDecimal.valueOf(province.getBaseTax()).divide((BigDecimal.TEN), MathContext.DECIMAL64));
            localForceLimit = localForceLimit.add(BigDecimal.valueOf(province.getBaseProd()).divide((BigDecimal.TEN), MathContext.DECIMAL64));
            localForceLimit = localForceLimit.add(BigDecimal.valueOf(province.getBaseManpower()).divide((BigDecimal.TEN), MathContext.DECIMAL64));

            if ("grain".equals(province.getGood())) {
                localForceLimit = localForceLimit.add(BigDecimal.ONE.divide(BigDecimal.valueOf(2), MathContext.DECIMAL64));
            }

            if (province.getBuildings() != null) {
                if (province.getBuildings().containsKey("regimental_camp")) {
                    localForceLimit = localForceLimit.add(BigDecimal.ONE);
                }

                if (province.getBuildings().containsKey("conscription_center")) {
                    localForceLimit = localForceLimit.add(BigDecimal.valueOf(2));
                }
            }

            //Rajputs, Nobility, Cossacks, Tribes remove the effect of local autonomy
            if (province.getEstate() == null || (province.getEstate() != 2 && province.getEstate() != 6 &&
                                                 province.getEstate() != 8 && province.getEstate() != 9)) {
                if (province.getEstate() != null && province.getAutonomy() < 25d) {
                    province.setAutonomy(25d);
                }

                if (!this.states.contains(Areas.provinceArea.get(province.getId())) && province.getAutonomy() < 75d) {
                    province.setAutonomy(75d);
                }

                localForceLimit = localForceLimit.multiply(BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(province.getAutonomy()))).divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
            }

            forceLimit.set(forceLimit.get().add(localForceLimit));
            provincesForceLimit.set(provincesForceLimit.get().add(localForceLimit));
        });

        this.forceLimit = forceLimit.get().multiply(BigDecimal.valueOf(forceLimitModifier.get()).divide(BigDecimal.valueOf(100), MathContext.DECIMAL64)).longValue();
    }
}
