package cz.encircled.ioc.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Service;

/**
 * Created by Encircled on 23-Dec-14.
 */
@Service
public class FalseCond implements Condition {

    @Autowired
    private SpringBean springBean;

    public FalseCond() {
        System.out.println("CREATE CONDITION");
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        System.out.println("Matches?" + (springBean != null));
        return false;
    }
}
