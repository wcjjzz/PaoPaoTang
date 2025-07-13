package com.pop.show;

import com.pop.controller.GamemainThread;
import com.pop.element.ElementObj;
import com.pop.manager.ElementManager;
import com.pop.manager.GameElement;
import com.pop.manager.MapManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;


import javax.swing.*;


/**
 * @说明 游戏的主要面板
 * @功能说明 主要进行元素的显示，同时进行界面的刷新(多线程)
 *
 */
public class GameMainJPanel extends ShowObj{

	private static boolean isLose;
	private Thread thead=null;  //游戏主线程

	private ElementManager em;
	public static int w=767;
	public static int h=677;
	int Level;
	boolean onePlay;
	public GameMainJPanel(boolean onePlay) {
		init(onePlay);
	}

	public void init(boolean onePlay) {
//		this.setLayout(null);
//		JButton pause = new JButton();//双人玩家按钮
//		pause.setIcon(new ImageIcon("image/bg/pause.png"));
////		pause.setBounds(23, 55+110*5+35, 136, 42);
//		pause.setBounds(30,55+120*5+30, 136, 42);
//
//		this.add(pause);
//		this.setVisible(true);
		int max=4,min=1;
		int ran = (int) (Math.random()*(max-min)+min);
		this.Level=ran;
		this.onePlay=onePlay;
		thead=new GamemainThread(ran,onePlay,this);
		if(thead !=null) {
			thead.start();//启动线程
		}
		em = ElementManager.getManager();//得到元素管理器对象

	}
	
	@Override  //用于绘画的    Graphics 画笔 专门用于绘画的，在此处将每次监听器层的反应反馈到这里，
	//需要提供上面要求的执行函数。实际是监听器层和类层之间的中介
	//类层包括人物和动画等，动态动画会自动播放
	public void paint(Graphics g) {
		super.paint(g);
		int w=1000;
		int h=830;
		GameJFrame.getGameJFrame().setSize(w,h);//更改面板大小
		Map<GameElement, List<ElementObj>> all = em.getGameElements();

		//此处以下是背景图片的设定，可以改变
		ImageIcon icon = new ImageIcon("image/mapbackground/"+Level+".png");
		g.drawImage(icon.getImage(), 195, 60,GameMainJPanel.w,GameMainJPanel.h,this);//此处进行缩放,注意获取的宽度和高度是游戏mianjpanel的而不是整个窗体

		//注意，由于分开了玩家类和其他元素，因此需要在此两个都显示

		//使用二维数组循环显示

		for (int dy = 0; dy < 15; dy++) { // 迭代器
			for (int dx = 0; dx < 17; dx++) {
				if(MapManager.mapList[dy][dx]==null) {//判断空值
					continue;
				}
				MapManager.mapList[dy][dx].showElement(g);// 调用每个类的显示
			}
		}
		ImageIcon imageIcon = new ImageIcon("image/bg/biankuang.png");//边框
		g.drawImage(imageIcon.getImage(), 0,0,w,h, null);

//		人物单元图片大小140 105
		ImageIcon play1  = new ImageIcon("image/bg/1.png");//玩家状态显示
		g.drawImage(play1.getImage(), 30,55,140,105, null);

		ImageIcon play2  = new ImageIcon("image/bg/2.png");//玩家状态显示
		g.drawImage(play2.getImage(), 30,55+120,140,105, null);

		ImageIcon play3  = new ImageIcon("image/bg/3.png");//玩家状态显示
		g.drawImage(play3.getImage(), 30,55+120*2,140,105, null);

		ImageIcon play4  = new ImageIcon("image/bg/4.png");//玩家状态显示
		g.drawImage(play4.getImage(), 30,55+120*3,140,105, null);

		ImageIcon level  = new ImageIcon("image/bg/level.png");//level状态显示
		g.drawImage(level.getImage(), 55,55+120*4-5,99,34, null);

		ImageIcon kuang1  = new ImageIcon("image/bg/kuang.png");//level框状态显示
		g.drawImage(kuang1.getImage(), 30,55+120*4+30,140,34, null);

		ImageIcon time  = new ImageIcon("image/bg/time.png");//time状态显示
		g.drawImage(time.getImage(), 55,55+120*5-55,99,34, null);

		ImageIcon kuang2  = new ImageIcon("image/bg/kuang.png");//time框状态显示
		g.drawImage(kuang2.getImage(), 30,55+120*5-20,140,34, null);

		ImageIcon pause  = new ImageIcon("image/bg/pause.png");//time框状态显示
		g.drawImage(pause.getImage(), 30,55+120*5+30,136,42, null);

		g.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g.setColor(new Color(30,34,85));
		int allTime = GamemainThread.getAllTime()/1000;
		int munite = allTime / 60;
		int second = allTime % 60;
		String m;
		String s;
		if(munite < 10)
			m = "0" + Integer.toString(munite);
		else
			m = Integer.toString(munite);
		if(second<10)
			s = "0" + Integer.toString(second);
		else
			s = Integer.toString(second);
		g.drawString(m + ":" + s,65,55+120*5+6);
		g.drawString("1 / 5",70,55+120*4+55);

//		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
//		g.setColor(new Color(51,98,55));//绿
//		g.drawString("1",145,55+120*3+20);
//		g.drawString("1",145,55+120*3+45);
//		g.drawString("1",145,55+120*3+70);
//		g.drawString("1",145,55+120*3+95);
//		g.drawString("10",80,55+120*3+95);
//
//		g.setColor(new Color(96,61,28));//黄
//		g.drawString("1",145,55+120*2+20);
//		g.drawString("1",145,55+120*2+45);
//		g.drawString("1",145,55+120*2+70);
//		g.drawString("1",145,55+120*2+95);
//		g.drawString("10",80,55+120*2+95);

		//循环显示玩家
		for(GameElement ge:GameElement.values()) { //迭代器
			List<ElementObj> list = all.get(ge);
			for(int i=0;i<list.size();i++) {
				ElementObj obj=list.get(i);
				obj.showElement(g);//调用玩家的自己的show方法完成自己的显示
			}
		}
	}

	@Override
	protected void go() {

	}

	public void win() {//成功函数
		GameJFrame.getGameJFrame().remove(this);isLose = false;
		GameJFrame.getGameJFrame().setjPanel(new GameOverJPanel());
	}
	public void lose() {//失败函数
		GameJFrame.getGameJFrame().remove(this);isLose = true;
		GameJFrame.getGameJFrame().setjPanel(new GameOverJPanel());
	}

	public static boolean isLose() {
		return isLose;
	}

}











