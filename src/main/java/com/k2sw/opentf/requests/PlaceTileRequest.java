package com.k2sw.opentf.requests;

import com.k2sw.opentf.ResourceType;
import com.k2sw.opentf.TileSlotType;
import com.k2sw.opentf.TileType;

public class PlaceTileRequest {
  private RestrictionType nearPlayerTile;
  private RestrictionType nearGreeneryTile;
  private RestrictionType nearAnyTile;
  private ResourceType resourceTypeRequired;
  private TileSlotType tileSlotRestriction;
  private RestrictionType tileSlotRestrictionType;
  private TileType adjacentTileRestriction;
  private RestrictionType adjacentTileRestrictionType;

  public PlaceTileRequest(RestrictionType nearPlayerTile, RestrictionType nearGreeneryTile, RestrictionType nearAnyTile, ResourceType resourceTypeRequired, TileSlotType tileSlotRestriction, RestrictionType tileSlotRestrictionType, TileType adjacentTileRestriction, RestrictionType adjacentTileRestrictionType) {
    this.nearPlayerTile = nearPlayerTile;
    this.nearGreeneryTile = nearGreeneryTile;
    this.nearAnyTile = nearAnyTile;
    this.resourceTypeRequired = resourceTypeRequired;
    this.tileSlotRestriction = tileSlotRestriction;
    this.tileSlotRestrictionType = tileSlotRestrictionType;
    this.adjacentTileRestriction = adjacentTileRestriction;
    this.adjacentTileRestrictionType = adjacentTileRestrictionType;
  }

  public RestrictionType getNearPlayerTile() {
    return nearPlayerTile;
  }

  public RestrictionType getNearGreeneryTile() {
    return nearGreeneryTile;
  }

  public RestrictionType getNearAnyTile() {
    return nearAnyTile;
  }

  public ResourceType getResourceTypeRequired() {
    return resourceTypeRequired;
  }

  public TileSlotType getTileSlotRestriction() {
    return tileSlotRestriction;
  }

  public RestrictionType getTileSlotRestrictionType() {
    return tileSlotRestrictionType;
  }

  public TileType getAdjacentTileRestriction() {
    return adjacentTileRestriction;
  }

  public RestrictionType getAdjacentTileRestrictionType() {
    return adjacentTileRestrictionType;
  }
}
