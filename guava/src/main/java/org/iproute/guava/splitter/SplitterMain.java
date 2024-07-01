package org.iproute.guava.splitter;

import com.google.common.base.Splitter;

import java.util.Map;

/**
 * SplitterMain
 *
 * @author tech@intellij.io
 * @since 5/16/2023
 */
public class SplitterMain {
    public static void main(String[] args) {
        String input = "Fruit::Apple ,,Dessert::Waffle, Veggies::Spinach, Dairy Product::Cheese,,";


        Map<String, String> split = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings()
                .withKeyValueSeparator("::")
                .split(input);

        split.forEach((k, v) -> System.out.println("key:" + k + ";value:" + v));
    }
}
