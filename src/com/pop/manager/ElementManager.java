package com.pop.manager;


import com.pop.element.ElementObj;
import com.pop.element.Enemy;
import com.pop.element.Play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @说明 本类是元素管理器，专门存储所有的元素，同时，提供方法
 * 		给予视图和控制获取数据
 */
public class ElementManager {
	/*
	 * String 作为key 匹配所有的元素  play -> List<Object> listPlay
	 *                             enemy ->List<Object> listEnemy 
	 * 枚举类型，当做map的key用来区分不一样的资源，用于获取资源
	 * List中元素的泛型 应该是 元素 基类
	 * 所有元素都可以存放到 map集合中，显示模块只需要获取到 这个map就可以
	 * 显示是有的界面需要显示的元素(调用元素基类的 showElement())
	 */
	private Map<GameElement,List<ElementObj>> gameElements;
//	本方法一定不够用
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
//	添加元素(多半由加载器调用)
	public void addElement(ElementObj obj,GameElement ge) {
//		List<ElementObj> list = gameElements.get(ge);
//		list.add(obj);
		gameElements.get(ge).add(obj);//添加对象到集合中，按key值就行存储
	}
//	依据key返回 list集合，取出某一类元素
	public List<ElementObj> getElementsByKey(GameElement ge){
		return gameElements.get(ge);
	}	

	private static ElementManager EM=null; //引用
//	synchronized线程锁->保证本方法执行中只有一个线程
	public static synchronized ElementManager getManager() {
		if(EM == null) {//控制判定
			EM=new ElementManager();
		}
		return EM;
	}
	private ElementManager() {//私有化构造方法
		init(); //实例化方法
	}


	public void init() {//实例化在这里完成
//		hashMap hash散列
		gameElements=new HashMap<GameElement,List<ElementObj>>();
//		将每种元素集合都放入到 map中
		for(GameElement ge:GameElement.values()) { //迭代器
			gameElements.put(ge, new ArrayList<ElementObj>());
		}

	}

	public static void refresh() {
		Map<GameElement, List<ElementObj>> all = ElementManager.getManager().getGameElements();

		for (GameElement ge : GameElement.values()) { // 迭代器
			List<ElementObj> list = all.get(ge);
			for (int i = 0; i < list.size(); i++) {
				list.remove(i--);
			}
		}
	}

}







