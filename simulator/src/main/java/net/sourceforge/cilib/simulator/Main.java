/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.simulator;

import java.io.File;
import java.util.List;

/**
 * This is the entry point for the CIlib simulator. This class accepts one
 * command line parameter, which is the name of the XML config file to parse.
 *
 */
public final class Main {

    private Main() {
        throw new UnsupportedOperationException("Cannot instantiate.");
    }

    /**
     * Main entry point for the simulator.
     * @param args provided arguments.
     */
    public static void main(String[] args) {
        System.out.println("Please note that this simulator is deprecated.");
        System.out.println("Not assistance will be given on XML specifications from v0.9");
        System.out.println("Migrate to the new script based simulator");

        if (args.length != 1) {
            System.out.println("Please provide the correct arguments.\nUsage: Simulator <simulation-config.xml>");
            System.exit(1);
        }

        final List<Simulator> simulators = SimulatorShell.prepare(new File(args[0]));
        ProgressText progress = new ProgressText(simulators.size());

        SimulatorShell.execute(simulators, progress);
    }
}
