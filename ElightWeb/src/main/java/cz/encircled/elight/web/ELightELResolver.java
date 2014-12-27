package cz.encircled.elight.web;

import cz.encircled.elight.core.context.AnnotationContext;

import javax.el.ELContext;
import javax.el.ELResolver;
import java.beans.FeatureDescriptor;
import java.util.Iterator;

/**
 * Created by Encircled on 24-Dec-14.
 */
public class ELightELResolver extends ELResolver {

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (base != null) {
            return null;
        }
        AnnotationContext context1 = new AnnotationContext("cz.encircled.elight.web");
        if (context1.containsComponent(property.toString())) {
            context.setPropertyResolved(true);
            return context1.getComponent(property.toString());
        }
        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        return true;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        return null;
    }

}
