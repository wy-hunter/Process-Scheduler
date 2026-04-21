// schedulerTest.java: The testing program for our scheduler.  This should be modified to accept command line input.
//
// CSCI 70300 -- 2025 SP -- Edgar E. Troudt, Ph.D.
//

public class schedulerTestRR {

	private static Clock            c;
	private static ProcessGenerator ps;
	private static ProcessorCore    pr;
	private static SchedulerRR	    sc;

	public static void main ( String args[] ) {
		// everyone follows the computer's clock
		// instantiate the clock.
		c = new Clock();

		// generates a single processor core
		// instantiate the core.
		pr = new ProcessorCore(c);

		// generates the operating system's scheduling algorithm
		// instantiate the scheduler with a quantum of 5.
		sc = new SchedulerRR(c, pr, 5);
		//pr.setScheduler(sc);

		// simulates the user + OS generating needed processes to run
		// instantiate the process generator.
		ps = new ProcessGenerator(c, sc);

		/* FILL IN #9 all of the above. */

		// start our simulation
		c.start();	// start the clock
		pr.start();	// start the processor
		sc.start();	// start the scheduler
		ps.start();	// start the process generator
	}

	public void run() {
		System.out.println("schedulerTestRR started.");
		c = new Clock();
		pr = new ProcessorCore(c);
		sc = new SchedulerRR(c, pr, 5);
		ps = new ProcessGenerator(c, sc);

		// start our simulation
		c.start();	// start the clock
		pr.start();	// start the processor
		sc.start();	// start the scheduler
		ps.start();	// start the process generator
	}
}