package lab2.practice;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Класс должен содержать логику подмены значений филдов заданых по умолчанию в контексте.
 * Заменяет строковые значение в бинах типа
 *
 * @see Printer
 * на значения в
 * @see PropertyRepository
 * Использует изначальные значения как ключи для поиска в PropertyRepository
 */

public class PropertyPlaceholder implements BeanFactoryPostProcessor {
    private static final String MESSAGE = "message";

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(beanClassName);
                if (Printer.class.isAssignableFrom(clazz)) {
                    String message = PropertyRepository.get(MESSAGE);
                    beanDefinition.getPropertyValues().add(MESSAGE, message);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Could not find class by name: " + beanClassName, e);
            }
        }
    }
}
