/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author USER
 */
public class URLBuilder {

    //build a query parameter URL
    public static String queryURLBuilder(String baseUrl, HashMap<String, String> parameters) {
        String url = baseUrl;
        int i = 0;

        Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> next = iterator.next();
            if (i == 0) {
                url = url + "?" + next.getKey() + "=" + next.getValue();
                i++;
            } else {
                url = url + "&" + next.getKey() + "=" + next.getValue();

            }

        }
        System.out.println(url);
        return url;
    }

    public static String pathURLBuilder(String baseUrl, String[] parameters) {
        for (String parameter : parameters) {
            baseUrl = baseUrl + "/" + parameter;
        }
        return baseUrl;
    }

    public static String payloadBuilder(Object parameters) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Class clas = parameters.getClass();
        if (clas.isPrimitive()) {
            if (clas == String.class) {
                return String.format("\"%s\"", parameters);
            } else {
                return parameters.toString();

            }
        } else {
            String body = "";
            Field[] fields = parameters.getClass().getDeclaredFields();

            int i = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                Class clazz = field.getType();
                if (clazz.isPrimitive()) {

                    if (i == 0) {
                        body = String.format("%s\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                    } else {
                        body = String.format("%s,\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                    }

                } else {
                    if (clazz.isArray()) {

                        Object[] params = (Object[]) field.get(parameters);
                        int j = 0;
                        String internal = "";
                        for (Object param : params) {
                            if (j == 0) {
                                internal = String.format("%s%s", internal, payloadBuilder(param));
                            } else {
                                internal = String.format("%s,%s", internal, payloadBuilder(param));
                            }
                        }
                        if (i == 0) {
                            body = String.format("%s\"%s\":[]", body, internal);
                        } else {
                            body = String.format("%s,\"%s\":[]", body, internal);
                        }

                    } else if (clazz == String.class || clazz == Character.class) {
                        if (i == 0) {
                            body = String.format("%s\"%s\":\"%s\"", body, field.getName(), field.get(parameters).toString());
                        } else {
                            body = String.format("%s,\"%s\":\"%s\"", body, field.getName(), field.get(parameters).toString());
                        }
                    } else if (clazz == Integer.class) {
                        if (i == 0) {
                            body = String.format("%s\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        } else {
                            body = String.format("%s,\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        }

                    } else if (clazz == Double.class) {
                        if (i == 0) {
                            body = String.format("%s\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        } else {
                            body = String.format("%s,\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        }
                    } else if (clazz == Long.class) {
                        if (i == 0) {
                            body = String.format("%s\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        } else {
                            body = String.format("%s,\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        }
                    } else if (clazz == Boolean.class) {
                        if (i == 0) {
                            body = String.format("%s\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        } else {
                            body = String.format("%s,\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        }
                    } else if (clazz == Short.class) {
                        if (i == 0) {
                            body = String.format("%s\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        } else {
                            body = String.format("%s,\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        }
                    } else if (clazz == Byte.class) {
                        if (i == 0) {
                            body = String.format("%s\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        } else {
                            body = String.format("%s,\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        }
                    } else if (clazz == Float.class) {
                        if (i == 0) {
                            body = String.format("%s\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        } else {
                            body = String.format("%s,\"%s\":%s", body, field.getName(), field.get(parameters).toString());
                        }
                    } else {
                        if (i == 0) {
                            body = String.format("%s\"%s\":%s", body, field.getName(), payloadBuilder(field.get(parameters)));
                        } else {
                            body = String.format("%s,\"%s\":%s", body, field.getName(), payloadBuilder(field.get(parameters)));
                        }
                    }
                }
                i++;
            }
            return String.format("{%s}", body);
        }

    }

}
