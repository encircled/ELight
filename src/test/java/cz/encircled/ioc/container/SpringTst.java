package cz.encircled.ioc.container;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class SpringTst {

    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("cz");
//        context.getBean("testOne");
//        context.getBean(TestOne.class);
    }

}
