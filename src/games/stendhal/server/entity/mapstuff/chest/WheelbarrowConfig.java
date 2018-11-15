package games.stendhal.server.entity.mapstuff.chest;

import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;

public class WheelbarrowConfig implements ZoneConfigurator{
	//
	// ZoneConfigurator
	//

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		placeWheelbarrows();
	}
	
	/**
	 * Place the carts and targets into the zone
	 */
	private void placeWheelbarrows() {
		StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_fado_city");

		WheelBarrow wheelbarrow1 = new WheelBarrow();
		wheelbarrow1.setPosition(87, 100);

		zone.add(wheelbarrow1);
	}
}
