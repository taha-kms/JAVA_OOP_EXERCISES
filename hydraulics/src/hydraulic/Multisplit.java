package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many
 * outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	private double[] proportions; // proportions of the output flows w.r.t. the input flow

	/**
	 * Constructor
	 * 
	 * @param name      the name of the multi-split element
	 * @param numOutput the number of outputs
	 */
	public Multisplit(String name, int numOutput) {
		super(name);

	}

	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		this.proportions = proportions;
	}

	public double[] getProportions() {
		return this.proportions;
	}

	@Override
	public void simulate(double inputFlow, SimulationObserver observer, boolean checkMax) {
		if (checkMax) {
			checkFlow("Multisplit", observer, inputFlow);
		}

		// Compute output flows based on proportions
		double[] outFlows = new double[proportions.length];
		for (int i = 0; i < proportions.length; i++) {
			outFlows[i] = inputFlow * proportions[i];
		}

		// Notify observer about this element's flow
		observer.notifyFlow("Multisplit", getName(), inputFlow, outFlows);

		// Simulate downstream elements
		Element[] outs = getOutputs();
		for (int i = 0; i < proportions.length; i++) {
			if (i < outs.length && outs[i] != null) {
				outs[i].simulate(outFlows[i], observer, checkMax);
			}
		}
	}

}
