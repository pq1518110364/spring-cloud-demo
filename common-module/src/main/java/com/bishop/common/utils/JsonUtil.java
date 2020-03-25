package com.bishop.common.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2019/11/7 10:41
 * @Description:
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonUtil() {
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static <C> C toBean(String json, Class<C> cls) throws RuntimeException {
        try {
            return mapper.readValue(json, cls);
        } catch (Exception var3) {
            throw new RuntimeException("JSON【" + json + "】转对象时出错:" + var3.getMessage(), var3);
        }
    }

    public static <C> C toBean(JsonNode jsonNode, Class<C> cls) throws JsonParseException, JsonMappingException, IOException {
        Assert.notNull(jsonNode, "jsonNode can not be empty.");
        return mapper.convertValue(jsonNode, cls);
    }

    public static JsonNode toJsonNode(Object obj) throws IOException {
        Assert.notNull(obj, "obj can not be empty.");
        return (JsonNode)mapper.convertValue(obj, JsonNode.class);
    }

    public static JsonNode toJsonNode(String json) throws IOException {
        return mapper.readTree(json);
    }

    public static <C> C toBean(String json, TypeReference<C> typeRef) throws JsonParseException, JsonMappingException, IOException {
        C list = mapper.readValue(json, typeRef);
        return list;
    }

    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public static <T> Map<String, T> toMap(String json) throws IOException {
        Map<String, T> map = (Map)mapper.readValue(json, Map.class);
        return map;
    }

    public static void toMap(Map<String, Object> map, JsonNode json) {
        Iterator iter = json.fields();

        while(true) {
            Map.Entry entry;
            ArrayNode nodes;
            ArrayList jary;
            label41:
            do {
                while(iter.hasNext()) {
                    entry = (Map.Entry)iter.next();
                    JsonNode node = (JsonNode)entry.getValue();
                    if (node.isObject() && node.size() > 0) {
                        Map<String, Object> mapSub = new HashMap();
                        toMap(map, node);
                        map.put((String) entry.getKey(), mapSub);
                    }

                    if (node.isArray()) {
                        jary = new ArrayList();
                        nodes = (ArrayNode)node;
                        continue label41;
                    }

                    if (node.isNumber()) {
                        map.put((String) entry.getKey(), node.numberValue());
                    }

                    if (node.isBoolean()) {
                        map.put(String.valueOf(entry.getKey()), node.booleanValue());
                    }

                    if (node.isTextual()) {
                        map.put((String) entry.getKey(), node.textValue());
                    }
                }

                return;
            } while(nodes.size() <= 0);

            Iterator var7 = nodes.iterator();

            while(var7.hasNext()) {
                JsonNode item = (JsonNode)var7.next();
                Map<String, Object> _map = new HashMap();
                toMap(_map, item);
                jary.add(_map);
            }

            map.put((String) entry.getKey(), jary);
        }
    }

    public static String getString(ObjectNode obj, String key, String defaultValue) {
        if (!isContainsKey(obj, key)) {
            return defaultValue;
        } else {
            try {
                if (obj.get(key).isObject() || obj.get(key).isArray()) {
                    return toJson(obj.get(key));
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return obj.get(key).asText();
        }
    }

    public static String getString(ObjectNode obj, String key) {
        return getString(obj, key, "");
    }

    public static int getInt(ObjectNode obj, String key) {
        return !isContainsKey(obj, key) ? 0 : obj.get(key).asInt();
    }

    public static int getInt(ObjectNode obj, String key, int defaultValue) {
        return !isContainsKey(obj, key) ? defaultValue : obj.get(key).asInt();
    }

    public static boolean getBoolean(ObjectNode obj, String key) {
        return !isContainsKey(obj, key) ? false : obj.get(key).asBoolean();
    }

    public static boolean getBoolean(ObjectNode obj, String key, boolean defaultValue) {
        return !isContainsKey(obj, key) ? defaultValue : obj.get(key).asBoolean();
    }

    public static boolean isNotEmptyJsonArr(String jsonArrStr) {
        return !isEmptyJsonArr(jsonArrStr);
    }

    public static boolean isEmptyJsonArr(String jsonArrStr) {
        if (StringUtil.isEmpty(jsonArrStr)) {
            return true;
        } else {
            try {
                ArrayNode jsonAry = (ArrayNode)toJsonNode(jsonArrStr);
                return jsonAry.size() <= 0;
            } catch (Exception var2) {
                System.out.println(var2.toString());
                return true;
            }
        }
    }

    public static boolean isContainsKey(ObjectNode obj, String key) {
        if (obj != null && key != null) {
            Iterator iterator = obj.fieldNames();

            while(iterator.hasNext()) {
                String name = (String)iterator.next();
                if (key.equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String escapeSpecialChar(String str) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            switch(c) {
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '"':
                    sb.append("\\\"");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    sb.append(c);
            }
        }

        return sb.toString();
    }

    public static void removeNull(ObjectNode jsonObject) {
        Iterator newSet = jsonObject.fields();

        while(newSet.hasNext()) {
            Map.Entry<String, JsonNode> ent = (Map.Entry)newSet.next();
            JsonNode val = jsonObject.get((String)ent.getKey());
            if (val instanceof NullNode) {
                jsonObject.put((String)ent.getKey(), "");
            }
        }

    }

    public static void removeNull(ArrayNode jsonArray) {
        for(int i = 0; i < jsonArray.size(); ++i) {
            removeNull((ObjectNode)jsonArray.get(i));
        }

    }

    public static ObjectNode arrayToObject(ArrayNode jsonArray, String keyName) throws IOException {
        ObjectNode jsonObject = getMapper().createObjectNode();

        for(int i = 0; i < jsonArray.size(); ++i) {
            JsonNode temp = toJsonNode((Object)jsonArray.get(i));
            jsonObject.put(temp.get(keyName).asText(), temp);
        }

        return jsonObject;
    }

    public static ArrayNode objectToArray(JsonNode jsonObject) {
        ArrayNode jsonArray = getMapper().createArrayNode();
        Iterator newSet = jsonObject.fields();

        while(newSet.hasNext()) {
            Map.Entry<String, JsonNode> ent = (Map.Entry)newSet.next();
            jsonArray.add(jsonObject.get((String)ent.getKey()));
        }

        return jsonArray;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof ObjectNode) {
            return ((ObjectNode)obj).isObject();
        } else {
            return obj instanceof ArrayNode ? ((ArrayNode)obj).isArray() : NullNode.getInstance().equals(obj);
        }
    }

    public static List<ObjectNode> arrayToList(ArrayNode jsonArray) throws IOException {
        List<ObjectNode> list = new ArrayList();

        for(int i = 0; i < jsonArray.size(); ++i) {
            JsonNode temp = toJsonNode((Object)jsonArray.get(i));
            list.add((ObjectNode)temp);
        }

        return list;
    }
/*
    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormat));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormat));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormat));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormat));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormat));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormat));
        mapper.registerModule(javaTimeModule);
        mapper.setDateFormat(myDateFormat);
    }*/
}
