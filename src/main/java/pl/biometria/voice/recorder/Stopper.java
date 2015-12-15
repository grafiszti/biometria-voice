package pl.biometria.voice.recorder;

public class Stopper extends Thread implements Runnable {
    long recordTime;
    Stoppable thread;

    public Stopper(long recordTime, Stoppable thread) {
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
