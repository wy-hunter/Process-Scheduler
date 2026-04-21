abstract class Scheduler extends Thread {

	 Scheduler ( ) {}

	abstract public void enqueue ( Process proc );
    
	abstract public void run();
	abstract public void removeProcess( Process proc );
}