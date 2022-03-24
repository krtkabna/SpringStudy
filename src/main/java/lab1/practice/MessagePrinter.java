package lab1.practice;


public class MessagePrinter implements Printer {
    @InjectRandom
    private String message;

    // создать аннотацию и аннотировать поле так, что бы при поднятии контекста
    // в поле инжектилось случайное число от 0 до числа заданого при помощи аннотации
    @InjectRandom(limit = "9")
    private int count;

    @InjectRandom(limit = "4.2")
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
