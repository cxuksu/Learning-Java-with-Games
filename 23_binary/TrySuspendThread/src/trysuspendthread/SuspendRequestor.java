/*
 * SuspendRequestor -- A class that synchronize two threads.
 * The two thread must share one object of SuspendRequestor.
 * That is, when one thread instantiate a object of SuspendRequestor,
 * it passes the object to the other thread.
 */
package trysuspendthread;

public class SuspendRequestor {

    private boolean suspendRequested = true;
    
    public SuspendRequestor() {
    }

    public synchronized void set(boolean b) {
        suspendRequested = b;
        notify();
    }

    public synchronized void waitForResume() throws InterruptedException {
        while (suspendRequested) {
            try {
                wait();
            } catch (Exception ex) {
            }
        }
    }

    public void requestSuspend() {
        set(true);
    }

    public void requestResume() {
        set(false);
    }
}
