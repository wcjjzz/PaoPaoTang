package com.pop.element;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


/**
 * @说明 所有元素的基类。
 *
 */
public abstract class ElementObj {
	public static int final_x = 195;
	public static int final_y = 60;
	private int x;
	private int y;
	private int w;
	private int h;
	private ImageIcon icon;
	private int wHit;//实际碰撞宽度
	private int hHit;//实际碰撞高度

	public void sethHit(int hHit) {
		this.hHit = hHit;
	}

	public int gethHit() {
		return hHit;
	}

	public void setwHit(int wHit) {
		this.wHit = wHit;
	}

	public int getwHit() {
		return wHit;
	}

	//	还有。。。。 各种必要的状态值，例如：是否生存.hp等
	private boolean islive=true;
	private int hp=1;
	
	public ElementObj() {	//这个构造其实没有作用，只是为继承的时候不报错写的	
	}
	/**
	 * @说明 带参数的构造方法; 可以由子类传输数据到父类
	 * @param x    左上角X坐标，玩家类以外的类中xy的含义都是网格中的xy
	 * @param y    左上角y坐标，玩家类以外的类中xy的含义都是网格中的xy
	 * @param w    w宽度
	 * @param h    h高度
	 * @param icon  图片
	 */
	public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.icon = icon;
		
	}
	/**
	 * @说明 抽象方法，显示元素
	 * @param g  画笔 用于进行绘画
	 */
	public abstract void showElement(Graphics g);
	
	
	protected void updateImage() {//更新表现图片函数
		
	} 
	protected void move() {//移动函数
		
	}
	protected void behavior() {//特殊行为方法，不同类有不同实现，
		
	}
	public void die() {//物体消失方法
		
	}
	
	public void model(){
		updateImage();
		move();
		behavior();
	}
	

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public boolean getislive() {
		return islive;
	}
	public void setIslive(boolean islive) {
		this.islive = islive;
	}
	
	public Rectangle getRectangle() {//这里的碰撞格是需要元素自己改变的
		Rectangle myRectangle=new Rectangle(getX()*45,getY()*45,gethHit(),getwHit());
		return myRectangle;
	}
	
	public boolean pk(ElementObj obj) {
		return this.getRectangle().intersects(obj.getRectangle());
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
}










