//
// CSCI 70300 -- 2025 SP -- Edgar E. Troudt, Ph.D.
//
import java.util.LinkedList;
import java.util.Random;

public class SchedulerCWRR extends Scheduler {

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

	public SchedulerCWRR ( Clock clock, ProcessorCore pcore, int quantum ) {
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
		System.out.println("SchedulerCWRR started.");
		Process p = null;
		ttl = 0;

		while (true) {
			if (p == null && (!queue1.isEmpty() || !queue2.isEmpty() || !queue3.isEmpty())) {
				// check if queue 1 , 2, or 3 has processes in it
				
				

				// if queue 1 ttl have time, run process in queue 1
				if (queue1_ttl > 0 && !queue1.isEmpty()) {
					System.out.println("Running process from queue 1");
					activeQueue = queue1;
					queue1_ttl--; // Decrement queue 1 ttl
				} else if (queue2_ttl > 0 && !queue2.isEmpty()) { // if queue 2 ttl have time, run process in queue 2
					System.out.println("Running process from queue 2");
					activeQueue = queue2;
					queue2_ttl--; // Decrement queue 2 ttl
				} else if (queue3_ttl > 0 && !queue3.isEmpty()) { // if queue 3 ttl have time, run process in queue 3
					System.out.println("Running process from queue 3");
					activeQueue = queue3;
					queue3_ttl--; // Decrement queue 3 ttl
				} else{
					System.out.println("All queues ttl is 0, resetting ttl to quantum");
					queue1_ttl = queue1_quantum;
					queue2_ttl = queue2_quantum;
					queue3_ttl = queue3_quantum;
					clock.semaphore();
					continue;
				}
				
				// System.out.println("This is the queues currently: \n" + "q1, quantum 1: " + toString(queue1) + "\n" + "q2, quantum 2: " + toString(queue2) + "\n" + "q3, quantum 3: " + toString(queue3)); -> Creates a concurency error
				

				p = activeQueue.remove(); // First item in queue is current process running
				System.out.println("This is the Item: " + p.getID());
				// hidden processes that are in the queue
				ttl = quantum; // Set time to live to the quantum
				pcore.interrupt(p); // Replace null process with current process
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