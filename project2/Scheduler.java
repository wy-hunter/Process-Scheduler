//
// CSCI 70300 -- 2025 SP -- Edgar E. Troudt, Ph.D.
//
import java.util.LinkedList;

public class Scheduler extends Thread {

	private ProcessorCore pcore;
	private Clock         clock;
	private int           quantum;
	private LinkedList<Process>  	  queue = new LinkedList<>();
	private LinkedList<Integer>  	  queueRepresentationsString = new LinkedList<>();
	private Integer			  currentprocessID = 0;
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

		queue.add(proc);
		queueRepresentationsString.add(proc.getID());
 	}

	public void run() {
		Process p = null;
		ttl = 0;

		while (true) {
			if (p == null && !queue.isEmpty()) { // If there is no current process running and the queue still has processes
				System.out.println("No process in, adding process to processor core");
				System.out.println("This is the queuebefore: " + toString(queueRepresentationsString));
				p = queue.remove(); // First item in queue is current process running
				/*
				for (Process process : queue) {
					System.out.println(process.getID());
				}
				*/
				currentprocessID = queueRepresentationsString.remove(); // Remove the first item in the queue representation string used to track what is going on in 
				// hidden processes that are in the queue
				ttl = quantum; // Set time to live to the quantum
				pcore.interrupt(p); // Replace null process with current process
			}

			if (p != null) { // If there is a current process running
				ttl--; // Continuously decrement ttl
				if (ttl <= 0 || p.isDone()) { // If ttl reaches quantum
					if(!p.isDone()) {
						queue.add(p); // If process isn't done, add process back to queue
						queueRepresentationsString.add(currentprocessID); // Add process ID to the queue representation string
					}

					p = null; // Nullify the process
				}
			}
			clock.semaphore();
		}
	}

	public String toString(LinkedList<Integer> repqueue) {
		String s = "";
		for (Integer id : repqueue) {
			s += id + " ";
		}
		return s;

	}
	public void removeProcess( Process proc ) {
		queue.remove(proc);
	}
}