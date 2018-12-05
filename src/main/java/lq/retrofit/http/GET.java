package lq.retrofit.http;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GET {

    String value() default "";

    boolean useBaseUrl() default true;

}
