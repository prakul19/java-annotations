package com.bridgelabz.intermediatelevel;
import java.lang.annotation.*;
import java.lang.reflect.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogExecutionTime {}
class DemoTasks {
    @LogExecutionTime
    public void fastTask() {
        for (int i = 0; i < 1_000; i++) {
            int x = i * i;
        }
    }
    @LogExecutionTime
    public void slowTask() {
        for (int i = 0; i < 1_000_000; i++) {
            int x = i * i;
        }
    }
}
// handle execution timing using reflection
public class ExecutionTimer {
    public static void main(String[] args) throws Exception {
        DemoTasks tasks = new DemoTasks();
        Method[] methods = DemoTasks.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                long start = System.nanoTime();
                method.invoke(tasks);
                long end = System.nanoTime();
                System.out.println("Executed " + method.getName() + " in " + (end - start) / 1_000_000.0 + " ms");
            }
        }
    }
}

