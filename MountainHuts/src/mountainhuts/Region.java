package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


// import java.util.stream.Collectors;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	
	private String name;
	private List<String> altitudeRanges;
	private Map<String, Municipality> municipalities;
	private Map<String, MountainHut> mountainHuts;



	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
		this.altitudeRanges = new ArrayList<>();
		this.municipalities = new HashMap<>();
		this.mountainHuts = new HashMap<>();

	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for(String range : ranges){
			this.altitudeRanges.add(range);
		}
		
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		
		for(String range : this.altitudeRanges){
			String[] min_max = range.split("-");
			Integer min_altitude = Integer.valueOf(min_max[0]);
			Integer max_altitude = Integer.valueOf(min_max[1]);

			if(altitude <= max_altitude && altitude >= min_altitude){
				return range;
			}
		}
		return "0-INF";
	}	
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude){
		Municipality existingMunicipality = municipalities.get(name);
		if (existingMunicipality != null) {
			return existingMunicipality;
		} else {
			Municipality Municipality = new Municipality(name, province, altitude);
			this.municipalities.put(name, Municipality);
			return Municipality;
		}
	}
	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
        return Collections.unmodifiableCollection(municipalities.values());
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
        return Collections.unmodifiableCollection(mountainHuts.values());
	}


	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,Municipality municipality) {
		MountainHut existingHut = mountainHuts.get(name);
		if (existingHut != null) {
			return existingHut;
		} else {

			Municipality intendedMunicipality = municipalities.get(municipality.getName());
			MountainHut newHut = new MountainHut(name, altitude, category, bedsNumber, intendedMunicipality);
			this.mountainHuts.put(name, newHut);
			return newHut;
		}
	}
	

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region region = new Region(name);
		List<String> lines = readData(file);
	
		// Ensure the header is correct and skip the first line
		lines.stream().skip(1)
			.map(line -> line.split(";"))
			.forEach(parts -> {
				

				String province = parts[0];
				String municipalityName = parts[1];
				Integer municipalityAltitude = Integer.valueOf(parts[2]);
				String hutName = parts[3];
				Integer hutAltitude = parts[4].isEmpty() ? 0 : Integer.valueOf(parts[4]);
				String category = parts[5];
				Integer bedsNumber = Integer.valueOf(parts[6]);
	
				Municipality municipality = region.createOrGetMunicipality(municipalityName, province, municipalityAltitude);

				region.createOrGetMountainHut(hutName, hutAltitude, category, bedsNumber, municipality);
				
			});
	
		return region;
	}
//Province;Municipality;MunicipalityAltitude;Name;Altitude;Category;BedsNumber
	/**
	 * Reads the lines of a text file.
	 *
	 * @param file path of the file
	 * @return a list with one element per line
	 */
	public static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {


		Map<String,Long> municipalityCounts = new HashMap<>();

		municipalityCounts = getMunicipalities().stream()
												.collect(Collectors.groupingBy(Municipality::getProvince, Collectors.counting()));

		return municipalityCounts;
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {

		return getMountainHuts().stream()
								.collect(Collectors.groupingBy(
																hut -> hut.getMunicipality().getProvince(),
																Collectors.groupingBy(
																		hut -> hut.getMunicipality().getName(),
																		Collectors.counting()
																)
															));
	}


	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		return getMountainHuts().stream()
								.collect(Collectors.groupingBy(
																hut -> getAltitudeRange(hut.getAltitude()
																						   .orElse(
																								hut.getMunicipality()
																									.getAltitude()
																									)),
																Collectors.counting()
															));
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return getMountainHuts().stream()
				.collect(Collectors.groupingBy(
						hut -> hut.getMunicipality().getProvince(),
						Collectors.summingInt(MountainHut::getBedsNumber)
				));
	}
	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
    public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
        return getMountainHuts().stream()
                .collect(Collectors.groupingBy(
                        hut -> getAltitudeRange(hut.getAltitude().orElse(hut.getMunicipality().getAltitude())),
                        Collectors.mapping(
                                MountainHut::getBedsNumber,
                                Collectors.maxBy(Comparator.naturalOrder())
                        )
                ));
    }

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		return getMountainHuts().stream()
				.collect(Collectors.groupingBy(
						hut -> hut.getMunicipality().getName(),
						Collectors.counting()
				))
				.entrySet().stream()
				.collect(Collectors.groupingBy(
						entry -> entry.getValue(),
						Collectors.mapping(
								Map.Entry::getKey,
								Collectors.collectingAndThen(
										Collectors.toList(),
										list -> {
											list.sort(String::compareTo);
											return list;
										}
								)
						)
				));
	}

}


