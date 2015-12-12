package pl.biometria.voice.recorder;

public class Stopper extends Thread implements Runnable {
	Integer recordTime;
	Stoppable thread;

	public Stopper(Integer recordTime, Stoppable thread) {
		this.recordTime = recordTime;
		this.thread = thread;
	}

	public void run() {
		try {
			Thread.sleep(recordTime);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		thread.finish();
	}
}
