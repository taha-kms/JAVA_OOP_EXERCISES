package hydraulic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system
 * 
 */
public class HSystem {

	private static final int MAX_ELEMENTS = 100;
	private ArrayList<Element> elementsMap;

	public HSystem() {
		this.elementsMap = new ArrayList<>();
	}

	// R1
	/**
	 * Adds a new element to the system
	 * 
	 * @param elem the new element to be added to the system
	 */
	public void addElement(Element elem) {
		if (this.elementsMap.size() < MAX_ELEMENTS) {
			this.elementsMap.add(elem);
		}
	}

	/**
	 * returns the number of element currently present in the system
	 * 
	 * @return count of elements
	 */
	public int size() {
		return this.elementsMap.size();
	}

	/**
	 * returns the element added so far to the system
	 * 
	 * @return an array of elements whose length is equal to
	 *         the number of added elements
	 */
	public Element[] getElements() {
		return this.elementsMap.toArray(new Element[this.elementsMap.size()]);
	}

	// R4
	/**
	 * starts the simulation of the system
	 * 
	 * The notification about the simulations are sent
	 * to an observer object
	 * 
	 * Before starting simulation the parameters of the
	 * elements of the system must be defined
	 * 
	 * @param observer the observer receiving notifications
	 */
	public void simulate(SimulationObserver observer) {
		for (Element e : elementsMap) {
			if (e instanceof Source) {
				e.simulate(((Source) e).getFlow(), observer);
			}
		}
	}

	// R6
	/**
	 * Deletes a previously added element
	 * with the given name from the system
	 */
	public boolean deleteElement(String name) {

		Element toDelete = null;

		for (Element e : elementsMap) {
			if (e.getName().equals(name)) {
				toDelete = e;
				break;
			}
		}

		if (toDelete == null)
			return false;

		if (toDelete instanceof Split || toDelete instanceof Multisplit) {
			int connectedCount = 0;
			for (Element out : toDelete.getOutputs()) {
				if (out != null)
					connectedCount++;
			}
			if (connectedCount > 1)
				return false;
		}

		for (Element upstream : elementsMap) {
			Element[] outputs = upstream.getOutputs();
			for (int i = 0; i < outputs.length; i++) {
				if (outputs[i] == toDelete) {
					// Step 4: Connect upstream to toDelete's only downstream
					Element[] toDeleteOuts = toDelete.getOutputs();
					Element downstream = (toDeleteOuts.length > 0) ? toDeleteOuts[0] : null;
					upstream.connect(downstream, i);
				}
			}
		}

		elementsMap.remove(toDelete);
		return true;
	}

	// R7
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is
	 * {@code true},
	 * checks also the elements maximum flows against the input flow
	 * 
	 * If {@code enableMaxFlowCheck} is {@code false} a normals simulation as
	 * the method {@link #simulate(SimulationObserver)} is performed
	 * 
	 * Before performing a checked simulation the max flows of the elements in thes
	 * system must be defined.
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		// TODO: to be implemented
	}

	// R8
	/**
	 * creates a new builder that can be used to create a
	 * hydraulic system through a fluent API
	 * 
	 * @return the builder object
	 */
	public static HBuilder build() {
		return new HBuilder();
	}
}
