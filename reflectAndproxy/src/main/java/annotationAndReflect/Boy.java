package annotationAndReflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD,ElementType.TYPE})
public @interface Boy {
    String name() default "noName";
    int age() default 0;
}
