package com.k2sw.opentf;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisibleResources {
  public int score;
  public VisibleResource mc;
  public VisibleResource steel;
  public VisibleResource titanium;
  public VisibleResource plants;
  public VisibleResource energy;
  public VisibleResource heat;

  public VisibleResources(int score, VisibleResource mc, VisibleResource steel, VisibleResource titanium, VisibleResource plants, VisibleResource energy, VisibleResource heat) {
    this.score = score;
    this.mc = mc;
    this.steel = steel;
    this.titanium = titanium;
    this.plants = plants;
    this.energy = energy;
    this.heat = heat;
  }

  public int getScore() {
    return score;
  }

  public VisibleResource getMc() {
    return mc;
  }

  public VisibleResource getSteel() {
    return steel;
  }

  public VisibleResource getTitanium() {
    return titanium;
  }

  public VisibleResource getPlants() {
    return plants;
  }

  public VisibleResource getEnergy() {
    return energy;
  }

  public VisibleResource getHeat() {
    return heat;
  }
}
