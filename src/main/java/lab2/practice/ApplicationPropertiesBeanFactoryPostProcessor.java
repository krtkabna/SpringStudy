package lab2.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
public class ApplicationPropertiesBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationPropertiesBeanFactoryPostProcessor.class);
    private static final PropertyReader PROPERTY_READER = new PropertyReader("/lab2/practice/application.properties");
    private static final String MESSAGE = "message";
    private static final String PREFIX = "${";
    private static final String SUFFIX = "}";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(beanClassName);
                if (Printer.class.isAssignableFrom(clazz)) {
                    MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
                    PropertyValue propertyValue = propertyValues.getPropertyValue(MESSAGE);
                    if (propertyValue == null) {
                        throw new AssertionError();
                    }
                    TypedStringValue value = (TypedStringValue) propertyValue.getValue();
                    String oldValue = value != null ? value.getValue() : null;
                    String croppedOldValue = cropValue(oldValue);
                    propertyValues.add(MESSAGE, getValueFromFile(croppedOldValue));
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Could not find class by name: " + beanClassName, e);
            }
        }
    }

    private String cropValue(String oldValue) {
        if (oldValue == null) {
            return "null";
        }
        if (oldValue.startsWith(PREFIX) && oldValue.endsWith(SUFFIX)) {
            oldValue = oldValue
                .replace(PREFIX, EMPTY)
                .replace(SUFFIX, EMPTY);
            LOGGER.info("cropped value = {}", oldValue);
        }
        return oldValue;
    }

    private String getValueFromFile(String name) {
        return PROPERTY_READER.getProperties().getProperty(name);
    }
}
