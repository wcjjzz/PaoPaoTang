package com.pop.manager;

import com.pop.controller.MusicPlayer;
import com.pop.element.ElementObj;
import com.pop.element.Mapobj;

import java.io.InputStream;
import java.util.*;

import javax.swing.ImageIcon;


/*
 * @加载器,负责从文件加载图片
 * @还负责生成相关的map类
 */
public class GameLoad {

	private static GameLoad elementLoader;
	
	private static ElementManager em=ElementManager.getManager();// 得到元素管理器对象

	public ElementObj[][] getMapBlock() {
		return mapBlock;
	}

	public void setMapBlock(ElementObj[][] mapBlock) {
		this.mapBlock = mapBlock;
	}

	private ElementObj  mapBlock[][]=new ElementObj[17][15];

	public static HashMap<String, ImageIcon> imgMap= new HashMap<String, ImageIcon>();

	//public static HashMap<String, MusicPlayer> soundMap = new HashMap<>();

	private Map<String, List<String>> gameInfoMap = new HashMap<>();//游戏信息字典

	private Map<String, ImageIcon> imageMap = new HashMap<>();//图片字典
	
	private static Properties pro=new Properties();
	
	
	//加载图片函数
	public static void imageLoad() {
		String imgpro="com/pop/text/Gamedata.pro";
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream imgs=classLoader.getResourceAsStream(imgpro);
		if (imgs==null) {
			System.out.println("图片配置文件读取错误");
		}
		try {
			pro.clear();
			pro.load(imgs);
			Set<Object> keys=pro.keySet();
			for (Object key:keys) {
				String url = pro.getProperty(key.toString());
				//存入image的map中
				imgMap.put(key.toString(), new ImageIcon(url));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("图片配置文件加载错误");
		}
		
	}

//	//加载声音函数
//	public static void soundLoad() {
//		String soundpro="com/pop/text/Soundata.pro";
//		ClassLoader classLoader=GameLoad.class.getClassLoader();
//		InputStream sounds=classLoader.getResourceAsStream(soundpro);
//		if (sounds==null) {
//			System.out.println("声音配置文件读取错误");
//		}
//		try {
//			pro.clear();
//			pro.load(sounds);
//			Set<Object> keys=pro.keySet();
//			for (Object key:keys) {
//				String url = pro.getProperty(key.toString());
//				//存入image的map中
//				soundMap.put(key.toString(), new MusicPlayer(url));
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("声音配置文件加载错误");
//		}
//
//	}

	//生成地图类函数，需要在这里对MapManager中的maplist赋值
	public static void MapLoad(int mapID) {

		String mapName="com/pop/text/"+mapID+".map";
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream mapStream=classLoader.getResourceAsStream(mapName);

		if (mapStream==null) {
			System.out.println("地图配置文件读取错误");
		}
		try {
			pro.clear();
			pro.load(mapStream);
			Enumeration<?> names=pro.propertyNames();
			while (names.hasMoreElements()) {
				String key = names.nextElement().toString();
				String [] arrsStrings=pro.getProperty(key).split(";");
				//这里的key是Breakable或者不可破坏的
				for (int i = 0; i < arrsStrings.length; i++) {//这里直接在map里面赋值
					String []arr= arrsStrings[i].split(",");//文件中，第一个是x第二个是y
					int y=(Integer.parseInt(arr[1]));
					int x=(Integer.parseInt(arr[0]));
					ElementObj mapElement= new Mapobj().build(key, arrsStrings[i]);
					MapManager.mapList[y][x]=mapElement;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("地图配置文件加载错误");
		}
	}

	//单例模式
	public static GameLoad getElementLoader() {
		if (elementLoader == null) {
			elementLoader = new GameLoad();
		}
		return elementLoader;
	}
	public Map<String, List<String>> getGameInfoMap() {
		return gameInfoMap;
	}
	public static HashMap<String, ImageIcon> getImgMap() {
		return imgMap;
	}
}

