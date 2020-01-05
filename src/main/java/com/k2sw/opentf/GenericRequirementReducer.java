package com.k2sw.opentf;

public class GenericRequirementReducer extends Reducer {
    private int discount;

    public GenericRequirementReducer(int discount) {
        this.discount = discount;
    }

    @Override
    public int reduceCost(CardTag[] tags, boolean genericProject, int amount) {
        return amount;
    }

    @Override
    public Requirement changeRequirement(Requirement req) {
        if (req instanceof TemperatureRequirement) {
            if (((TemperatureRequirement) req).isGreater())
                return new TemperatureRequirement(((TemperatureRequirement) req).getAmount() - discount, true);
            else
                return new TemperatureRequirement(((TemperatureRequirement) req).getAmount() + discount, false);
        }
        else if (req instanceof OxygenRequirement) {
            if (((OxygenRequirement) req).isGreater())
                return new OxygenRequirement(((OxygenRequirement) req).getAmount() - discount, true);
            else
                return new OxygenRequirement(((OxygenRequirement) req).getAmount() + discount, false);
        }
        else if (req instanceof OceanRequirement) {
            if (((OceanRequirement) req).isGreater())
                return new OceanRequirement(((OceanRequirement) req).getAmount() - discount, true);
            else
                return new OceanRequirement(((OceanRequirement) req).getAmount() + discount, false);
        }
        else return req;
    }

    @Override
    public String getText() {
        return "Allows you to play cards with global requirements " + discount + " steps higher or lower than you normally could.";
    }
}
