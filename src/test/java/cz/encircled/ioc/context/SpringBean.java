package cz.encircled.ioc.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by Encircled on 23-Dec-14.
 */
@Repository
@Conditional(value = FalseCond.class)
public class SpringBean {

    public SpringBean() {
        System.out.println("CREATE BEAN");
    }

    @Autowired
    private SpringBeanTwo two;

    public boolean isInitialized = false;

    @PostConstruct
    public void init() {
        isInitialized = true;
        System.out.println("Bean init : " + two);
    }

}
