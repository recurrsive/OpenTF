package com.k2sw.opentf;

import com.k2sw.opentf.requests.ChoosePlayerRequest;
import com.k2sw.opentf.requests.PlaceTileRequest;

public class Request {
  private PlaceTileRequest placeTile;
  private ChoosePlayerRequest choosePlayer;
}

/*
  Types
  -------------
  Place tile (with tile restriction)
  Choose player
  Choose card (with tag restriction)
  Choose amount of resource to pay (min amount, max amount)
  Choose amount of production to pay (min amount, max amount)
  Choose to buy card or not (with card)
  Choose which effect to perform (with list of effects)
 */
