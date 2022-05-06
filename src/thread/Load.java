package thread;

public class Load extends Thread{
	
	//Attributes
	private int seconds;
	private boolean running;
	
	private Thread thread;


	public Load() {
		setThread(new Thread(this));
		seconds = 0;
		running = true;
	}

	@Override
	public void run() {

		while (running) {
			seconds += 1;			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public int getSeconds() {
		return seconds;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

}
