package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {
		super(name);
	}


	@Override
	public void simulate(double inputFlow, SimulationObserver observer) {
		double outFlow = inputFlow / 2;
		observer.notifyFlow("Split", getName(), inputFlow, outFlow, outFlow);
		Element[] outs = getOutputs();
		if (outs.length > 0 && outs[0] != null) outs[0].simulate(outFlow, observer);
		if (outs.length > 1 && outs[1] != null) outs[1].simulate(outFlow, observer);
	}
	

	
}
