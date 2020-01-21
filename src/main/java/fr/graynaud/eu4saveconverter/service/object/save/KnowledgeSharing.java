package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

public class KnowledgeSharing extends EndDatableRelation {

    private Boolean subjectInteractions;

    public KnowledgeSharing(String content) {
        super(content);
    }

    public Boolean getSubjectInteractions() {
        return subjectInteractions;
    }

    public void setSubjectInteractions(Boolean subjectInteractions) {
        this.subjectInteractions = subjectInteractions;
    }

    @Override
    public void parse(String content) {
        super.parse(content);
        this.subjectInteractions = ParseUtils.parseBoolean(content, "subject_interactions").orElse(null);
    }
}
