package fr.graynaud.eu4saveconverter.service.object.save;

import fr.graynaud.eu4saveconverter.common.ParseUtils;

public class QuantifyDatableRelation extends DatableRelation {

    private Double amount;

    public QuantifyDatableRelation(String content) {
        super(content);
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public void parse(String content) {
        super.parse(content);
        this.amount = ParseUtils.parseDouble(content, "amount").orElse(null);
    }
}
