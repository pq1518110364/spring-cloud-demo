package com.bishop.common.utils;

import com.bishop.common.model.Tree;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.beanutils.*;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2019/11/7 10:48
 * @Description:
 */
public class BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    public static ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
    private static BeanUtilsBean beanUtilsBean;

    public BeanUtils() {
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof String) {
            return StringUtil.isEmpty((String)o);
        } else {
            if (o instanceof Collection) {
                if (((Collection)o).size() == 0) {
                    return true;
                }
            } else if (o.getClass().isArray()) {
                if (((Object[])((Object[])o)).length == 0) {
                    return true;
                }
            } else if (o instanceof Map) {
                if (((Map)o).size() == 0) {
                    return true;
                }
            } else {
                if (o instanceof Serializable) {
                    return ((Serializable)o).toString().trim().length() == 0;
                }

                if (o instanceof ArrayNode) {
                    ArrayNode an = (ArrayNode)o;
                    return an.isEmpty((SerializerProvider)null);
                }
            }

            return false;
        }
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNumber(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof Number) {
            return true;
        } else if (o instanceof String) {
            try {
                Double.parseDouble((String)o);
                return true;
            } catch (NumberFormatException var2) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean validClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException var2) {
            return false;
        }
    }

    public static boolean isInherit(Class cls, Class parentClass) {
        return parentClass.isAssignableFrom(cls);
    }

    public static List<String> scanPackages(String basePackages) throws IllegalArgumentException {
        ResourcePatternResolver rl = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(rl);
        List result = new ArrayList();
        String[] arrayPackages = basePackages.split(",");

        try {
            for(int j = 0; j < arrayPackages.length; ++j) {
                String packageToScan = arrayPackages[j];
                String packagePart = packageToScan.replace('.', '/');
                String classPattern = "classpath*:/" + packagePart + "/**/*.class";
                Resource[] resources = rl.getResources(classPattern);

                for(int i = 0; i < resources.length; ++i) {
                    Resource resource = resources[i];
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    result.add(className);
                }
            }
        } catch (Exception var14) {
            new IllegalArgumentException("scan pakcage class error,pakcages:" + basePackages);
        }

        return result;
    }

    public static Object getValue(Object instance, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = getField(instance.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    public static Object convertByActType(String typeName, String value) {
        Object o = null;
        if (typeName.equals("int")) {
            o = Integer.parseInt(value);
        } else if (typeName.equals("short")) {
            o = Short.parseShort(value);
        } else if (typeName.equals("long")) {
            o = Long.parseLong(value);
        } else if (typeName.equals("float")) {
            o = Float.parseFloat(value);
        } else if (typeName.equals("double")) {
            o = Double.parseDouble(value);
        } else if (typeName.equals("boolean")) {
            o = Boolean.parseBoolean(value);
        } else if (typeName.equals("java.lang.String")) {
            o = value;
        } else {
            o = value;
        }

        return o;
    }

    public static Field getField(Class<?> thisClass, String fieldName) throws NoSuchFieldException {
        if (fieldName == null) {
            throw new NoSuchFieldException("Error field !");
        } else {
            Field field = thisClass.getDeclaredField(fieldName);
            return field;
        }
    }

    public static void mergeObject(Object srcObj, Object desObj) {
        if (srcObj != null && desObj != null) {
            Field[] fs1 = srcObj.getClass().getDeclaredFields();
            Field[] fs2 = desObj.getClass().getDeclaredFields();

            for(int i = 0; i < fs1.length; ++i) {
                try {
                    fs1[i].setAccessible(true);
                    Object value = fs1[i].get(srcObj);
                    fs1[i].setAccessible(false);
                    if (null != value) {
                        fs2[i].setAccessible(true);
                        fs2[i].set(desObj, value);
                        fs2[i].setAccessible(false);
                    }
                } catch (Exception var6) {
                    logger.error("mergeObject" + var6.getMessage());
                }
            }

        }
    }

    public static void removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }

    public static <T> List<T> listToTree(List<T> list) {
        Map<String, Tree> tempMap = new LinkedHashMap();
        if (isEmpty(list)) {
            return Collections.emptyList();
        } else if (!(list.get(0) instanceof Tree)) {
            throw new RuntimeException("树形转换出现异常。数据必须实现Tree接口！");
        } else {
            List<T> returnList = new ArrayList();
            Iterator var3 = list.iterator();

            Object _obj;
            Tree obj;
            while(var3.hasNext()) {
                _obj = var3.next();
                obj = (Tree)_obj;
                obj.setIsParent("false");
                tempMap.put(obj.getId(), obj);
            }

            var3 = list.iterator();

            while(true) {
                while(var3.hasNext()) {
                    _obj = var3.next();
                    obj = (Tree)_obj;
                    String parentId = obj.getParentId();
                    if (tempMap.containsKey(parentId) && !obj.getId().equals(parentId)) {
                        if (((Tree)tempMap.get(parentId)).getChildren() == null) {
                            ((Tree)tempMap.get(parentId)).setChildren(new ArrayList());
                        }

                        ((Tree)tempMap.get(parentId)).getChildren().add(obj);
                        ((Tree)tempMap.get(parentId)).setIsParent("true");
                    } else {
                        returnList.add((T) obj);
                    }
                }

                return returnList;
            }
        }
    }

    public static <T> void listByPid(List<T> list, String pid, List<T> result) {
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            T _t = (T) var3.next();
            Tree t = (Tree)_t;
            if (isNotEmpty(t) && pid.equals(t.getParentId())) {
                listByPid(list, t.getId(), result);
                result.add(_t);
            }
        }

    }

    public static void copyNotNullProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
        if (dest == null) {
            logger.error("No destination bean specified");
        } else if (orig == null) {
            logger.error("No origin bean specified");
        } else {
            int i;
            String name;
            Object value;
            if (orig instanceof DynaBean) {
                DynaProperty[] origDescriptors = ((DynaBean)orig).getDynaClass().getDynaProperties();

                for(i = 0; i < origDescriptors.length; ++i) {
                    name = origDescriptors[i].getName();
                    if (beanUtilsBean.getPropertyUtils().isReadable(orig, name) && beanUtilsBean.getPropertyUtils().isWriteable(dest, name)) {
                        value = ((DynaBean)orig).get(name);
                        beanUtilsBean.copyProperty(dest, name, value);
                    }
                }
            } else if (orig instanceof Map) {
                Iterator entries = ((Map)orig).entrySet().iterator();

                while(entries.hasNext()) {
                    Map.Entry entry = (Map.Entry)entries.next();
                    name = (String)entry.getKey();
                    if (beanUtilsBean.getPropertyUtils().isWriteable(dest, name)) {
                        beanUtilsBean.copyProperty(dest, name, entry.getValue());
                    }
                }
            } else {
                PropertyDescriptor[] origDescriptors = beanUtilsBean.getPropertyUtils().getPropertyDescriptors(orig);

                for(i = 0; i < origDescriptors.length; ++i) {
                    name = origDescriptors[i].getName();
                    if (!"class".equals(name) && beanUtilsBean.getPropertyUtils().isReadable(orig, name) && beanUtilsBean.getPropertyUtils().isWriteable(dest, name)) {
                        try {
                            value = beanUtilsBean.getPropertyUtils().getSimpleProperty(orig, name);
                            if (value != null) {
                                beanUtilsBean.copyProperty(dest, name, value);
                            }
                        } catch (NoSuchMethodException var6) {
                            var6.printStackTrace();
                        }
                    }
                }
            }

        }
    }

    public static Object cloneBean(Object bean) throws Exception {
        return beanUtilsBean.cloneBean(bean);
    }

    public static void setProperty(Object bean, String name, Object value) {
        try {
            beanUtilsBean.setProperty(bean, name, value);
        } catch (Exception var4) {
            handleReflectionException(var4);
        }

    }

    private static void handleReflectionException(Exception e) {
        ReflectionUtils.handleReflectionException(e);
    }

    static {
        beanUtilsBean = new BeanUtilsBean(convertUtilsBean, new PropertyUtilsBean());
        convertUtilsBean.register(new DateConverter(), Date.class);
        convertUtilsBean.register(new LongConverter((Object)null), Long.class);
    }
}
