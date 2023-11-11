package zen.relife.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();

    String description() default "114514";

    Category category();

    int key() default -1;

    boolean enabled() default false;
}
