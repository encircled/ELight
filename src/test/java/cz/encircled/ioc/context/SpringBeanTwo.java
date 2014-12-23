package cz.encircled.ioc.context;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by Encircled on 23-Dec-14.
 */
@Repository
@Conditional(FalseCond.class)
public class SpringBeanTwo {

    //    @Autowired
    private SpringBean two;

    public SpringBeanTwo() {
        System.out.println("BEAN CONSTRUCTOR");
    }

    @PostConstruct
    public void init() {
        System.out.println("BeanTwo init : " + two);
    }

}
