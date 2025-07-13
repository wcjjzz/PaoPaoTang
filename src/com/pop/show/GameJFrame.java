package com.pop.show;

import com.pop.manager.GameLoad;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;




/**
 * @说明 游戏窗体 主要实现的功能：关闭，显示，最大最小化
 *  1.面板绑定到窗体
 *       2.监听绑定//绑定到controller
 *       3.游戏主线程启动
 *       4.显示窗体
 */
public class GameJFrame extends JFrame{
	public static int GameX =  780;//GAMEX
	public static int GameY =  715;
	private int i=0;
	private JPanel jPanel =new JPanel(); //大面板
	private KeyListener  keyListener=null;//键盘监听
	private MouseMotionListener mouseMotionListener=null; //鼠标监听
	private MouseListener mouseListener=null;

	private static CardLayout cLayout=new CardLayout();
	private ShowObj jPanel_Child = null;//子面板
	private static GameJFrame gj=null;
	public static synchronized GameJFrame getGameJFrame() {
		if(gj == null) {//控制判定

			gj=new GameJFrame();
			gj.jPanel.setLayout(cLayout);
		}
		return gj;
	}

	public GameJFrame() {
		init();
	}
	public void init() {

		this.setSize(GameX, GameY); //设置窗体大小
		this.setTitle("Q版泡泡堂");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置退出并且关闭
		this.setLocationRelativeTo(null);//屏幕居中显示
		// 缓存所有图片
		GameLoad.imageLoad();
	}
	/*窗体布局: 可以讲 存档，读档。button   给大家扩展的*/
	public void addButton() {
//		this.setLayout(manager);//布局格式，可以添加控件
	}	
	/**
	 * 启动方法
	 */
	public void start() {
		if(jPanel!=null) {
			this.add(jPanel);
		}
		if(keyListener !=null) {
			this.addKeyListener(keyListener);
		}

//		界面的刷新和监听

				
		this.setVisible(true);//显示界面
		
		while (true) {	
			jPanel.repaint();
			jPanel_Child.model();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/*set注入：等大家学习ssm 通过set方法注入配置文件中读取的数据;讲配置文件
	 * 中的数据赋值为类的属性
	 * 构造注入：需要配合构造方法
	 * spring 中ioc 进行对象的自动生成，管理。
	 * */
	public void setjPanel(ShowObj jp) {
		jPanel_Child=jp;
		this.jPanel.add(jp,Integer.toString(i));
		cLayout.show(this.jPanel,Integer.toString(i++));

		this.setVisible(false);
		this.setVisible(true);
	}
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}
	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}
	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}
	/*public void setThead(Thread thead) {
		this.thead = thead;
	}*/

}





