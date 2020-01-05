package com.k2sw.opentf;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisiblePlayer {
  public int id;
  public VisibleCard[] hand;
  public VisibleCard[] tableau;
  public VisibleResources resources;

  public VisiblePlayer(int id, VisibleCard[] hand, VisibleCard[] tableau, VisibleResources resources) {
    this.id = id;
    this.hand = hand;
    this.tableau = tableau;
    this.resources = resources;
  }

  public int getId() {
    return id;
  }

  public VisibleCard[] getHand() {
    return hand;
  }

  public VisibleCard[] getTableau() {
    return tableau;
  }

  public VisibleResources getResources() {
    return resources;
  }
}
