package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

public class Dependency extends DatableRelation {

    private String subjectType;

    public Dependency(String content) {
        super(content);
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    @Override
    public void parse(String content) {
        super.parse(content);
        this.subjectType = ParseUtils.parseString(content, "subject_type").orElse(null);
    }
}
