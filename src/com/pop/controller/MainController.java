package com.pop.controller;

import com.pop.element.ElementObj;
import com.pop.element.Play;
import com.pop.manager.ElementManager;
import com.pop.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import java.util.List;
import java.util.Set;



/*
 * @监听主线程类
 * @只做到监听和发送消息即可
 * 
 * */
public class MainController implements KeyListener {

	// 添加元素管理器
	private ElementManager em;

	/*能否通过一个集合来记录所有按下的键，如果重复触发，就直接结束
	 * 同时，第1次按下，记录到集合中，第2次判定集合中否有。
	 *       松开就直接删除集合中的记录。
	 * set集合
	 * */
	private Set<Integer> set=new HashSet<Integer>();
	// controller的构建函数
	public MainController() {
		super();
		em = ElementManager.getManager();// 得到元素管理器对象
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key=e.getKeyCode();
		if(set.contains(key)) { //判定集合中是否已经存在,包含这个对象
//			如果包含直接结束方法
			return;
		}
		set.add(key);
		List<ElementObj> plays = em.getElementsByKey(GameElement.PLAY);
		for (int i = 0; i < plays.size(); i++) {
			Play obj = (Play) plays.get(i);
			obj.keyClick(true, e.getKeyCode());// 调用play的内部key方法提示所有玩家
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(!set.contains(e.getKeyCode())) {//如果这个不存在，就停止
			return;
		}//存在(已经按过这个案件)
		set.remove(e.getKeyCode());//移除数据
		List<ElementObj> plays = em.getElementsByKey(GameElement.PLAY);
		for (int i = 0; i < plays.size(); i++) {
			Play obj = (Play) plays.get(i);
			obj.keyClick(false, e.getKeyCode());// 调用play的内部key方法提示所有玩家
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
