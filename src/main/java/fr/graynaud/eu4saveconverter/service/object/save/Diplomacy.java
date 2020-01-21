package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Diplomacy extends Eu4Object {

    private List<Dependency> dependencies;

    private List<DatableRelation> alliances;

    private List<DatableRelation> guarantees;

    private List<KnowledgeSharing> knowledgeSharing;

    private List<Subsidies> subsidies;

    public Diplomacy(String content) {
        super(content);
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<DatableRelation> getAlliances() {
        return alliances;
    }

    public void setAlliances(List<DatableRelation> alliances) {
        this.alliances = alliances;
    }

    public List<DatableRelation> getGuarantees() {
        return guarantees;
    }

    public void setGuarantees(List<DatableRelation> guarantees) {
        this.guarantees = guarantees;
    }

    public List<KnowledgeSharing> getKnowledgeSharing() {
        return knowledgeSharing;
    }

    public void setKnowledgeSharing(List<KnowledgeSharing> knowledgeSharing) {
        this.knowledgeSharing = knowledgeSharing;
    }

    public List<Subsidies> getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(List<Subsidies> subsidies) {
        this.subsidies = subsidies;
    }

    @Override
    public void parse(String content) {
        content = ParseUtils.getNextObject(content, content.indexOf("\ndiplomacy={"));
        this.dependencies = ParseUtils.getListSameObject(content, "dependency")
                                      .stream()
                                      .map(Dependency::new)
                                      .collect(Collectors.toList());
        this.alliances = ParseUtils.getListSameObject(content, "alliance")
                                   .stream()
                                   .map(DatableRelation::new)
                                   .collect(Collectors.toList());
        this.guarantees = ParseUtils.getListSameObject(content, "guarantee")
                                    .stream()
                                    .map(DatableRelation::new)
                                    .collect(Collectors.toList());
        this.knowledgeSharing = ParseUtils.getListSameObject(content, "knowledge_sharing")
                                          .stream()
                                          .map(KnowledgeSharing::new)
                                          .collect(Collectors.toList());
        this.subsidies = ParseUtils.getListSameObject(content, "subsidies")
                                   .stream()
                                   .map(Subsidies::new)
                                   .collect(Collectors.toList());
    }
}
