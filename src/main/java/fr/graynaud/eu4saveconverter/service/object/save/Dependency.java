package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

public class Dependency extends DatableRelation {

    private SubjectType subjectType;

    public Dependency(String content) {
        super(content);
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    @Override
    public void parse(String content) {
        super.parse(content);
        this.subjectType = SubjectType.getByValue(ParseUtils.parseString(content, "subject_type").orElse(null));
    }
}
