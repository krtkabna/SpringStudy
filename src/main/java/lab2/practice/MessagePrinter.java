package lab2.practice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessagePrinter implements Printer {
    private String message;

    public void print() {
        System.out.println(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
