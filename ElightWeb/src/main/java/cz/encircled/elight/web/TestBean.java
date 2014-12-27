package cz.encircled.elight.web;

import cz.encircled.elight.core.annotation.Component;

/**
 * Created by Encircled on 25-Dec-14.
 */
@Component
public class TestBean {

    public String propName = "YAY";

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }


    public String someMethod() {
        return "METHOD!!";
    }

}
