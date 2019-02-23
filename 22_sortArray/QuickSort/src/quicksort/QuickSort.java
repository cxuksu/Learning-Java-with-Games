/*
 * QuickSort.java - A class that implements the quick sorting algorithm.
 * It is a thread so that it has the run() method and the waitForBarExchange()
 * method. Due to the swap two integers is faster than the position exchange of
 * two animated bar sprites, the algorithm needs to be paused and waits for the
 * finishing of the position exchange of two bars.
 */
package quicksort;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class QuickSort extends Thread {

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Integer> theAry;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<BarSprite> tempAry;
    private boolean waiting;
    private BarComposite barComposite;
    @SuppressWarnings("FieldMayBeFinal")
    private PropertyChangeSupport pChange;

    public QuickSort() {
        pChange = new PropertyChangeSupport(this);
    }

    public void initQuickSort() {
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
        quickSortAlgo();
    }

    private void waitForBarExchange() {
        try {
            while (isWaiting()) {
                QuickSort.sleep(100); // "this" is sleep
            }
        } catch (Exception ex) {
        }
        setWaiting(true);
    }

    public void quickSortAlgo() { // ascending sort
        recQuickSort(0, theAry.size() - 1);
    }

    public void recQuickSort(int in, int out) {
        if ((out - in) >= 1) {
            Integer pivot = theAry.get(out);
            firePivotArrow(out);

            int partition = partitionAry(in, out, pivot);
            recQuickSort(in, partition - 1);
            recQuickSort(partition + 1, out);
        }
    }

    public int partitionAry(int in, int out, int pivot) {
        int smallerIdx = in - 1;
        int largerIdx = out;
        while (true) {
            while (theAry.get(++smallerIdx) < pivot) {
                fireInArrow(smallerIdx);
            }
            while ((largerIdx > 0) && (theAry.get(--largerIdx) > pivot)) {
                fireOutArrow(largerIdx);
            }
            if (smallerIdx >= largerIdx) {
                break;
            } else {
                swapInt(smallerIdx, largerIdx);
            }
        }
        // in case when smallerIdx == out, it is impossible
        // to swap two same bars, then the program cannot
        // be further executed. To skip this case,
        // the following checking is necessary.
        if (smallerIdx < out) {
            swapInt(smallerIdx, out); // restore pivot
        }
        return smallerIdx;
    }

    public void swapInt(int smallerIdx, int largerIdx) {
        // swap integers in theAry
        Integer temp = theAry.get(smallerIdx);
        theAry.set(smallerIdx, theAry.get(largerIdx));
        theAry.set(largerIdx, temp);
        // set arrows and swap bars
        fireInArrow(smallerIdx);
        fireOutArrow(largerIdx);
        barComposite.swapBar(smallerIdx, largerIdx);
        waitForBarExchange();
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

    // asking gameCanvas to assign inArrow. In order to
    // display arrows in the "while" loop in sorting algo, 
    // pausing the arrow display and let paintSprite() in
    // the ArrowSprite.java to resume it.
    public void fireInArrow(int inIdx) {
        pChange.firePropertyChange("inArrow", "-1", inIdx + "");
        waitForBarExchange();
    }

    // asking gameCanvas to assign outArrow
    public void fireOutArrow(int outIdx) {
        pChange.firePropertyChange("outArrow", "-1", outIdx + "");
        waitForBarExchange();
    }

    public void firePivotArrow(int pivotIdx) {
        pChange.firePropertyChange("pivotArrow", "-1", pivotIdx + "");
    }

    public void setBarComposite(BarComposite barComposite) {
        this.barComposite = barComposite;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pChange.addPropertyChangeListener(pcl);
    }
}
