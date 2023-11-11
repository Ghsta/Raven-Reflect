package zen.relife.eventbus;

import java.lang.annotation.*;

@Documented
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EventTarget {
    byte value() default 2;
}

