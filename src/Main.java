import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static final BlockingQueue<String> blockingQueue1 = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> blockingQueue2 = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> blockingQueue3 = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {
        MyString stringMaxA = new MyString();
        MyString stringMaxB = new MyString();
        MyString stringMaxC = new MyString();

        int stringLength = 100_000;// todo 100_000;
        int stringAmount = 10_000;// todo 10_000;

        new Thread(() -> {
            String[] texts = new String[stringAmount];
            for (int i = 0; i < texts.length; i++) {
                texts[i] = generateText("abc", stringLength);
                try {
                    blockingQueue1.put(texts[i]);
                    blockingQueue2.put(texts[i]);
                    blockingQueue3.put(texts[i]);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        Thread.sleep(100);
        Thread letterA = foo(stringAmount, blockingQueue1, stringMaxA, 'a');
        Thread letterB = foo(stringAmount, blockingQueue2, stringMaxB, 'b');
        Thread letterC = foo(stringAmount, blockingQueue3, stringMaxC, 'c');

        letterA.start();
        letterB.start();
        letterC.start();

        letterA.join();
        letterB.join();
        letterC.join();

//        System.out.println("\n" + stringMaxA.getText());
        System.out.println("Строка с наибольшим количеством букв 'a' содержит " + stringMaxA.getMaxAmount() + " букв 'a'.");

//        System.out.println("\n" + stringMaxB.getText());
        System.out.println("Строка с наибольшим количеством букв 'b' содержит " + stringMaxB.getMaxAmount() + " букв 'b'.");

//        System.out.println("\n" + stringMaxC.getText());
        System.out.println("Строка с наибольшим количеством букв 'c' содержит " + stringMaxC.getMaxAmount() + " букв 'c'.");
    }// main

    private static Thread foo(int stringAmount, BlockingQueue<String> blockingQueue, MyString stringMax, char c) {
        return new Thread(() -> {
            int amount = 0;
            int tempAmount;
            for (int i = 0; i < stringAmount; i++) {
                try {
                    String tempString = blockingQueue.take();
                    tempAmount = symbolCounting(c, tempString);
                    if (tempAmount > amount) {
                        stringMax.setMaxAmount(tempAmount);
                        stringMax.setText(tempString);
                        amount = tempAmount;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int symbolCounting(char chr, String text) {
        int quantity = 0;
        for (char letter : text.toCharArray()) {
            if (letter == chr) {
                quantity++;
            }
        }
        return quantity;
    }
}// class