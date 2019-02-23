/*
 * BubbleSort.java - A class that implements the bubble sort algorithm.
 * It is a thread so that it has the run() method and the waitForBarExchange()
 * method. Due to the swap two integers is faster than the position exchange of
 * two animated bar sprites, the algorithm needs to be paused and waits for the
 * finish of the position exchange of two bars.
 */
package bubblesort;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class BubbleSort extends Thread {

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Integer> theAry;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<BarSprite> tempAry;
    private boolean waiting;
    private BarComposite barComposite;
    @SuppressWarnings("FieldMayBeFinal")
    private PropertyChangeSupport pChange;

    public BubbleSort() {
        pChange = new PropertyChangeSupport(this);
    }

    public void initBubbleSort() {
        // the sorting array theAry could be part of the init array tempAry
        tempAry = barComposite.getBarList();
        theAry = new ArrayList<Integer>();
        for (int i = 0; i < tempAry.size(); i++) {
            int aValue = tempAry.get(i).getHeight();
            if (aValue > Consts.INITBAR) {
                theAry.add(aValue);
            }
        }
        waiting = true;
    }

    @Override
    public void run() {
        bubbleSortAlgo();
    }

    private void waitForBarExchange() {
        try {
            while (isWaiting()) {
                BubbleSort.sleep(100); // "this" is sleep
            }
        } catch (Exception ex) {
        }
        setWaiting(true);
    }

    public void bubbleSortAlgo() { // ascending sort
        for (int out = theAry.size() - 1; out > 0; out--) {
            fireOutArrow(out);
            for (int in = 0; in < out; in++) {
                fireInArrow(in);
                if ((theAry.get(in + 1)) <= (theAry.get(in))) {
                    swapInt(in, in + 1);
                    waitForBarExchange();
                }
            }
        }
    }

    public void swapInt(int smallerIdx, int largerIdx) {
        Integer temp = theAry.get(smallerIdx);
        theAry.set(smallerIdx, (int) (theAry.get(largerIdx)));
        theAry.set(largerIdx, temp);
        barComposite.swapBar(smallerIdx, largerIdx);
    }

    // for debugging if needed
    public void printAry() {
        for (int i = 0; i < theAry.size(); i++) {
            System.out.print((theAry.get(i)) + " ");
        }
        System.out.println();
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    // asking gameCanvas to assign inArrow
    public void fireInArrow(int inIdx) {
        pChange.firePropertyChange("inArrow", "-1", inIdx + "");
    }

    // asking gameCanvas to assign outArrow
    public void fireOutArrow(int outIdx) {
        pChange.firePropertyChange("outArrow", "-1", outIdx + "");
    }

    public void setBarComposite(BarComposite barComposite) {
        this.barComposite = barComposite;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pChange.addPropertyChangeListener(pcl);
    }
}
