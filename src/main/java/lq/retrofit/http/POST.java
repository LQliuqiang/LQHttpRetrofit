package lq.retrofit.http;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface POST {

    String value() default "";

    boolean useBaseUrl() default true;

}
