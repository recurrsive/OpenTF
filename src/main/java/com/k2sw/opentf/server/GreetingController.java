package com.k2sw.opentf.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.k2sw.opentf.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public VisibleTile[][] greeting(@RequestParam(value="name", defaultValue="World") String name) {
		PlayerID id = new PlayerID(0);
		PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 25);
		player.getHand().add(StandardCards.giantIceAsteroid);
		StandardBoard board = new StandardBoard();
		GameStateBuilder builder = new GameStateBuilder()
				.withPlayers(new PlayerBuilder[]{player}).withTemperature(0).withOxygen(0);
		Map<TileSlot, Tile> placed = new HashMap<>();
		placed.put(builder.getStandardBoard().at(2, 2), new Tile(id, TileType.Plants));
		return builder.withPlacedTiles(placed).build().getVisible();
	}
}
