package lab1.practice;


public class MessagePrinter implements Printer {
    @InjectRandom
    private String message;

    @InjectRandom(limit = 9)
    private int count;

    @InjectRandom(limit = 5)
    private double aDouble;

    public void print() {
        for (int i = 0; i < count; i++) {
            System.out.println(message);
        }
        System.out.println("aDouble = " + aDouble);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
