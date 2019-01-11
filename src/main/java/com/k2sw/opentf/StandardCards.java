package com.k2sw.opentf;

import com.k2sw.opentf.effects.*;

public class StandardCards {
    public static Card trees = new Card("Trees", 13, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new TemperatureRequirement(-4, true),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Plants, 3),
                    new IncreaseAmountEffect(ResourceType.Plants, 1)}),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card deepWellHeating = new Card("Deep Well Heating", 13, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseTemperatureEffect()}
            ),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card subterraneanReservoir = new Card("Subterranean Reservoir", 11, CardType.Event,
            new CardTag[]{CardTag.Event}, Global.NO_REQUIREMENT,
            new PlaceOceanTileEffect(),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card domedCrater = new Card("Domed Crater", 24, CardType.Auto,
            new CardTag[]{CardTag.City, CardTag.Steel}, new OxygenRequirement(7, false),
            new CompoundEffect(new Effect[]{
                    new IncreaseAmountEffect(ResourceType.Plants, 3),
                    new PlaceCityTileEffect(),
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 3)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card martianRails = new Card("Martian Rails", 13, CardType.Active,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Energy, 1),
                    new MartianRailsActionEffect()
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card asteroid = new Card("Asteroid", 14, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseTemperatureEffect(),
                    new IncreaseAmountEffect(ResourceType.Titanium, 2),
                    new DecreaseOpponentAmountEffect(ResourceType.Plants, 3)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card searchForLife = new Card("Search For Life", 3, CardType.Active,
            new CardTag[]{CardTag.Science, CardTag.UsesCounters}, new OxygenRequirement(6, false),
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.MegaCredits, 1),
                    new SearchForLifeActionEffect()
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            new SearchForLifeScorer(),
            Global.NO_REDUCER);

    public static Card capital = new Card("Capital", 26, CardType.Auto,
            new CardTag[]{CardTag.City, CardTag.Steel}, new OceanRequirement(4, true),
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 2),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 5),
                    new PlaceCapitalCityTileEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new CapitalScorer(),
            Global.NO_REDUCER);

    public static Card waterImportFromEuropa = new Card("Water Import From Europa", 25, CardType.Active,
            new CardTag[]{CardTag.Jovian, CardTag.Space}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new PayForCostEffect(new CardTag[]{CardTag.Space}, false, 12),
                    new PlaceOceanTileEffect(),
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            new OnePerAlliedJovianScorer(),
            Global.NO_REDUCER);

    public static Card equatorialMagnetizer = new Card("Equatorial Magnetizer", 11, CardType.Active,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseTerraformingScoreEffect(1)
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card noctisCity = new Card("Noctis City", 18, CardType.Auto,
            new CardTag[]{CardTag.City, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 3),
                    new PlaceNoctisCityTileEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card importedHydrogen = new Card("Imported Hydrogen", 16, CardType.Event,
            new CardTag[]{CardTag.City, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new PlaceOceanTileEffect(),
                    new OrEffect(new Effect[]{
                            new IncreaseAmountEffect(ResourceType.Plants, 3),
                            new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Microbes}, 3),
                            new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Animals}, 2),
                    })
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card researchOutpost = new Card("Research Outpost", 18, CardType.Event,
            new CardTag[]{CardTag.City, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new PlaceOceanTileEffect(),
                    new OrEffect(new Effect[]{
                            new IncreaseAmountEffect(ResourceType.Plants, 3),
                            new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Microbes}, 3),
                            new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Animals}, 2),
                    })
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            new GenericCardCostReducer(1));

    public static Card roverConstruction = new Card("Rover Construction", 8, CardType.Active,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new IncreaseAmountEffect(ResourceType.MegaCredits, 2),
            new TriggerType[]{TriggerType.CityPlaced},
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card phobosSpaceHaven = new Card("Phobos Space Haven", 25, CardType.Auto,
            new CardTag[]{CardTag.City, CardTag.Space}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Titanium, 1),
                    new PlaceOffBoardCityEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(3),
            Global.NO_REDUCER);

    public static Card adaptationTechnology = new Card("Adaptation Technology", 12, CardType.Active,
            new CardTag[]{CardTag.Science}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            new GenericRequirementReducer(2));

    public static Card moholeArea = new Card("Mohole Area", 20, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Heat, 4),
                    new PlaceSpecialTileOnOceanEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            new GenericRequirementReducer(2));

    public static Card optimalAerobraking = new Card("Optimal Aerobraking", 7, CardType.Active,
            new CardTag[]{CardTag.Space}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new IncreaseAmountEffect(ResourceType.MegaCredits, 3),
                    new IncreaseAmountEffect(ResourceType.Heat, 3)
            }),
            new TriggerType[]{TriggerType.SpaceEventPlayed},
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card predators = new Card("Predators", 14, CardType.Active,
            new CardTag[]{CardTag.Animals}, new OxygenRequirement(11, true),
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new RemoveCountersFromOtherCardEffect("Predators", new CardTag[]{CardTag.Animals}, 1),
                    new AddCountersToThisCardEffect("Predators", 1)
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            new PointsPerCounterScorer("Predators", 1, 1),
            Global.NO_REDUCER);

    public static Card arcticAlgae = new Card("Arctic Algae", 12, CardType.Active,
            new CardTag[]{CardTag.Plants}, new TemperatureRequirement(-12, false),
            new IncreaseAmountEffect(ResourceType.Plants, 1),
            Global.NULL_EFFECT,
            new IncreaseAmountEffect(ResourceType.Plants, 2),
            new TriggerType[]{TriggerType.OceanPlaced},
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card regolithEaters = new Card("Regolith Eaters", 13, CardType.Active,
            new CardTag[]{CardTag.Science, CardTag.Microbes}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            new OrEffect(new Effect[]{
                    new AddCountersToThisCardEffect("Regolith Eaters", 1),
                    new CompoundEffect(new Effect[]{
                            new RemoveCountersFromThisCardEffect("Regolith Eaters", 2),
                            new IncreaseOxygenEffect()
                    })
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card nitrogenRichAsteroid = new Card("Nitrogen-Rich Asteroid", 31, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseTerraformingScoreEffect(2),
                    new IncreaseTemperatureEffect(),
                    new OrEffect(new Effect[]{
                            new IncreaseProductionEffect(ResourceType.Plants, 1),
                            new NitrogenRichAsteroidConditionalRaiseProductionEffect()
                    })
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card naturalPreserve = new Card("Natural Reserve", 9, CardType.Auto,
            new CardTag[]{CardTag.Science, CardTag.Steel}, new OxygenRequirement(4, false),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 1),
                    new PlaceSpecialTileNotNextToTileEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card fish = new Card("Fish", 9, CardType.Auto,
            new CardTag[]{CardTag.Science, CardTag.Steel}, new TemperatureRequirement(2, true),
            new DecreaseOpponentProductionEffect(ResourceType.Plants, 1),
            new AddCountersToThisCardEffect("Fish", 1),
            Global.NULL_EFFECT,
            new TriggerType[0],
            new PointsPerCounterScorer("Fish", 1, 1),
            Global.NO_REDUCER);

    public static Card beamFromAThoriumAsteroid = new Card("Beam From A Thorium Asteroid", 32, CardType.Auto,
            new CardTag[]{CardTag.Jovian, CardTag.Space, CardTag.Energy}, new TagCountRequirement(CardTag.Jovian, 1),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Heat, 3),
                    new IncreaseProductionEffect(ResourceType.Energy, 3)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card mangrove = new Card("Mangrove", 12, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new TemperatureRequirement(4, true),
            new CompoundEffect(new Effect[] {
                    new PlaceMangrovePlantTileEffect(),
                    new IncreaseOxygenEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card miningRights = new Card("Mining Rights", 9, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new PlaceMiningRightsTileEffect(),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card powerGrid = new Card("Power Grid", 18, CardType.Auto,
            new CardTag[]{CardTag.Energy}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionByTagCountEffect(ResourceType.Energy, CardTag.Energy),
                    new IncreaseProductionEffect(ResourceType.Energy, 1)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card artificialLake = new Card("Artificial Lake", 15, CardType.Auto,
            new CardTag[]{CardTag.Steel}, new TemperatureRequirement(-6, true),
            new PlaceArtificialLakeTileEffect(),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card urbanizedArea = new Card("Urbanized Area", 10, CardType.Auto,
            new CardTag[]{CardTag.City, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new PlaceUrbanizedAreaTileEffect(),
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card ecologicalZone = new Card("Ecological Zone", 12, CardType.Active,
            new CardTag[]{CardTag.Plants, CardTag.Animals, CardTag.UsesCounters}, new HaveGreeneryRequirement(),
            new CompoundEffect(new Effect[]{
                    new PlaceEcologicalZoneTileEffect(),
                    new AddCountersToThisCardEffect("Ecological Zone", 2)
            }),
            Global.NULL_EFFECT,
            new AddCountersToThisCardPerTagEffect("Ecological Zone", new CardTag[]{CardTag.Plants, CardTag.Animals}),
            new TriggerType[]{TriggerType.PlantTagPlayed, TriggerType.AnimalTagPlayed},
            new PointsPerCounterScorer("Ecological Zone", 1, 2),
            Global.NO_REDUCER);

    public static Card zeppelins = new Card("Zeppelins", 13, CardType.Auto,
            new CardTag[]{CardTag.City, CardTag.Steel}, new OxygenRequirement(5, true),
            new IncreaseProductionByTileCountEffect(ResourceType.MegaCredits, TileType.City),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card herbivores = new Card("Herbivores", 8, CardType.Active,
            new CardTag[]{CardTag.Plants, CardTag.UsesCounters}, new OxygenRequirement(8, true),
            new DecreaseOpponentProductionEffect(ResourceType.Plants, 1),
            Global.NULL_EFFECT,
            new AddCountersToThisCardEffect("Herbivores", 1),
            new TriggerType[]{TriggerType.PlantsPlaced},
            new PointsPerCounterScorer("Herbivores", 1, 2),
            Global.NO_REDUCER);

    public static Card insulation = new Card("Insulation", 2, CardType.Auto,
            new CardTag[0], Global.NO_REQUIREMENT,
            new VariableConvertProductionEffect(ResourceType.Heat, ResourceType.MegaCredits),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card shuttles = new Card("Shuttles", 10, CardType.Active,
            new CardTag[]{CardTag.Space}, new OxygenRequirement(5, true),
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            new TagRestrictedCostReducer(CardTag.Space, 2));

    public static Card protectedValley = new Card("Insulation", 23, CardType.Auto,
            new CardTag[]{CardTag.Plants, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 2),
                    new PlacePlantsTileOnOceanEffect(),
                    new IncreaseOxygenEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card colonizerTrainingCamp = new Card("Colonizer Training Camp", 8, CardType.Auto,
            new CardTag[]{CardTag.Jovian, CardTag.Steel}, new OxygenRequirement(5, false),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(2),
            Global.NO_REDUCER);

    public static Card cloudSeeding = new Card("Cloud Seeding", 11, CardType.Auto,
            new CardTag[0], new OceanRequirement(3, true),
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.MegaCredits, 1),
                    new DecreaseOpponentProductionEffect(ResourceType.Heat, 1),
                    new IncreaseProductionEffect(ResourceType.Plants, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card comet = new Card("Comet", 21, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseTemperatureEffect(),
                    new PlaceOceanTileEffect(),
                    new DecreaseOpponentAmountEffect(ResourceType.Plants, 3)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card bigAsteroid = new Card("Big Asteroid", 27, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseTemperatureEffect(),
                    new IncreaseTemperatureEffect(),
                    new IncreaseAmountEffect(ResourceType.Titanium, 4),
                    new DecreaseOpponentAmountEffect(ResourceType.Plants, 4)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card methaneFromTitan = new Card("Methane From Titan", 28, CardType.Auto,
            new CardTag[]{CardTag.Jovian, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Heat, 2),
                    new IncreaseProductionEffect(ResourceType.Plants, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(2),
            Global.NO_REDUCER);

    public static Card blackPolarDust = new Card("Methane From Titan", 28, CardType.Auto,
            new CardTag[0], Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.MegaCredits, 2),
                    new IncreaseProductionEffect(ResourceType.Heat, 3),
                    new PlaceOceanTileEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card eosChasmaNationalPark = new Card("Eos Chasma National Park", 16, CardType.Auto,
            new CardTag[]{CardTag.Plants, CardTag.Steel}, new TemperatureRequirement(-12, true),
            new CompoundEffect(new Effect[]{
                    new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Animals}, 1),
                    new IncreaseAmountEffect(ResourceType.Plants, 3),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card cupolaCity = new Card("Cupola City", 16, CardType.Auto,
            new CardTag[]{CardTag.City, CardTag.Steel}, new OxygenRequirement(9, false),
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 3),
                    new PlaceCityTileEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card lunarBeam = new Card("Lunar Beam", 13, CardType.Auto,
            new CardTag[]{CardTag.Earth, CardTag.Energy}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.MegaCredits, 2),
                    new IncreaseProductionEffect(ResourceType.Heat, 2),
                    new IncreaseProductionEffect(ResourceType.Energy, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card undergroundCity = new Card("Underground City", 18, CardType.Auto,
            new CardTag[]{CardTag.City, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 2),
                    new IncreaseProductionEffect(ResourceType.Steel, 2),
                    new PlaceCityTileEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card ghgProducingBacteria = new Card("GHG Producing Bacteria", 8, CardType.Active,
            new CardTag[]{CardTag.Science, CardTag.Microbes, CardTag.UsesCounters}, new OxygenRequirement(4, true),
            Global.NULL_EFFECT,
            new OrEffect(new Effect[]{
                    new AddCountersToThisCardEffect("GHG Producing Bacteria", 1),
                    new CompoundEffect(new Effect[]{
                            new RemoveCountersFromThisCardEffect("GHG Producing Bateria", 2),
                            new IncreaseTemperatureEffect()
                    })
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card ants = new Card("Ants", 9, CardType.Active,
            new CardTag[]{CardTag.Microbes, CardTag.UsesCounters}, new OxygenRequirement(4, true),
            new CompoundEffect(new Effect[]{
                    new RemoveCountersFromOtherCardEffect("Ants", new CardTag[]{CardTag.Microbes}, 1),
                    new AddCountersToThisCardEffect("Ants", 1)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new PointsPerCounterScorer("Ants", 1, 2),
            Global.NO_REDUCER);

    public static Card releaseOfInertGases = new Card("Release Of Inert Gases", 14, CardType.Event,
            new CardTag[]{CardTag.Event}, Global.NO_REQUIREMENT,
            new IncreaseTerraformingScoreEffect(2),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card deimosDown = new Card("Deimos Down", 31, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseTemperatureEffect(),
                    new IncreaseTemperatureEffect(),
                    new IncreaseTemperatureEffect(),
                    new IncreaseAmountEffect(ResourceType.Titanium, 4),
                    new DecreaseOpponentAmountEffect(ResourceType.Plants, 8)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card asteroidMining = new Card("Asteroid Mining", 30, CardType.Auto,
            new CardTag[]{CardTag.Jovian, CardTag.Space}, Global.NO_REQUIREMENT,
            new IncreaseProductionEffect(ResourceType.Titanium, 2),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(2),
            Global.NO_REDUCER);

    public static Card foodFactory = new Card("Food Factory", 12, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Plants, 1),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 4)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card archaebacteria = new Card("Archaebacteria", 6, CardType.Auto,
            new CardTag[]{CardTag.Microbes}, new TemperatureRequirement(-18, false),
            new IncreaseProductionEffect(ResourceType.Plants, 1),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card carbonateProcessing = new Card("Carbonate Processing", 6, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.Heat, 3)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card nuclearPower = new Card("Nuclear Power", 10, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.MegaCredits, 2),
                    new IncreaseProductionEffect(ResourceType.Energy, 3)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card algae = new Card("Algae", 10, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new OceanRequirement(5, true),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Plants, 2),
                    new IncreaseAmountEffect(ResourceType.Plants, 1)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card adaptedLichen = new Card("Adapted Lichen", 9, CardType.Auto,
            new CardTag[]{CardTag.Plants}, Global.NO_REQUIREMENT,
            new IncreaseProductionEffect(ResourceType.Plants, 1),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card lakeMarineris = new Card("Lake Marineris", 18, CardType.Auto,
            new CardTag[0], new TemperatureRequirement(0, true),
            new CompoundEffect(new Effect[]{
                    new PlaceOceanTileEffect(),
                    new PlaceOceanTileEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(2),
            Global.NO_REDUCER);

    public static Card smallAnimals = new Card("Small Animals", 8, CardType.Active,
            new CardTag[]{CardTag.Plants, CardTag.UsesCounters}, new OxygenRequirement(6, true),
            new DecreaseOpponentProductionEffect(ResourceType.Plants, 1),
            new AddCountersToThisCardEffect("Small Animals", 1),
            Global.NULL_EFFECT,
            new TriggerType[0],
            new PointsPerCounterScorer("Small Animals", 1, 2),
            Global.NO_REDUCER);

    public static Card kelpFarming = new Card("Kelp Farming", 17, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new OceanRequirement(6, true),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 2),
                    new IncreaseProductionEffect(ResourceType.Plants, 3),
                    new IncreaseAmountEffect(ResourceType.Plants, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card miningExpedition = new Card("Mining Expedition", 12, CardType.Event,
            new CardTag[]{CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseOxygenEffect(),
                    new IncreaseAmountEffect(ResourceType.Titanium, 2),
                    new DecreaseOpponentAmountEffect(ResourceType.Plants, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card birds = new Card("Birds", 10, CardType.Active,
            new CardTag[]{CardTag.Plants, CardTag.UsesCounters}, new OxygenRequirement(13, true),
            new DecreaseOpponentProductionEffect(ResourceType.Plants, 2),
            new AddCountersToThisCardEffect("Birds", 1),
            Global.NULL_EFFECT,
            new TriggerType[0],
            new PointsPerCounterScorer("Birds", 1, 1),
            Global.NO_REDUCER);

    public static Card towingAComet = new Card("Towing A Comet", 23, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseOxygenEffect(),
                    new PlaceOceanTileEffect(),
                    new IncreaseAmountEffect(ResourceType.Plants, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card spaceMirrors = new Card("Space Mirrors", 3, CardType.Active,
            new CardTag[]{CardTag.Space, CardTag.Energy}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new PayForCostEffect(new CardTag[0], false, 7),
                    new IncreaseProductionEffect(ResourceType.Energy, 1)
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card solarWindPower = new Card("Solar Wind Power", 11, CardType.Auto,
            new CardTag[]{CardTag.Science, CardTag.Space, CardTag.Energy}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseAmountEffect(ResourceType.Titanium, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card iceAsteroid = new Card("Ice Asteroid", 23, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new PlaceOceanTileEffect(),
                    new PlaceOceanTileEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card giantIceAsteroid = new Card("Giant Ice Asteroid", 36, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseTemperatureEffect(),
                    new IncreaseTemperatureEffect(),
                    new PlaceOceanTileEffect(),
                    new PlaceOceanTileEffect(),
                    new DecreaseOpponentAmountEffect(ResourceType.Plants, 6)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card ganymedeColony = new Card("Ganymede Colony", 20, CardType.Auto,
            new CardTag[]{CardTag.Jovian, CardTag.City, CardTag.Space}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new PlaceOffBoardCityEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new OnePerAlliedJovianScorer(),
            Global.NO_REDUCER);

    public static Card giantSpaceMirror = new Card("Giant Space Mirror", 17, CardType.Auto,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new IncreaseProductionEffect(ResourceType.Energy, 3),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card grass = new Card("Grass", 11, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new TemperatureRequirement(-16, true),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Plants, 1),
                    new IncreaseAmountEffect(ResourceType.Plants, 3)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card heather = new Card("Heather", 6, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new TemperatureRequirement(-14, true),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Plants, 1),
                    new IncreaseAmountEffect(ResourceType.Plants, 1)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card peroxidePower = new Card("Peroxide Power", 7, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.MegaCredits, 1),
                    new IncreaseProductionEffect(ResourceType.Energy, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card bushes = new Card("Bushes", 10, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new TemperatureRequirement(-10, true),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Plants, 2),
                    new IncreaseAmountEffect(ResourceType.Plants, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card greenHouses = new Card("Greenhouses", 6, CardType.Auto,
            new CardTag[]{CardTag.Plants, CardTag.Steel}, Global.NO_REQUIREMENT,
            new IncreaseProductionByTileCountEffect(ResourceType.Plants, TileType.City),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card nuclearZone = new Card("Nuclear Zone", 6, CardType.Auto,
            new CardTag[]{CardTag.Earth}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new PlaceSpecialTileEffect(),
                    new IncreaseTemperatureEffect(),
                    new IncreaseTemperatureEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(-2),
            Global.NO_REDUCER);

    public static Card fueledGenerators = new Card("Fueled Generators", 1, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.MegaCredits, 1),
                    new IncreaseProductionEffect(ResourceType.Energy, 1)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card ironWorks = new Card("Ironworks", 11, CardType.Active,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Energy, 4),
                    new IncreaseAmountEffect(ResourceType.Steel, 1),
                    new IncreaseOxygenEffect()
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card steelWorks = new Card("Steelworks", 15, CardType.Active,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Energy, 4),
                    new IncreaseAmountEffect(ResourceType.Steel, 2),
                    new IncreaseOxygenEffect()
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card oreProcessor = new Card("Ore Processor", 13, CardType.Active,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Energy, 4),
                    new IncreaseAmountEffect(ResourceType.Titanium, 1),
                    new IncreaseOxygenEffect()
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card openCity = new Card("Open City", 23, CardType.Auto,
            new CardTag[]{CardTag.City, CardTag.Steel}, new OxygenRequirement(12, true),
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 4),
                    new PlaceCityTileEffect(),
                    new IncreaseAmountEffect(ResourceType.Plants, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card solarPower = new Card("Solar Power", 11, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, Global.NO_REQUIREMENT,
            new IncreaseProductionEffect(ResourceType.Energy, 1),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card breathingFilters = new Card("Breathing Filters", 11, CardType.Auto,
            new CardTag[]{CardTag.Science}, new OxygenRequirement(7, true),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(2),
            Global.NO_REDUCER);

    public static Card artificialPhotosynthesis = new Card("Artificial Photosynthesis", 12, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, Global.NO_REQUIREMENT,
            new OrEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Energy, 2),
                    new IncreaseProductionEffect(ResourceType.Plants, 1)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card geoThermalPower = new Card("Geothermal Power", 11, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, Global.NO_REQUIREMENT,
            new IncreaseProductionEffect(ResourceType.Energy, 1),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card farming = new Card("Farming", 16, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new TemperatureRequirement(4, true),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 2),
                    new IncreaseProductionEffect(ResourceType.Plants, 2),
                    new IncreaseAmountEffect(ResourceType.Plants, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(2),
            Global.NO_REDUCER);

    public static Card dustSeals = new Card("Dust Seals", 2, CardType.Auto,
            new CardTag[0], new OceanRequirement(3, false),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card moss = new Card("Moss", 4, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new OceanRequirement(3, true),
            new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Plants, 1),
                    new IncreaseProductionEffect(ResourceType.Plants, 1),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card ghgFactories = new Card("GHG Factories", 11, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.Heat, 4)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card worms = new Card("Worms", 8, CardType.Auto,
            new CardTag[]{CardTag.Microbes}, new OceanRequirement(4, true),
            new IncreaseProductionByTagCountEffect(ResourceType.Plants, CardTag.Microbes, 1, 2),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card decomposers = new Card("Decomposers", 5, CardType.Active,
            new CardTag[]{CardTag.Microbes, CardTag.UsesCounters}, new OxygenRequirement(3, true),
            new AddCountersToThisCardEffect("Decomposers", 1),
            Global.NULL_EFFECT,
            new AddCountersToThisCardPerTagEffect("Decomposers", new CardTag[]{CardTag.Plants, CardTag.Animals, CardTag.Microbes}),
            new TriggerType[]{TriggerType.PlantTagPlayed, TriggerType.AnimalTagPlayed, TriggerType.MicrobeTagPlayed},
            new PointsPerCounterScorer("Decomposers", 1, 3),
            Global.NO_REDUCER);

    public static Card fusionPower = new Card("Fusion Power", 14, CardType.Auto,
            new CardTag[]{CardTag.Science, CardTag.Energy, CardTag.Steel}, new TagCountRequirement(CardTag.Energy, 2),
            new IncreaseProductionEffect(ResourceType.Energy, 3),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card symbioticFungus = new Card("Symbiotic Fungus", 4, CardType.Active,
            new CardTag[]{CardTag.Microbes}, new TemperatureRequirement(-14, true),
            Global.NULL_EFFECT,
            new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Microbes}, 1),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card extremeColdFungus = new Card("Extreme-Cold Fungus", 13, CardType.Active,
            new CardTag[]{CardTag.Microbes}, new TemperatureRequirement(-10, false),
            Global.NULL_EFFECT,
            new OrEffect(new Effect[]{
                    new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Microbes}, 2),
                    new IncreaseAmountEffect(ResourceType.Plants, 1)
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card advancedEcosystems = new Card("Advanced Ecosystem", 11, CardType.Auto,
            new CardTag[]{CardTag.Microbes}, new CompoundRequirement(new Requirement[]{
                    new TagCountRequirement(CardTag.Microbes, 1),
                    new TagCountRequirement(CardTag.Plants, 1),
                    new TagCountRequirement(CardTag.Animals, 1),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(3),
            Global.NO_REDUCER);

    public static Card greatDam = new Card("Great Dam", 12, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, new OceanRequirement(4, true),
            new IncreaseProductionEffect(ResourceType.Energy, 2),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card stripMine = new Card("Strip Mine", 25, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 2),
                    new IncreaseProductionEffect(ResourceType.Steel, 2),
                    new IncreaseProductionEffect(ResourceType.Titanium, 1),
                    new IncreaseOxygenEffect(),
                    new IncreaseOxygenEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card wavePower = new Card("Wave Power", 8, CardType.Auto,
            new CardTag[]{CardTag.Energy}, new OceanRequirement(3, true),
            new IncreaseProductionEffect(ResourceType.Energy, 1),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card powerPlant = new Card("Power Plant", 4, CardType.Auto,
            new CardTag[]{CardTag.Energy}, Global.NO_REQUIREMENT,
            new IncreaseProductionEffect(ResourceType.Energy, 1),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card largeConvoy = new Card("Large Convoy", 36, CardType.Event,
            new CardTag[]{CardTag.Earth, CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new PlaceOceanTileEffect(),
                    new DrawCardEffect(),
                    new DrawCardEffect(),
                    new OrEffect(new Effect[]{
                            new IncreaseAmountEffect(ResourceType.Plants, 5),
                            new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Animals}, 4)
                    })
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(2),
            Global.NO_REDUCER);

    public static Card tectonicStressPower = new Card("Tectonic Stress Power", 18, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, new TagCountRequirement(CardTag.Energy, 2),
            new IncreaseProductionEffect(ResourceType.Energy, 3),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card nitrophilicMoss = new Card("Nitrophilic Moss", 8, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new OceanRequirement(3, true),
            new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Plants, 2),
                    new IncreaseProductionEffect(ResourceType.Plants, 2),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card insects = new Card("Insects", 9, CardType.Auto,
            new CardTag[]{CardTag.Microbes}, new OxygenRequirement(6, true),
            new IncreaseProductionByTagCountEffect(ResourceType.Plants, CardTag.Plants),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card designedMicroorganisms = new Card("Designed Microorganisms", 16, CardType.Auto,
            new CardTag[]{CardTag.Science, CardTag.Microbes}, new TemperatureRequirement(-14, false),
            new IncreaseProductionEffect(ResourceType.Plants, 2),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card nitrateReducingBacteria = new Card("Nitrate Reducing Bacteria", 11, CardType.Active,
            new CardTag[]{CardTag.Microbes, CardTag.UsesCounters}, new OxygenRequirement(4, true),
            Global.NULL_EFFECT,
            new OrEffect(new Effect[]{
                    new AddCountersToThisCardEffect("Nitrate Reducing Bacteria", 1),
                    new CompoundEffect(new Effect[]{
                            new RemoveCountersFromThisCardEffect("Nitrate Reducing Bacteria", 3),
                            new IncreaseTerraformingScoreEffect(1)
                    })
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card industrialMicrobes = new Card("Industrial Microbes", 11, CardType.Auto,
            new CardTag[]{CardTag.Microbes, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.Steel, 1),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card lichen = new Card("Lichen", 7, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new TemperatureRequirement(-24, true),
            new IncreaseProductionEffect(ResourceType.Plants, 1),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card convoyFromEuropa = new Card("Convoy From Europa", 15, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new PlaceOceanTileEffect(),
                    new DrawCardEffect(),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card importedGHG = new Card("Imported GHG", 7, CardType.Event,
            new CardTag[]{CardTag.Earth, CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Heat, 1),
                    new IncreaseAmountEffect(ResourceType.Heat, 3)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);


    public static Card importedNitrogen = new Card("Imported Nitrogen", 16, CardType.Event,
            new CardTag[]{CardTag.City, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseTerraformingScoreEffect(1),
                    new IncreaseAmountEffect(ResourceType.Plants, 4),
                    new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Microbes}, 3),
                    new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Animals}, 2),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card microMills = new Card("Micro-Mills", 3, CardType.Auto,
            new CardTag[0], Global.NO_REQUIREMENT,
            new IncreaseProductionEffect(ResourceType.Heat, 1),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card magneticFieldGenerators = new Card("Magnetic Field Generators", 20, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 4),
                    new IncreaseProductionEffect(ResourceType.Plants, 2),
                    new IncreaseTerraformingScoreEffect(3),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card importOfAdvancedGHG = new Card("Import of Advanced GHG", 9, CardType.Event,
            new CardTag[]{CardTag.Earth, CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new IncreaseProductionEffect(ResourceType.Heat, 2),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card windmills = new Card("Windmills", 6, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, new OxygenRequirement(7, true),
            new IncreaseProductionEffect(ResourceType.Energy, 1),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card tundraFarming = new Card("Tundra Farming", 16, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new TemperatureRequirement(-6, true),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 2),
                    new IncreaseProductionEffect(ResourceType.Plants, 1),
                    new IncreaseAmountEffect(ResourceType.Plants, 1)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(2),
            Global.NO_REDUCER);

    public static Card aerobrakedAmmoniaAsteroid = new Card("AerobrakedAmmoniaAsteroid", 26, CardType.Event,
            new CardTag[]{CardTag.Space, CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.Heat, 3),
                    new IncreaseProductionEffect(ResourceType.Plants, 1),
                    new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Microbes}, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card magneticFieldDome = new Card("Magnetic Field Generators", 5, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 2),
                    new IncreaseProductionEffect(ResourceType.Plants, 1),
                    new IncreaseTerraformingScoreEffect(1),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card pets = new Card("Pets", 10, CardType.Active,
            new CardTag[]{CardTag.Earth, CardTag.Animals, CardTag.UsesCounters, CardTag.CantRemoveCounters}, Global.NO_REQUIREMENT,
            new AddCountersToThisCardEffect("Pets", 1),
            Global.NULL_EFFECT,
            new AddCountersToThisCardEffect("Pets", 1),
            new TriggerType[]{TriggerType.CityPlaced},
            new PointsPerCounterScorer("Pets", 1, 2),
            Global.NO_REDUCER);

    public static Card noctisFarming = new Card("Noctis Farming", 10, CardType.Auto,
            new CardTag[]{CardTag.Steel, CardTag.Plants}, new TemperatureRequirement(-20, true),
            new CompoundEffect(new Effect[]{
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 4),
                    new IncreaseAmountEffect(ResourceType.Plants, 2),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card waterSplittingPlant = new Card("Water Splitting Plant", 12, CardType.Active,
            new CardTag[]{CardTag.Steel}, new OceanRequirement(2, true),
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Energy, 3),
                    new IncreaseOxygenEffect()
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card heatTrappers = new Card("Heat Trappers", 6, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseOpponentProductionEffect(ResourceType.Heat, 2),
                    new IncreaseProductionEffect(ResourceType.Energy, 1)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(-1),
            Global.NO_REDUCER);

    public static Card soilFactory = new Card("Soil Factory", 9, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseProductionEffect(ResourceType.Plants, 1)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(1),
            Global.NO_REDUCER);

    public static Card iceCapMelting = new Card("Ice Cap Melting", 5, CardType.Event,
            new CardTag[]{CardTag.Event}, new TemperatureRequirement(2, true),
            new PlaceOceanTileEffect(),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card biomassConverters = new Card("Biomass Converters", 4, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, new OxygenRequirement(6, true),
            new CompoundEffect(new Effect[]{
                    new DecreaseOpponentProductionEffect(ResourceType.Plants, 1),
                    new IncreaseProductionEffect(ResourceType.Energy, 2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            new GenericScorer(-1),
            Global.NO_REDUCER);

    public static Card livestock = new Card("Livestock", 13, CardType.Active,
            new CardTag[]{CardTag.Animals, CardTag.UsesCounters}, new OxygenRequirement(9, true),
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Plants, 1),
                    new IncreaseProductionEffect(ResourceType.MegaCredits, 2),
            }),
            new AddCountersToThisCardEffect("Livestock", 1),
            Global.NULL_EFFECT,
            new TriggerType[0],
            new PointsPerCounterScorer("Livestock", 1, 1),
            Global.NO_REDUCER);

    public static Card aquiferPumping = new Card("Aquifer Pumping", 18, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new PayForCostEffect(new CardTag[]{CardTag.Steel}, false, 8),
                    new PlaceOceanTileEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card energySaving = new Card("Energy Saving", 15, CardType.Auto,
            new CardTag[]{CardTag.Energy}, Global.NO_REQUIREMENT,
            new IncreaseProductionByTileCountEffect(ResourceType.Energy, TileType.City),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card localHeatTrapping = new Card("Local Heat Trapping", 1, CardType.Event,
            new CardTag[]{CardTag.Event}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Heat, 5),
                    new OrEffect(new Effect[]{
                            new IncreaseAmountEffect(ResourceType.Plants, 2),
                            new AddCountersToOtherCardEffect(new CardTag[]{CardTag.Animals}, 2)
                    }),
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card permafrostExtraction = new Card("Permafrost Extraction", 8, CardType.Event,
            new CardTag[]{CardTag.Event}, new TemperatureRequirement(-8, true),
            new PlaceOceanTileEffect(),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card plantation = new Card("Plantation", 15, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new TagCountRequirement(CardTag.Science, 2),
            new CompoundEffect(new Effect[]{
                    new PlacePlantsTileEffect(),
                    new IncreaseOxygenEffect()
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card immigrationShuttles = new Card("Immigrant Shuttles", 31, CardType.Auto,
            new CardTag[]{CardTag.Earth, CardTag.Space}, new TagCountRequirement(CardTag.Science, 2),
            new IncreaseProductionEffect(ResourceType.MegaCredits, 5),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[]{TriggerType.CityPlaced},
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card immigrantCity = new Card("Immigrant City", 13, CardType.Active,
            new CardTag[]{CardTag.City, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new DecreaseProductionEffect(ResourceType.MegaCredits, 2),
                    new PlaceCityTileEffect()
            }),
            Global.NULL_EFFECT,
            new IncreaseProductionEffect(ResourceType.MegaCredits, 1),
            new TriggerType[]{TriggerType.CityPlaced},
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card undergroundDetonations = new Card("Underground Detonations", 6, CardType.Active,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            Global.NULL_EFFECT,
            new CompoundEffect(new Effect[]{
                    new PayForCostEffect(new CardTag[0], false, 10),
                    new IncreaseProductionEffect(ResourceType.Heat, 2)
            }),
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card soletta = new Card("Soletta", 35, CardType.Auto,
            new CardTag[]{CardTag.Space}, Global.NO_REQUIREMENT,
            new IncreaseProductionEffect(ResourceType.Heat, 7),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card radChemFactory = new Card("Rad-Chem Factory", 8, CardType.Auto,
            new CardTag[]{CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseTerraformingScoreEffect(2)
            }),
            Global.NULL_EFFECT,
            Global.NULL_EFFECT,
            new TriggerType[0],
            Global.ZERO_SCORER,
            Global.NO_REDUCER);

    public static Card[] cards = new Card[]{
            trees,
            deepWellHeating,
            subterraneanReservoir,
            domedCrater,
            martianRails,
            asteroid,
            searchForLife,
            capital,
            waterImportFromEuropa,
            equatorialMagnetizer,
            noctisCity,
            importedHydrogen,
            researchOutpost,
            roverConstruction,
            adaptationTechnology,
            moholeArea,
            optimalAerobraking,
            predators,
            arcticAlgae,
            regolithEaters,
            nitrogenRichAsteroid,
            naturalPreserve,
            fish,
            beamFromAThoriumAsteroid,
            mangrove,
            miningRights,
            powerGrid,
            urbanizedArea,
            ecologicalZone,
            zeppelins,
            herbivores,
            insulation,
            protectedValley,
    };
}

