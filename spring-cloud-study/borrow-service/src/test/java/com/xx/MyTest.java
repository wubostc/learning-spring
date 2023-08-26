package com.xx;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;

@SpringBootTest
public class MyTest {


    class User implements Serializable {
        public static final long serialVersionUID = 1L;


    }

    public class A implements Serializable {
        private static final long serialVersionUID = 203480238492L;
    }

    @Test
    void test() {
        User user = new User();
        System.out.println("a1111111");
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }

//    public static void main(String[] args) {
////        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);
////
////        String encoded = encoder.encode("123");
////
////        System.out.println("encoded = " + encoded);
//
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(1);
//
//        StringBuffer sbf = new StringBuffer();
//        sbf.append(1);
//
//        String s = "sdsd";
//
//        s.compareTo(s);
//
//        Map<Object, Object> map = new HashMap<>();
//
//        map.put(1, "");
//
//    }
}
