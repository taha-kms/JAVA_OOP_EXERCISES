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
	public void addElement(Element elem){
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
	 * 							the number of added elements
	 */
	public Element[] getElements(){
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
	public void simulate(SimulationObserver observer){
		for (Element e : elementsMap) {
			if (e instanceof Source) {
				simulateFrom(e, ((Source)e).getFlow(), observer);
			}
		}
	}

	private void simulateFrom(Element elem, double inputFlow, SimulationObserver observer) {
		if (elem instanceof Source) {
			double flow = ((Source)elem).getFlow();
			observer.notifyFlow("Source", elem.getName(), SimulationObserver.NO_FLOW, flow);
			Element out = elem.getOutput();
			if (out != null) simulateFrom(out, flow, observer);
		} else if (elem instanceof Tap) {
			Tap tap = (Tap)elem;
			double output = tap.isOpen() ? inputFlow : 0;
			observer.notifyFlow("Tap", tap.getName(), inputFlow, output);
			Element out = tap.getOutput();
			if (out != null) simulateFrom(out, output, observer);
		} else if (elem instanceof Split) {
			double outFlow = inputFlow / 2;
			observer.notifyFlow("Split", elem.getName(), inputFlow, outFlow, outFlow);
			Element[] outs = elem.getOutputs();
			for (int i = 0; i < 2; i++) {
				if (outs[i] != null) simulateFrom(outs[i], outFlow, observer);
			}
		} else if (elem instanceof Multisplit) {
			Multisplit ms = (Multisplit) elem;
			double[] props = ms.getProportions();
			double[] outFlows = new double[props.length];
			for (int i = 0; i < props.length; i++) {
				outFlows[i] = inputFlow * props[i];
			}
			observer.notifyFlow("Multisplit", elem.getName(), inputFlow, outFlows);
			Element[] outs = elem.getOutputs();
			for (int i = 0; i < outs.length; i++) {
				if (outs[i] != null) simulateFrom(outs[i], outFlows[i], observer);
			}
		} else if (elem instanceof Sink) {
			observer.notifyFlow("Sink", elem.getName(), inputFlow, SimulationObserver.NO_FLOW);
		}
	}
	


// R6
	/**
	 * Deletes a previously added element 
	 * with the given name from the system
	 */
	public boolean deleteElement(String name) {
		//TODO: to be implemented
		return false;
	}

// R7
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 * 
	 * If {@code enableMaxFlowCheck} is {@code false}  a normals simulation as
	 * the method {@link #simulate(SimulationObserver)} is performed
	 * 
	 * Before performing a checked simulation the max flows of the elements in thes
	 * system must be defined.
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		//TODO: to be implemented
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
