import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Problem2 {
    private static final int NUM_GUESTS = 50;

    enum ViewStatus {
        AVAILABLE,
        BUSY
    }

    // Keeps track of what viewers have already seen the exhibit
    private static final Set<Long> viewersVisited = new HashSet<>();
    private static final Object mutex = new Object();
    private static ViewStatus exhibitStatus = ViewStatus.AVAILABLE;

    // Note: the bounds for min and max are both inclusive
    private static int getRandomNumber(int min, int max) {
        Random rng = new Random();
        return rng.nextInt(max - min + 1) + min;
    }

    private static void viewExhibit(int viewerIndex) {
        long viewerId = Thread.currentThread().getId();

        while (viewersVisited.size() < NUM_GUESTS) {
            synchronized (mutex) {
                if (exhibitStatus == ViewStatus.AVAILABLE && !viewersVisited.contains(viewerId)) {
                    exhibitStatus = ViewStatus.BUSY;
                    System.out.println("Guest #" + viewerIndex + " entered the showroom");
                    // try {
                    //     Thread.sleep(getRandomNumber(10, 500));
                    // } catch (InterruptedException e) {
                    //     e.printStackTrace();
                    // }
                    exhibitStatus = ViewStatus.AVAILABLE;
                    viewersVisited.add(viewerId);
                }
            }
        }
    }

    public static void main(String[] args) {
        // long start = System.currentTimeMillis();
        Thread[] threads = new Thread[NUM_GUESTS];

        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> viewExhibit(index));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // long end = System.currentTimeMillis();
        // long duration = end - start;

        System.out.println("All guests have visited the vase.");
        // System.out.println("Finished in " + duration + "ms");
    }
}

