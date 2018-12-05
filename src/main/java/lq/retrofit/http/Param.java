package lq.retrofit.http;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {

    String value();

}
