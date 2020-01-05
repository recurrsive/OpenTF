package com.k2sw.opentf;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisibleResource {
  public int amount;
  public int production;

  public VisibleResource(int amount, int production) {
    this.amount = amount;
    this.production = production;
  }

  public int getAmount() {
    return amount;
  }

  public int getProduction() {
    return production;
  }
}
