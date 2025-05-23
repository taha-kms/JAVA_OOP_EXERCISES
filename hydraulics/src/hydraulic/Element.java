package hydraulic;

import java.util.ArrayList;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 * 
 * The class is abstract since it is not intended to be instantiated,
 * though all methods are defined to make subclass implementation easier.
 */
public abstract class Element {

	private String name;
	protected ArrayList<Element> outputs;
	private double maxFlow = Double.POSITIVE_INFINITY;

	public Element(String name) {
		this.name = name;
		this.outputs = new ArrayList<>();
	}

	/**
	 * getter method for the name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * In case of element with multiple outputs this method operates on the first
	 * one,
	 * it is equivalent to calling {@code connect(elem,0)}.
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem) {
		connect(elem, 0);
	}

	/**
	 * Connects a specific output of this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * @param elem  the element that will be placed downstream
	 * @param index the output index that will be used for the connection
	 */
	public void connect(Element elem, int index) {

		while (outputs.size() <= index) {
			outputs.add(null); // pad with nulls
		}
		outputs.set(index, elem);
	}

	/**
	 * Retrieves the single element connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element getOutput() {
		return outputs.isEmpty() ? null : outputs.get(0);
	}

	/**
	 * Retrieves the elements connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element[] getOutputs() {
		return outputs.toArray(new Element[outputs.size()]);
	}

	/**
	 * Defines the maximum input flow acceptable for this element
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		this.maxFlow = maxFlow;
	}

	public double getMaxFlow() {
		return maxFlow;
	}

	protected static String pad(String current, String down) {
		int n = current.length();
		final String fmt = "\n%" + n + "s";
		return current + down.replace("\n", fmt.formatted(""));
	}

	@Override
	public String toString() {
		String res = "[%s] ".formatted(getName());
		Element[] out = getOutputs();
		if (out != null) {
			StringBuilder buffer = new StringBuilder();
			for (int i = 0; i < out.length; ++i) {
				if (i > 0)
					buffer.append("\n");
				if (out[i] == null)
					buffer.append("+-> *");
				else
					buffer.append(pad("+-> ", out[i].toString()));
			}
			res = pad(res, buffer.toString());
		}
		return res;
	}

	public abstract void simulate(double inputFlow, SimulationObserver observer, boolean checkMax);

	
	public void simulate(double inputFlow, SimulationObserver observer) {
		simulate(inputFlow, observer, false); // default: no max flow check
	}
	
	protected void checkFlow(String type, SimulationObserver observer, double inputFlow) {
		if (inputFlow > maxFlow) {
			observer.notifyFlowError(type, getName(), inputFlow, maxFlow);
		}
	}
}
