//
// CSCI 70300 -- 2025 SP -- Edgar E. Troudt, Ph.D.
//

public class Scheduler extends Thread {

	private ProcessorCore pcore;
	private Clock         clock;
	private int           quantum;
	private Process[]  	  queue = new Process[100];
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
		queue[backPointer] = proc;
		backPointer += 1;
 	}

	public void run() {
		// run infinitely.
		while ( true ) {
			// yield processes in the processor that have exhausted their quantum.
			
			// keep track of the quantum.

			/* FILL IN #8 */
			ttl--;
			if(queue[frontPointer] != null && ttl <= 0) {
				if(queue[frontPointer].runUnit()) enqueue(queue[frontPointer]);
				pcore.interrupt(queue[frontPointer]);
				ttl = quantum;
				frontPointer += 1;
			}
			clock.semaphore();
		}
	}
}