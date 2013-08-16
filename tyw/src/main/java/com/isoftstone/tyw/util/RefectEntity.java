package com.isoftstone.tyw.util;
/**
 * @author liuyulong
 */
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class RefectEntity {
    /*
     * dest 反射目标
     * src 反射源
     */
	public static void copy(Object dest, Object src) {
		Map<String, Object> srcMap = new HashMap<String, Object>();
		Field[] srcFields = src.getClass().getDeclaredFields();
		for (Field fd : srcFields) {
			try {
				fd.setAccessible(true);// 暴力取值
				srcMap.put(fd.getName(), fd.get(src)); // 获取属性值
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Field[] destFields = dest.getClass().getDeclaredFields();
		for (Field fd : destFields) {
			if (srcMap.get(fd.getName()) == null) {
				continue;
			}
			try {
			    fd.setAccessible(true);// 暴力赋值
				fd.set(dest, srcMap.get(fd.getName())); // 给属性赋值
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
