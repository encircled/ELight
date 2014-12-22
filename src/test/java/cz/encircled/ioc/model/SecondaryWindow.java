package cz.encircled.ioc.model;

import cz.encircled.ioc.annotation.Component;
import cz.encircled.ioc.annotation.Order;

/**
 * Created by Encircled on 22-Dec-14.
 */
@Component
@Order(2)
public class SecondaryWindow implements Window {
}
