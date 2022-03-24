package lab1.practice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRunner {
    public static void main(String[] args) {
        int seven = 7;

        ApplicationContext context = new ClassPathXmlApplicationContext("lab1/practice/context.xml");
        Printer printer = context.getBean(Printer.class);
        printer.print();
    }
}
