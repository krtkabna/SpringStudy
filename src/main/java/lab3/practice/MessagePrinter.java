package lab3.practice;

import lab3.practice.annotation.Logger;
import lab3.practice.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

@Logger
@Slf4j
public class MessagePrinter implements Printer {
    private String message;

    @Transactional
    public void print() {
        try {
            log.info(message);
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

