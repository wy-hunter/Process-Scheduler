// Processor.java: Simulates a single core of a CPU.
//
// CSCI 70300 -- 2025 SP -- Edgar E. Troudt, Ph.D.
//

public class ProcessorCore extends Thread {

	// follows the computer clock
	private Clock   clock;
	private Process runningProc;
	private boolean internalSemaphore = false;

	// a single core's constructor
	public ProcessorCore ( Clock clock ) {
		// accept values
		/* FILL IN #7. */
		this.clock = clock;
	}

	// replace the current process with another
	public void interrupt ( Process p ) {
		// stop what's running
		processYields();

		System.out.println( "\tProcessor: Scheduler requested running process #" 
			+ p.getID() + " at time=" + clock.getTime() + ".\u001B[0m" );

		// set the new running process
		runningProc = p;
	}

	// cause the currently running process to pause
	public void processYields ( ) {
		internalSemaphore = true;

		if ( runningProc != null ) {
			System.out.println( "\tProcessor: Process #" + runningProc.getID() 
				+ " has yielded control at time=" 
				+ clock.getTime() + ".\u001B[0m" );
			runningProc = null;
		}
		// if there's no process, nothing to yield.

		internalSemaphore = false;
	}

	public void run ( ) {
		while ( true ) {
			while ( runningProc == null || internalSemaphore ) { 
				try {
					Thread.sleep(3);
				}
				catch ( InterruptedException iec ) {}
			}

			try {
				Thread.sleep(3);
			}
			catch ( InterruptedException iec ) {}

			// run one time unit
			// if the process happens to complete, yield it
			if ( runningProc != null && ! runningProc.runUnit() ) processYields();

			// wait until the next clock cycle
			clock.semaphore();
		}
	}
}