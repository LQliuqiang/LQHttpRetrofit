package lq.retrofit;

import com.sun.istack.internal.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Util {

    private Util(){}

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean strIsEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }


    @Nullable
    public static Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object, @Nullable Object... args) throws Throwable {
        throw new UnsupportedOperationException();
    }

    /**
     * 检查传递的类型是不是接口
     */
    public static <T> void validateServiceInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        } else if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }


    /**
     * 获取Annotation
     */
    public static  <T extends Annotation> T getAnnotation(Class<T> aClass){
        if (Util.class.isAnnotationPresent(aClass)) {
            return Util.class.getAnnotation(aClass);
        } else {
            throw new RuntimeException("getAnnotation error");
        }
    }


//    public static Class<?> getRawType(Type type) {
//        if (type==null){
//            throw new NullPointerException("type == null");
//        }
//        if (type instanceof Class) {
//            return (Class)type;
//        } else if (type instanceof ParameterizedType) {
//            ParameterizedType parameterizedType = (ParameterizedType)type;
//            Type rawType = parameterizedType.getRawType();
//            if (!(rawType instanceof Class)) {
//                throw new IllegalArgumentException();
//            } else {
//                return (Class)rawType;
//            }
//        } else if (type instanceof GenericArrayType) {
//            Type componentType = ((GenericArrayType)type).getGenericComponentType();
//            return Array.newInstance(getRawType(componentType), 0).getClass();
//        } else if (type instanceof TypeVariable) {
//            return Object.class;
//        } else if (type instanceof WildcardType) {
//            return getRawType(((WildcardType)type).getUpperBounds()[0]);
//        } else {
//            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
//        }
//    }


}
