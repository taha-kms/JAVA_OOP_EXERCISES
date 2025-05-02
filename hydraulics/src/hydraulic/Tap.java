package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {

	private boolean open = true; // status of the tap (open or closed)

	/**
	 * Constructor
	 * 
	 * @param name name of the tap element
	 */
	public Tap(String name) {
		super(name);
	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * Returns the status of the tap
	 * 
	 * @return true if the tap is open, false otherwise
	 */
	public boolean isOpen() {
		return this.open;
	}

	@Override
	public void simulate(double inputFlow, SimulationObserver observer) {
		double output = isOpen() ? inputFlow : 0;
		observer.notifyFlow("Tap", getName(), inputFlow, output);
		if (getOutput() != null)
			getOutput().simulate(output, observer);
	}

}
