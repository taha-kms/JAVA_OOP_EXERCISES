package hydraulic;

/**
 * Hydraulics system builder providing a fluent API
 */
public class HBuilder {

    private HSystem system = new HSystem();
    private Element last;
    private Split currentSplitter;
    private Multisplit currentMultisplit;
    private int currentOutputIndex = 0;
    private Element[] lastPerOutput = new Element[0]; // track end of each output path


    /**
     * Add a source element with the given name
     * 
     * @param name name of the source element to be added
     * @return the builder itself for chaining
     */
    public HBuilder addSource(String name) {
        Source src = new Source(name);
        system.addElement(src);
        last = src;
        return this;
    }
    

    /**
     * returns the hydraulic system built with the previous operations
     * 
     * @return the hydraulic system
     */
    public HSystem complete() {
        return system;
    }

    /**
     * creates a new tap and links it to the previous element
     * 
     * @param name name of the tap
     * @return the builder itself for chaining
     */
    public HBuilder linkToTap(String name) {
        Tap tap = new Tap(name);
        system.addElement(tap);
        connectToCurrentOutput(tap);
        last = tap;
        return this;
    }

    /**
     * creates a sink and link it to the previous element
     * 
     * @param name name of the sink
     * @return the builder itself for chaining
     */
    public HBuilder linkToSink(String name) {
        Sink sink = new Sink(name);
        system.addElement(sink);
        connectToCurrentOutput(sink);
        last = sink;
        return this;
    }

    /**
     * creates a split and links it to the previous element
     * 
     * @param name of the split
     * @return the builder itself for chaining
     */
    public HBuilder linkToSplit(String name) {
        Split split = new Split(name);
        system.addElement(split);
        connectToCurrentOutput(split);
        last = split;
        currentSplitter = split;
        currentMultisplit = null;
        currentOutputIndex = 0;
        return this;
    }

    /**
     * creates a multisplit and links it to the previous element
     * 
     * @param name      name of the multisplit
     * @param numOutput the number of output of the multisplit
     * @return the builder itself for chaining
     */
    public HBuilder linkToMultisplit(String name, int numOutput) {
        Multisplit ms = new Multisplit(name, numOutput);
        system.addElement(ms);
        connectToCurrentOutput(ms);
        last = ms;
        currentMultisplit = ms;
        currentSplitter = null;
        currentOutputIndex = 0;
        return this;
    }

    /**
     * introduces the elements connected to the first output
     * of the latest split/multisplit.
     * The element connected to the following outputs are
     * introduced by {@link #then()}.
     * 
     * @return the builder itself for chaining
     */
    public HBuilder withOutputs() {
        if (currentSplitter != null) {
            lastPerOutput = new Element[2]; // Split has 2 outputs
        } else if (currentMultisplit != null) {
            lastPerOutput = new Element[currentMultisplit.getOutputs().length];
        }
        currentOutputIndex = 0;
        return this;
    }
    

    /**
     * inform the builder that the next element will be
     * linked to the successive output of the previous split or multisplit.
     * The element connected to the first output is
     * introduced by {@link #withOutputs()}.
     * 
     * @return the builder itself for chaining
     */
public HBuilder then() {
    currentOutputIndex++;
    if (currentSplitter != null) {
        last = currentSplitter;
    } else if (currentMultisplit != null) {
        last = currentMultisplit;
    }
    return this;
}


    /**
     * completes the definition of elements connected
     * to outputs of a split/multisplit.
     * 
     * @return the builder itself for chaining
     */
    public HBuilder done() {
        currentSplitter = null;
        currentMultisplit = null;
        currentOutputIndex = 0;
        return this;
    }
    

    /**
     * define the flow of the previous source
     * 
     * @param flow flow used in the simulation
     * @return the builder itself for chaining
     */
    public HBuilder withFlow(double flow) {
        if (last instanceof Source) {
            ((Source) last).setFlow(flow);
        }
        return this;
    }

    /**
     * define the status of a tap as open,
     * it will be used in the simulation
     * 
     * @return the builder itself for chaining
     */
    public HBuilder open() {
        if (last instanceof Tap) {
            ((Tap) last).setOpen(true);
        }
        return this;
    }

    /**
     * define the status of a tap as closed,
     * it will be used in the simulation
     * 
     * @return the builder itself for chaining
     */
    public HBuilder closed() {
        if (last instanceof Tap) {
            ((Tap) last).setOpen(false);
        }
        return this;
    }

    /**
     * define the proportions of input flow distributed
     * to each output of the preceding a multisplit
     * 
     * @param props the proportions
     * @return the builder itself for chaining
     */
    public HBuilder withPropotions(double[] props) {
        if (last instanceof Multisplit) {
            ((Multisplit) last).setProportions(props);
        }
        return this;
    }

    /**
     * define the maximum flow theshold for the previous element
     * 
     * @param max flow threshold
     * @return the builder itself for chaining
     */
    public HBuilder maxFlow(double max) {
        last.setMaxFlow(max);
        return this;
    }


    // --- Internal helper method ---

    private void connectToCurrentOutput(Element target) {
        if (currentSplitter != null) {
            currentSplitter.connect(target, currentOutputIndex);
        } else if (currentMultisplit != null) {
            currentMultisplit.connect(target, currentOutputIndex);
        } else if (last != null) {
            last.connect(target);
        }
        last = target;
    }
    
    
}
