// Clock.java: Simulates the clock of the computer, which keeps all devices in sync.
//
// CSCI 70300 -- 2025 SP -- Edgar E. Troudt, Ph.D.

public class Clock extends Thread {
	// keeps track of where we are and when the clock was last accessed
	private int count;
	private int lastTimeReported = 0;

	// returns the current computer time;
	// should also update the last reported time so the simulation knows when to stop.
	public int getTime() {
		// FILL IN #1.
		lastTimeReported = count;
		return lastTimeReported;
	}

	// this is a thread semaphore
	// it pauses a process until next clock cycle so that all are in sync.
	public void semaphore() {
		int curr = count;
		while ( curr == count ) {
			try {
				Thread.sleep(1);
			}
			catch ( InterruptedException ie ) {}
		}
	}

	// the main method of the Clock's thread.
	public void run () {
		count = 0;

		// run infinitely
		while ( true ) {
			count++;

			// ansi codes courtesy of Google Gemini
			// prompt: "how do i use ansi escape codes in java"  
			// 4/6/2025, around 2:50pm
			System.out.println( "\u001B[31mClock: " + count + "\u001B[0m" );
			try {
				// each cycle is 1000ms.
				sleep( 1000 );
			}
			catch ( InterruptedException ie ) {
			}

			// end the simulation if no thread has used the clock in 5 cycles.
			if ( (lastTimeReported + 5) < count ) {
				System.out.println( "\u001B[31mClock: system not used, shutting down." );
				System.exit(0);
			}
		}
	}
}