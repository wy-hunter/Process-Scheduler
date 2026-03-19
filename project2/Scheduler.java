//
// CSCI 70300 -- 2025 SP -- Edgar E. Troudt, Ph.D.
//

public class Scheduler extends Thread {

	private ProcessorCore pcore;
	private Clock         clock;
	private int           quantum;
	private Process  	  queue;
	private int           ttl;
	private int			  backPointer = 0, frontPointer = 0;

	public Scheduler ( Clock clock, ProcessorCore pcore, int quantum ) {
		this.pcore   = pcore;
		this.clock   = clock;
		this.quantum = quantum;
		//ttl = quantum;
	}

	public void enqueue ( Process proc ) {
		System.out.println( "\t\u001B[34mScheduler: Process #" 
			+ proc.getID() + " enqueued.\u001B[0m" );

		queue = proc;
		ttl   = quantum;

		// send the process to the processor.
		// obviously this is not proper behavior.
		pcore.interrupt( queue );
 	}

	public void run() {
		// run infinitely.
		while ( true ) {
			// yield processes in the processor that have exhausted their quantum.
			
			// keep track of the quantum.

			/* FILL IN #8 */
			if (queue != null) {
				ttl--;

				if (ttl < 0) {
					pcore.processYields();
					queue = null;
				}
			}
			clock.semaphore();
		}
	}
}