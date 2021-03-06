package channels;

public class BlockingQueueExample {

    public static void main(String[] args) {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        System.out.println("Beginning:");
        try {
            System.out.println("Let’s put in basket: Apple");
            queue.put("Apple");
            System.out.println("Let’s put in basket: Banana");
            queue.put("Banana");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Done!");
    }
}