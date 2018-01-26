package annotationAndReflect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Inherited
public @interface Girl {
    String name() default "haoge";
    int[] age();
}
