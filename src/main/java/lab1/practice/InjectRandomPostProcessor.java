package lab1.practice;

import lab1.practice.util.RandomUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

/**
 * класс спрингового пост процессора, должен имплементировать интерфейс
 *
 * @see BeanPostProcessor
 * <p>
 * Класс отвечает за логику инжекта случайного числа в поле, проаннотированное специально обученной аннотацией
 */
public class InjectRandomPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            InjectRandom annotation = field.getDeclaredAnnotation(InjectRandom.class);
            if (annotation != null) {
                field.setAccessible(true);
                Object value = RandomUtil.getRandom(field.getType().getName(), annotation.limit());
                ReflectionUtils.setField(field, bean, value);
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
