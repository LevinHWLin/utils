package com.jdk;

import org.apache.lucene.search.MultiCollectorManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class LambdaTest {

    public static void main(String[] args){
        // 常规Collections的排序
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd","","bb");
        Collections.sort(list, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("这是常规的Collections的排序的写法，需要对接口方法重写");
        for (String string : list) {
            System.out.print(string + " ");
        }

        Collections.sort(list, (Comparator<? super String>) (String a, String b) -> {
            return b.compareTo(a);
        });
        System.out.println();
        System.out.println("这是带参数类型的Lambda的写法");
        for (String string : list) {
            System.out.print(string + " ");
        }

        Collections.sort(list, (a, b) -> a.compareTo(b));
        System.out.println();
        System.out.println("这是不带参数的lambda的写法");
        for (String string : list) {
            System.out.print(string + " ");
        }

        System.out.println();
        System.out.println("list.stream().forEach(System.out::print)");
        list.stream().forEach(System.out::print);

        System.out.println();
        System.out.println("list.stream().count()");
        System.out.println("个数："+list.stream().count());

        System.out.println("list.stream().limit(2)");
        list.stream().limit(2).forEach(System.out::print);

        System.out.println();
        System.out.println("list.stream().filter(string -> !string.isEmpty())");
        list.stream().filter(string -> !string.isEmpty()).forEach(System.out::print);

        System.out.println();
        System.out.println("list.stream().distinct()");
        list.stream().distinct().forEach(System.out::print);

        System.out.println();
        System.out.println("list.stream().sorted()");
        list.stream().sorted().forEach(System.out::print);

        System.out.println();
        System.out.println("list.stream().sorted(Comparator.reverseOrder())");
        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::print);

        System.out.println();
        System.out.println("list.stream().sorted(Comparator.naturalOrder())");
        list.stream().sorted(Comparator.naturalOrder()).forEach(System.out::print);

        System.out.println();
        System.out.println("list.stream().findFirst()="+list.stream().findFirst().toString());

    }
}
