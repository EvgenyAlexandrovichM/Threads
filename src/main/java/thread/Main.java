package thread;

import java.util.concurrent.Semaphore;

public class Main {
    private static final Semaphore evenSemaphore = new Semaphore(1);
    private static final Semaphore oddSemaphore = new Semaphore(0);

    public static void main(String[] args) {
        Thread evenThread = new Thread(() -> {
            for (int i = 0; i <= 10; i += 2) {
                try {
                    evenSemaphore.acquire();
                    System.out.println("Четный " + i);
                    oddSemaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread oddThread = new Thread(() -> {
            for (int i = 1; i <= 9; i += 2) {
                try {
                    oddSemaphore.acquire();
                    System.out.println("Нечетный " + i);
                    evenSemaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        evenThread.start();
        oddThread.start();

        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}