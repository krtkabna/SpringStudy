package lab3.example;

@Logger
public class MessagePrinter implements Printer {
    private String message;

    public void print() {
        try {
            System.out.println(message);
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
