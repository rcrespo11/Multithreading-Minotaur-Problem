import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Problem1{
    private static final int NUM_DINERS = 100;
    private static boolean[] dinersVisited = new boolean[NUM_DINERS];
    private static Lock lock = new ReentrantLock();
    private static boolean isCupcakeAvailable = true;
    private static int currentDinerCount = 0;
    private static int activeDinerIndex;

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static void checkCupcake() {
        while (currentDinerCount < NUM_DINERS) {
            lock.lock();
            if (activeDinerIndex == 0) {
                if (!isCupcakeAvailable) {
                    currentDinerCount++;
                    isCupcakeAvailable = true;
                }
                if (isCupcakeAvailable && !dinersVisited[0]) {
                    currentDinerCount++;
                    isCupcakeAvailable = true;
                    dinersVisited[0] = true;
                }
            }
            lock.unlock();
        }
    }

    private static void exploreLabyrinth(int dinerIndex) {
        while (currentDinerCount < NUM_DINERS) {
            lock.lock();
            if (activeDinerIndex == dinerIndex && isCupcakeAvailable && !dinersVisited[dinerIndex]) {
                isCupcakeAvailable = false;
                dinersVisited[dinerIndex] = true;
                System.out.println("The guest #" + dinerIndex + " ate the cupcake");
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
      //  long startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[NUM_DINERS];

        threads[0] = new Thread(Problem1::checkCupcake);
        threads[0].start();

        for (int i = 1; i < NUM_DINERS; i++) {
            final int index = i;
            threads[i] = new Thread(() -> exploreLabyrinth(index));
            threads[i].start();
        }

        while (currentDinerCount < NUM_DINERS) {
            activeDinerIndex = getRandomNumber(0, NUM_DINERS - 1);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

      //  long endTime = System.currentTimeMillis();
       // long executionTime = endTime - startTime;

        System.out.println("All " + currentDinerCount + " guests have explored the labyrinth.");
      //  System.out.println("Execution time: " + executionTime + "ms");
    }
}
