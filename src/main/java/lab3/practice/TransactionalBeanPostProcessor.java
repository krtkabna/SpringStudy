package lab3.practice;

import lab3.practice.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TransactionalBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> map = new HashMap<>();
    private final Map<Class<?>, List<String>> methods = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        List<String> beanMethods = new ArrayList<>();

        Arrays.stream(bean.getClass().getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(Transactional.class))
            .map(Method::getName)
            .forEach(beanMethods::add);

        if (!beanMethods.isEmpty()) {
            map.put(beanName, clazz);
            methods.put(clazz, beanMethods);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = map.get(beanName);
        if (clazz != null) {
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (proxy, method, args) -> {
                boolean isTransactional = methods.get(clazz).contains(method.getName());
                if (isTransactional) {
                    log.info("Start transaction: {}.{}", beanName, method.getName());
                    Object value = ReflectionUtils.invokeMethod(method, bean, args);
                    log.info("Finish transaction: {}.{}", beanName, method.getName());
                    return value;
                }
                return ReflectionUtils.invokeMethod(method, bean, args);
            });
        }
        return bean;
    }
}
