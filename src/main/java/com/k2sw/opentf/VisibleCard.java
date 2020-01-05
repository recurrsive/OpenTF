package com.k2sw.opentf;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisibleCard {
  public String name;
  public int cost;
  public int resource_count;
  public String requirement;
  public String[] tags;
  public String text;
  public boolean requirementsMet;
  public boolean activated;

  public VisibleCard(String name, int cost, int resource_count, String requirement, String[] tags, String text, boolean requirementsMet, boolean activated) {
    this.name = name;
    this.cost = cost;
    this.resource_count = resource_count;
    this.requirement = requirement;
    this.tags = tags;
    this.text = text;
    this.requirementsMet = requirementsMet;
    this.activated = activated;
  }

  public String getName() {
    return name;
  }

  public int getCost() {
    return cost;
  }

  public int getResource_count() {
    return resource_count;
  }

  public String getRequirement() {
    return requirement;
  }

  public String[] getTags() {
    return tags;
  }

  public String getText() {
    return text;
  }

  public boolean isRequirementsMet() {
    return requirementsMet;
  }

  public boolean isActivated() {
    return activated;
  }
}
