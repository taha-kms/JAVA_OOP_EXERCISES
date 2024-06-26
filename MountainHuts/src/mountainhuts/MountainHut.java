package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut
 * 
 * It includes a name, optional altitude, category,
 * number of beds and location municipality.
 *  
 *
 */
public class MountainHut {

	private String name;
	private String category;
	private Optional<Integer> altitude;
	private Integer bedsNumber;
	private Municipality municipality;


	public MountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {

		this.name = name;
		this.category = category;
		this.altitude = Optional.ofNullable(altitude);
		this.bedsNumber = bedsNumber;
		this.municipality = municipality;

	}
	public MountainHut(String name, String category, Integer bedsNumber, Municipality municipality) {

		this.name = name;
		this.category = category;
		this.altitude = Optional.empty();
		this.bedsNumber = bedsNumber;
		this.municipality = municipality;

	}

	public String getName() {
		return this.name;
	}

	public Optional<Integer> getAltitude() {
		return this.altitude;
	}

	public String getCategory() {
		return this.category;
	}

	public Integer getBedsNumber() {
		return this.bedsNumber;
	}

	public Municipality getMunicipality() {
		return this.municipality;
	}
}
