package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {

	private double flow = 0.0; // flow of the source (in cubic meters per hour)

	/**
	 * constructor
	 * 
	 * @param name name of the source element
	 */
	public Source(String name) {
		super(name);
	}

	/**
	 * Define the flow of the source to be used during the simulation
	 *
	 * @param flow flow of the source (in cubic meters per hour)
	 */
	public void setFlow(double flow) {
		this.flow = flow;
	}

	/**
	 * Returns the flow of the source
	 * 
	 * @return the flow of the source (in cubic meters per hour)
	 */
	public double getFlow() {
		return this.flow;
	}

	@Override
	public void simulate(double inputFlow, SimulationObserver observer,boolean checkMax) {
		if(checkMax) checkFlow("Source", observer, inputFlow);
		double flow = getFlow();
		observer.notifyFlow("Source", getName(), SimulationObserver.NO_FLOW, flow);
		if (getOutput() != null)
			getOutput().simulate(flow, observer);
	}

	@Override
	public void setMaxFlow(double maxFlow) {
		// Do nothing: source doesn't accept input
	}


}
