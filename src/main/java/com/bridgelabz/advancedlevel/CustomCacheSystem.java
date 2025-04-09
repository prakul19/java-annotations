package com.bridgelabz.advancedlevel;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
// Annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface CacheResult {}
class ExpensiveService {
    @CacheResult
    public long computeFactorial(int n) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        long result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }
}
// Caching Proxy
class CacheProxy {
    private final Map<String, Object> cache = new HashMap<>();
    @SuppressWarnings("unchecked")
    public <T> T createProxy(T obj) {
        Class<?> clazz = obj.getClass();
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                clazz.getInterfaces(),
                (proxy, method, args) -> {
                    Method realMethod = clazz.getMethod(method.getName(), method.getParameterTypes());
                    if (realMethod.isAnnotationPresent(CacheResult.class)) {
                        String key = method.getName() + Arrays.toString(args);
                        if (cache.containsKey(key)) {
                            System.out.println("Fetching for input: " + Arrays.toString(args));
                            return cache.get(key);
                        } else {
                            Object result = realMethod.invoke(obj, args);
                            cache.put(key, result);
                            return result;
                        }
                    } else {
                        return realMethod.invoke(obj, args);
                    }
                }
        );
    }
}
// Interface
interface IExpensiveService {
    long computeFactorial(int n);
}
public class CustomCacheSystem {
    public static void main(String[] args) {
        IExpensiveService service = new ExpensiveService()::computeFactorial;
        CacheProxy proxyCreator = new CacheProxy();
        IExpensiveService proxy = proxyCreator.createProxy(service);

        System.out.println("First Call: " + proxy.computeFactorial(5)); // Calculates
        System.out.println("Second Call: " + proxy.computeFactorial(5)); // Cached
        System.out.println("Different Call: " + proxy.computeFactorial(6)); // Calculates
    }
}

