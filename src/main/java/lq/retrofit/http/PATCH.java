package lq.retrofit.http;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PATCH {
    String value() default "";

    boolean useBaseUrl() default true;
}
