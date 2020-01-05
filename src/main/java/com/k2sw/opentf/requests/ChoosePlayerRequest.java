package com.k2sw.opentf.requests;

public class ChoosePlayerRequest {
  private boolean canBeSelf;

  public ChoosePlayerRequest(boolean canBeSelf) {
    this.canBeSelf = canBeSelf;
  }

  public boolean isCanBeSelf() {
    return canBeSelf;
  }
}
