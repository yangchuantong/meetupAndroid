package org.elastos.meetup.tools;

import android.text.TextUtils;

import org.elastos.meetup.config.SystemConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * 反射应用类
 *
 */
public class ReflactHelper {
	/**
	 * 对象
	 */
	private Object obj;
	private String[] fields;
	
	/**
	 * 字段类型map
	 */
	private Map<String, Class<?> > typeMap = new HashMap<String, Class<?> >();
	
	public ReflactHelper(Object obj) {
		try {
			this.obj = obj;
			Field[] fieldAry = obj.getClass().getDeclaredFields();
			final int len = fieldAry.length;
			fields = new String[len];
			for (int i = 0; i < len; ++i) {
				fields[i] = fieldAry[i].getName();
				typeMap.put(fields[i], fieldAry[i].getType());
			}
		} catch (Exception e) {
			if (SystemConfig.IS_DEBUG) {
				System.out.println("ReflactHelper/构造错误：" + e);
			}
		}
	}

	
	public String[] getFields() {
		return fields;
	}


	/**
	 * 调用set方法
	 * @param field
	 * @param value
	 */
	public void setFieldValue(String field, Object value) {
		try {
			if (typeMap.containsKey(field)) {
				final Class<?> typeClass = typeMap.get(field);
				Method method = obj.getClass().getMethod("set" + toUpperCaseFirstOne(field), typeClass);
				if (value.getClass().equals(typeClass)) {
					method.invoke(obj, value);
				}
				else {
					try {
						if (typeClass.equals(String.class)) {
							method.invoke(obj, String.valueOf(value));
						}
						else if (typeClass.equals(Integer.class)) {
							method.invoke(obj, Integer.valueOf(String.valueOf(value)));
						}
						else if (typeClass.equals(Double.class)) {
							method.invoke(obj, Double.valueOf(String.valueOf(value)));
						}
						else if (typeClass.equals(Long.class)) {
							method.invoke(obj, Long.valueOf(String.valueOf(value)));
						}
						else if (typeClass.equals(Float.class)) {
							method.invoke(obj, Float.valueOf(String.valueOf(value)));
						}
						else if (typeClass.equals(Short.class)) {
							method.invoke(obj, Short.valueOf(String.valueOf(value)));
						}
						else if (typeClass.getName().equals("int")) {
							method.invoke(obj, Integer.valueOf(String.valueOf(value)).intValue());
						}
						else if (typeClass.getName().equals("float") || typeClass.getName().equals("double")) {
							method.invoke(obj, Double.valueOf(String.valueOf(value)).doubleValue());
						}
						else if (typeClass.getName().equals("long")) {
							method.invoke(obj, Long.valueOf(String.valueOf(value)).longValue());
						}
						else if (typeClass.getName().equals("short")) {
							method.invoke(obj, Short.valueOf(String.valueOf(value)).shortValue());
						}
						else if (typeClass.getName().equals("char") || typeClass.getName().equals("byte")) {
							method.invoke(obj, String.valueOf(value));
						}
						else {
							if (SystemConfig.IS_DEBUG) {
								System.out.println("ReflactHelper/setFieldValue：未处理字段" + typeClass + " " + field + " 类型：" + typeClass.getName());
							}
						}
					} catch (Exception e) {
						if (SystemConfig.IS_DEBUG) {
							System.out.println("ReflactHelper/setFieldValue：转型错误：" + e);
						}
					}
				}
			}
		} catch (Exception e) {
			if (SystemConfig.IS_DEBUG) {
				System.out.println("ReflactHelper/setFieldValue error：" + e);
			}
		}
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	private String toUpperCaseFirstOne(final String str) {
		String result = new String();
		if (!TextUtils.isEmpty(str)) {
			char[] chs = str.toCharArray();
			chs[0] = Character.toUpperCase(chs[0]);
			result = String.valueOf(chs);
		}
		return result;
	}
}
