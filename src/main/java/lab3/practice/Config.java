package lab3.practice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Date;

@Configuration
public class Config {

    @Bean
    public Printer printer() {
        MessagePrinter printer = new MessagePrinter();
        printer.setMessage(String.valueOf(new Date()));
        return printer;
    }

    @Bean
    public PerformanceLoggerBeanPostProcessor performanceLoggerBeanPostProcessor() {
        return new PerformanceLoggerBeanPostProcessor();
    }

    @Bean
    public TransactionalBeanPostProcessor transactionalBeanPostProcessor() {
        return new TransactionalBeanPostProcessor();
    }
}
