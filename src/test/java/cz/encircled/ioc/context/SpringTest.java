package cz.encircled.ioc.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Encircled on 23-Dec-14.
 */
public class SpringTest {

    //    @Test
    public void springTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("cz");
        SpringBean bean = context.getBean(SpringBean.class);

    }

}
