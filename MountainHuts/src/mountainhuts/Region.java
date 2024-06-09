package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

	private String regionName;
	private List<String> altitudeRangesList;
	private Map<String, Municipality> municipalityMap;
	private Map<String, MountainHut> mountainHutMap;

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		
		this.regionName = name;
		this.altitudeRangesList = new ArrayList<>();
		this.municipalityMap = new HashMap<>();
		this.mountainHutMap = new HashMap<>();

	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.regionName;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for(String alt: ranges) this.altitudeRangesList.add(alt);
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

		for (String range : this.altitudeRangesList) {
			String[] bounds = range.split("-");
			Integer minValue = Integer.parseInt(bounds[0]);
			Integer maxValue = Integer.parseInt(bounds[1]);

			if (altitude >= minValue && altitude <= maxValue) {
				return range;
			}
		}
		return "0-INF";
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return Collections.unmodifiableCollection(this.municipalityMap.values());
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return Collections.unmodifiableCollection(this.mountainHutMap.values());
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		
		return this.municipalityMap.computeIfAbsent(name,k -> new Municipality(name, province, altitude));
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber, Municipality municipality) {

		return this.mountainHutMap.computeIfAbsent(name, k -> new MountainHut(name, category, bedsNumber, municipality));
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
		return this.mountainHutMap.computeIfAbsent(name, k -> new MountainHut(name, altitude, category, bedsNumber, municipality));
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

		if (lines.size() <= 1) {
			return region; // No data to process
		}

		// Skip header line and process data lines
		lines.stream().skip(1).forEach(line -> {
			String[] fields = line.split(";");
			String province = fields[0].trim();
			String municipalityName = fields[1].trim();
			Integer municipalityAltitude = Integer.parseInt(fields[2].trim());
			String hutName = fields[3].trim();
			String altitudeStr = fields[4].trim();
			Integer hutAltitude = altitudeStr.isEmpty() ? null : Integer.parseInt(altitudeStr);
			String category = fields[5].trim();
			Integer bedsNumber = Integer.parseInt(fields[6].trim());

			Municipality municipality = region.createOrGetMunicipality(municipalityName, province, municipalityAltitude);

			if (hutAltitude == null) {
				region.createOrGetMountainHut(hutName, category, bedsNumber, municipality);
			} else {
				region.createOrGetMountainHut(hutName, hutAltitude, category, bedsNumber, municipality);
			}
		});

		return region;
	}
	

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

		Map<String, Long> result = new HashMap<>();
		for (Municipality municipality : this.municipalityMap.values()) {
			String province = municipality.getProvince();
			result.put(province, result.getOrDefault(province, Long.valueOf(0)) + 1);
			
		}
		return result;
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {

		Map<String, Map<String, Long>> result = new HashMap<>();
		for (MountainHut hut : this.mountainHutMap.values()) {
			String province = hut.getMunicipality().getProvince();
			String municipalityName = hut.getMunicipality().getName();
			
			result.putIfAbsent(province, new HashMap<>());
			Map<String, Long> municipalityMap = result.get(province);
	
			if (municipalityMap.containsKey(municipalityName)) {
				municipalityMap.put(municipalityName, municipalityMap.get(municipalityName) + 1);
			} else {
				municipalityMap.put(municipalityName, 1L);
			}
		}
	
		return result;
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {

		Map<String, Long> result = new HashMap<>();
		for (MountainHut hut : this.mountainHutMap.values()) {
			int altitude = hut.getAltitude().orElse(hut.getMunicipality().getAltitude());
			String range = getAltitudeRange(altitude);
	
			if (result.containsKey(range)) {
				result.put(range, result.get(range) + 1);
			} else {
				result.put(range, 1L);
			}
		}
	
		return result;
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		Map<String, Integer> result = new HashMap<>();

		for (MountainHut hut : this.mountainHutMap.values()) {
			String province = hut.getMunicipality().getProvince();
			int beds = hut.getBedsNumber();
	
			if (result.containsKey(province)) {
				result.put(province, result.get(province) + beds);
			} else {
				result.put(province, beds);
			}
		}
	
		return result;
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
		Map<String, Optional<Integer>> result = new HashMap<>();

		for (MountainHut hut : this.mountainHutMap.values()) {
			int altitude = hut.getAltitude().orElse(hut.getMunicipality().getAltitude());
			String range = getAltitudeRange(altitude);
			int beds = hut.getBedsNumber();
	
			result.putIfAbsent(range, Optional.of(beds));
			result.compute(range, (k, v) -> Optional.of(Math.max(v.orElse(0), beds)));
		}
	
		return result;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		Map<String, Long> hutsCountPerMunicipality = new HashMap<>();

		for (MountainHut hut : this.mountainHutMap.values()) {
			String municipalityName = hut.getMunicipality().getName();
			
			if (hutsCountPerMunicipality.containsKey(municipalityName)) {
				hutsCountPerMunicipality.put(municipalityName, hutsCountPerMunicipality.get(municipalityName) + 1);
			} else {
				hutsCountPerMunicipality.put(municipalityName, 1L);
			}
		}
	
		Map<Long, List<String>> result = new HashMap<>();
	
		for (Map.Entry<String, Long> entry : hutsCountPerMunicipality.entrySet()) {
			String municipalityName = entry.getKey();
			Long count = entry.getValue();
	
			result.putIfAbsent(count, new ArrayList<>());
			result.get(count).add(municipalityName);
		}
	
		for (List<String> municipalityNames : result.values()) {
			Collections.sort(municipalityNames);
		}
	
		return result;
	}

}
