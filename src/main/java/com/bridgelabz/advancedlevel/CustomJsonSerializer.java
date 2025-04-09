package com.bridgelabz.advancedlevel;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JsonField {
    String name();
}
// a class with annotated fields
class User {
    @JsonField(name = "user_name")
    private String username;
    @JsonField(name = "user_age")
    private int age;
    private String ignoredField;
    public User(String username, int age, String ignoredField) {
        this.username = username;
        this.age = age;
        this.ignoredField = ignoredField;
    }
}
// Serializer Utility
class JsonSerializer {
    public static String toJson(Object obj) {
        StringBuilder json = new StringBuilder("{");
        Class<?> clazz = obj.getClass();
        List<String> jsonElements = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonField.class)) {
                field.setAccessible(true);
                JsonField annotation = field.getAnnotation(JsonField.class);
                try {
                    Object value = field.get(obj);
                    jsonElements.add("\"" + annotation.name() + "\": \"" + value + "\"");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to access field: " + field.getName(), e);
                }
            }
        }
        json.append(String.join(", ", jsonElements)).append("}");
        return json.toString();
    }
}
public class CustomJsonSerializer {
    public static void main(String[] args) {
        User user = new User("Prakul", 21, "ignored");
        String json = JsonSerializer.toJson(user);
        System.out.println(json);
    }
}

