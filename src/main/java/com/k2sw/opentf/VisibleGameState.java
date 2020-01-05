package com.k2sw.opentf;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisibleGameState {
  public VisiblePlayer[] players;
  public int active;
  public VisibleTile[][] board;

  public VisibleGameState(VisiblePlayer[] players, int active, VisibleTile[][] board) {
    this.players = players;
    this.active = active;
    this.board = board;
  }

  public VisiblePlayer[] getPlayers() {
    return players;
  }

  public int getActive() {
    return active;
  }

  public VisibleTile[][] getBoard() {
    return board;
  }
}
