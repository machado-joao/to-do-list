package com.github.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    private Utils() {
    }

    public static String[] getNullPropertiesNames(Object source) {

        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] properties = beanWrapper.getPropertyDescriptors();

        Set<String> emptyProperties = new HashSet<>();

        for (PropertyDescriptor property : properties) {
            Object value = beanWrapper.getPropertyValue(property.getName());
            if (value == null) {
                emptyProperties.add(property.getName());
            }
        }

        String[] result = new String[emptyProperties.size()];
        return emptyProperties.toArray(result);
    }

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertiesNames(source));
    }
}
