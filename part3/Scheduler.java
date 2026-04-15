//
// CSCI 70300 -- 2025 SP -- Edgar E. Troudt, Ph.D.
//
import java.util.LinkedList;
import java.util.Random;

public class Scheduler extends Thread {

	private ProcessorCore pcore;
	private Clock         clock;
	private int           quantum;
	private LinkedList<Process>  	  queue1 = new LinkedList<>();
	private LinkedList<Process>  	  queue2 = new LinkedList<>();
	private LinkedList<Process>  	  queue3 = new LinkedList<>();
	private static int queue1_quantum = 1;
	private static int queue2_quantum = 2;
	private static int queue3_quantum = 3;

	private int queue1_ttl = queue1_quantum;
	private int queue2_ttl = queue2_quantum;	
	private int queue3_ttl = queue3_quantum;
	private LinkedList<Process> activeQueue = null;

	private int           ttl;	

	public Scheduler ( Clock clock, ProcessorCore pcore, int quantum ) {
		this.pcore   = pcore;
		this.clock   = clock;
		this.quantum = quantum;
		//ttl = quantum;
	}

	public void enqueue ( Process proc ) {
		System.out.println( "\t\u001B[34mScheduler: Process #" 
			+ proc.getID() + " enqueued.\u001B[0m" );

		
		Random random = new Random();
		int queueNum = random.nextInt(3) + 1;
		if (queueNum == 1) {
			queue1.add(proc);
		} else if (queueNum == 2) {
			queue2.add(proc);
		} else {
			queue3.add(proc);
		}
 	}

	public void run() {
		Process p = null;
		ttl = 0;

		while (true) {
			if (p == null && (!queue1.isEmpty() || !queue2.isEmpty() || !queue3.isEmpty())) {
				// check if queue 1 , 2, or 3 has processes in it
				
				if (activeQueue == null) {
					if (queue1_ttl > 0 && !queue1.isEmpty()) activeQueue = queue1;
					else if (queue2_ttl > 0 && !queue2.isEmpty()) activeQueue = queue2;
					else if (queue3_ttl > 0 && !queue3.isEmpty()) activeQueue = queue3;
				}
				
				if (!activeQueue.isEmpty()) {
					p = activeQueue.remove(); // First item in queue is current process running
					System.out.println("This is the Item: " + p.getID());
					// hidden processes that are in the queue
					ttl = quantum; // Set time to live to the quantum
					pcore.interrupt(p); // Replace null process with current process
				}

				if (activeQueue == queue3) {
					System.out.println("Running process from queue 3");
					queue3_ttl--;
					if (queue2_ttl > 0) activeQueue = queue2;
				} else if (activeQueue == queue2) {
					System.out.println("Running process from queue 2");
					queue2_ttl--;
					if (queue1_ttl > 0) {
						activeQueue = queue1;
					} else {
						activeQueue = queue3;
					}
				} else if (activeQueue == queue1) {
					System.out.println("Running process from queue 1");
					queue1_ttl--;
					activeQueue = queue3;
				}

				if (queue1_ttl <= 0 && queue2_ttl <= 0 && queue3_ttl <= 0) {
					System.out.println("All queues ttl is 0, resetting ttl to quantum");
					queue1_ttl = queue1_quantum;
					queue2_ttl = queue2_quantum;
					queue3_ttl = queue3_quantum;
					clock.semaphore();
					continue;
				}
			}

			if (p != null) { // If there is a current process running
				ttl--; // Continuously decrement ttl
				if (ttl <= 0 || p.isDone()) { // If ttl reaches quantum
					if(!p.isDone()) {
						activeQueue.add(p); // If process isn't done, add process back to queue
					}
					p = null; // Nullify the process
				}
			}
			clock.semaphore();
		}
	}

	public String toString(LinkedList<Process> repqueue) {
		String s = "";
		for (Process proc : repqueue) {
			s += proc.getID() + " ";
		}
		return s;

	}

	public void removeProcess( Process proc ) {
		activeQueue.remove(proc);
	}
}