package com.game.admin.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

public class DynamicObjectCreator {

	public static final Map<String, Class> CtClassMap = new ConcurrentHashMap<>();
	public static final Map<String, Long> CtClassVersionMap = new ConcurrentHashMap<>();

//	public static void main(String[] args)
//			throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException,
//			NoSuchMethodException, InvocationTargetException, ClassNotFoundException, IOException {
//
//		DynamicObjectCreator dco = new DynamicObjectCreator();
//		Object student1 = null, team = null;
//		Map<String, Object> fieldMap = new HashMap<String, Object>();// 属性-取值map
//
//		fieldMap.put("name", "xiao ming");
//		fieldMap.put("age", 27);
//
//		student1 = dco.addField("Student", fieldMap);// 创建一个名称为Student的类
//		Class c = Class.forName("Student");
//
//		Object s1 = c.newInstance();// 创建Student类的对象
//		Object s2 = c.newInstance();
//
//		dco.setFieldValue(s1, "name", " xiao ming ");// 创建对象s1赋值
//		dco.setFieldValue(s2, "name", "xiao zhang");
//
//		fieldMap.clear();
//		List<Object> students = new ArrayList<Object>();
//		students.add(s1);
//		students.add(s2);
//
//		fieldMap.put("students", students);
//		team = dco.addField("Team", fieldMap);// //创建一个名称为Team的类
//
//		Field[] fields = team.getClass().getDeclaredFields();
//
//		if (fields != null) {
//			for (Field field : fields) {
//				System.out.println(field.getName() + "=" + dco.getFieldValue(team, field.getName()));
//			}
//		}
//	}

	public void clearClassCache(String className) {
		synchronized (CtClassMap) {
			// CtClassMap.remove(className);
			Long version = CtClassVersionMap.get(className);
			if (null == version) {
				CtClassVersionMap.put(className, 1l);
			} else {
				CtClassVersionMap.put(className, ++version);
			}
		}
	}

	private long getClassVersion(String className) {
		Long version = CtClassVersionMap.get(className);
		if (null == version) {
			return 0l;
		} else {
			return version;
		}
	}

	/**
	 * 
	 * 为对象动态增加属性，并同时为属性赋值
	 * 
	 * @param className
	 *            需要创建的java类的名称
	 * @param fieldMap
	 *            字段-字段值的属性map，需要添加的属性
	 * @return
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws IOException
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * 
	 */
	public Object addField(String className, Map<String, Object> fieldMap)

			throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException,
			IOException, NoSuchFieldException, SecurityException, IllegalArgumentException {

		Class c = null;
		synchronized (CtClassMap) {

			if (getClassVersion(className) != 0l) {
				className = className + "$" + getClassVersion(className);
				// 获得对应版本的class
//				System.err.println(className);
			}

			c = CtClassMap.get(className);
			if (c == null) {
				ClassPool pool = ClassPool.getDefault();// 获取javassist类池
				// ClassPool pool = new ClassPool(true);
				CtClass ctClass = pool.makeClass(className);// 创建javassist类

				// 为创建的类ctClass添加属性
				Iterator it = fieldMap.entrySet().iterator();

				while (it.hasNext()) { // 遍历所有的属性

					Map.Entry entry = (Map.Entry) it.next();
					String fieldName = (String) entry.getKey();
					Object fieldValue = entry.getValue();

					// 增加属性，这里仅仅是增加属性字段
					String fieldType = fieldValue.getClass().getName();
					CtField ctField = new CtField(pool.get(fieldType), fieldName, ctClass);
					ctField.setModifiers(Modifier.PUBLIC);
					ctClass.addField(ctField);
				}
				CtClassMap.put(className, c = ctClass.toClass());// 为创建的javassist类转换为java类
			}
		}

		Object newObject = c.newInstance();// 为创建java对象
		// 为创建的类newObject属性赋值
		Iterator it = fieldMap.entrySet().iterator();
		while (it.hasNext()) { // 遍历所有的属性
			Map.Entry entry = (Map.Entry) it.next();
			String fieldName = (String) entry.getKey();
			Object fieldValue = entry.getValue();
			// 为属性赋值
			this.setFieldValue(newObject, fieldName, fieldValue);
		}
		// ctClass.writeFile();
		// ctClass.detach();
		return newObject;
	}

	/**
	 * 
	 * 获取对象属性赋值
	 * 
	 * @param dObject
	 * @param fieldName
	 *            字段别名
	 * @return
	 */
	public Object getFieldValue(Object dObject, String fieldName) {

		Object result = null;
		try {
			Field fu = dObject.getClass().getDeclaredField(fieldName); // 获取对象的属性域
			try {
				fu.setAccessible(true); // 设置对象属性域的访问属性
				result = fu.get(dObject); // 获取对象属性域的属性值
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 给对象属性赋值
	 * 
	 * @param dObject
	 * @param fieldName
	 * @param val
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Object setFieldValue(Object dObject, String fieldName, Object val) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Object result = null;

			Field fu = dObject.getClass().getDeclaredField(fieldName); // 获取对象的属性域
				fu.setAccessible(true); // 设置对象属性域的访问属性
				fu.set(dObject, val); // 设置对象属性域的属性值
				result = fu.get(dObject); // 获取对象属性域的属性值
		return result;
	}

	public static void fillDataList(String classname, List<Object> listAll, ArrayList tablecols,RefObject<Integer> ref) {
		Map<String, Object> fieldMap = new HashMap<>();
		DynamicObjectCreator dco = new DynamicObjectCreator();
		try {

			for (Iterator<Map> iterator = tablecols.iterator(); iterator.hasNext();) {
				Map entryMap = (Map) iterator.next();
				for (Iterator iterator2 = entryMap.keySet().iterator(); iterator2.hasNext();) {
					// Map entryMap2 = (Map) iterator2.next();
					// for (Iterator iterator3 = entryMap2.keySet().iterator();
					// iterator3.hasNext();) {
					String m = (String) iterator2.next();
					fieldMap.put(m, entryMap.get(m));

					// }
					// dco.setFieldValue(s1, m, entryMap.get(m));// 创建对象s1赋值
				}
				Object obj = dco.addField(classname, fieldMap);
				listAll.add(obj);
			}
		} catch (Exception e) {
			if (ref.value<3) {
				if (e instanceof NoSuchFieldException) {
					dco.clearClassCache(classname);
					ref.value++;
					fillDataList(classname, listAll, tablecols,ref);
				} else {
					e.printStackTrace();
				}
			}else {
				e.printStackTrace();
			}
		}catch (Throwable e) {
				e.printStackTrace();
		}

		// JSONArray jsonArray = JSON.parseArray(tablecols);
		// jsonArray.forEach(o -> {
		// ((JSONObject)o).forEach((k,v)->{
		// fieldMap.put(k, v);
		// });
		// });

		// for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
		// JSONObject o = (JSONObject) iterator.next();
		// for (Iterator<String> iterator2 = o.keySet().iterator();
		// iterator2.hasNext();) {
		// String k = (String) iterator2.next();
		// fieldMap.put(k, o.get(k));
		// }
		// }
		//
		// try {
		// Object obj = dco.addField(tablename, fieldMap);
		// } catch (IllegalAccessException | InstantiationException | NotFoundException
		// | CannotCompileException
		// | IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// tabledatas.values().forEach(v->
		// listAll.add(e));
		// tabledatas.forEach((k,v)->
		//
		// );
		// string to Data
	}
}
