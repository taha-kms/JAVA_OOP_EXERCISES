package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * 
	 * @param name name of the sink element
	 */
	public Sink(String name) {
		super(name);
	}

	@Override
	public void connect(Element elem) {
		// Do nothing: sinks cannot have outputs
	}

	@Override
	public void connect(Element elem, int index) {
		// Do nothing: sinks cannot have outputs
	}

	@Override
	public void simulate(double inputFlow, SimulationObserver observer, boolean checkMax) {
		if (checkMax) checkFlow("Sink", observer, inputFlow);
		observer.notifyFlow("Sink", getName(), inputFlow, SimulationObserver.NO_FLOW);
	}

}
