package lab2.practice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Класс должен содержать логику подмены значений филдов заданых по умолчанию в контексте.
 * Заменяет строковые значение в бинах типа
 *
 * @see Printer
 * на значения в
 * @see PropertyRepository
 * Использует изначальные значения как ключи для поиска в PropertyRepository
 */

@Component
public class PropertyPlaceholderBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyPlaceholderBeanFactoryPostProcessor.class);
    private static final String MESSAGE = "message";

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(beanClassName);
                if (Printer.class.isAssignableFrom(clazz)) {
                    MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
                    String startMessage = (String) propertyValues.get(MESSAGE);
                    LOGGER.info("Initial message: {}", startMessage);
                    String message = PropertyRepository.get(startMessage);
                    propertyValues.add(MESSAGE, message);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Could not find class by name: " + beanClassName, e);
            }
        }
    }
}
