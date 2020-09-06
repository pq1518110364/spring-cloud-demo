package utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/7/11 10:57
 * @Description:
 */
@Slf4j
public class KillNullUtil {

    public static Object killNull(Object object) {
        //遍历object类 成员为String类型 属性为空的全部替换为""
        Field[] fields = object.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                // 获取属性的名字
                String name = field.getName();
                // 将属性的首字符大写，方便构造get，set方法
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                // 获取属性的类型
                String type = field.getGenericType().toString();
                // 如果type是类类型，则前面包含"class "，后面跟类名
                if (type.equals("class java.lang.String")) {
                    Method m = object.getClass().getMethod("get" + name);
                    // 调用getter方法获取属性值
                    String value = (String) m.invoke(object);
                    if (value == null) {
                        Class[] parameterTypes = new Class[1];
                        parameterTypes[0] = field.getType();
                        m = object.getClass().getMethod("set" + name, parameterTypes);
                        String string = new String("");
                        Object[] objects = new Object[1];
                        objects[0] = string;
                        m.invoke(object, objects);
                    }
                }
                //将形参object类中在com.yu.killnull包下的引用一块遍历修改
                if (type.contains("class com.yu.killnull")) {
                    Method m = object.getClass().getMethod("get" + name);
                    // 调用getter方法获取属性值
                    Object value = m.invoke(object);
                    killNull(value);
                }
            }
        } catch (Exception e) {
            log.error("null转\"\"失败....");
            e.printStackTrace();
        }
        return object;
    }

}
