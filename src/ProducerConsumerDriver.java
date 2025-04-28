import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerDriver {
    private static final int MAX_QUEUE_CAPACITY = 5;

    public static void main(String[] args) {
        DataQueue dataQueue = new DataQueue(MAX_QUEUE_CAPACITY);
        List<Thread> threads = new ArrayList<>();
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();

        int producerCount = 5;
        int consumerCount = 5;

        for (int i = 0; i < producerCount; i++) {
            Producer producer = new Producer(dataQueue);
            Thread producerThread = new Thread(producer, "Producer-" + i);
            producers.add(producer);
            threads.add(producerThread);
            producerThread.start();
        }

        for (int i = 0; i < consumerCount; i++) {
            Consumer consumer = new Consumer(dataQueue);
            Thread consumerThread = new Thread(consumer, "Consumer-" + i);
            consumers.add(consumer);
            threads.add(consumerThread);
            consumerThread.start();
        }

        // Let threads run for 10 seconds
        MyThread.sleep(10000);

        // Tell all producers and consumers to stop
        producers.forEach(Producer::stop);
        consumers.forEach(Consumer::stop);

        // Wait for all threads to finish
        MyThread.waitForAllThreadsToComplete(threads);

        System.out.println("All threads have shut down gracefully.");
    }
}
