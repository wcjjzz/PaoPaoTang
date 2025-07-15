package com.pop.element;

import com.pop.controller.MusicPlayer;
import com.pop.manager.GameLoad;
import com.pop.manager.MapManager;

import java.awt.*;

/*
* @道具
* */
public class PropEffect extends ElementObj {

	int types;
	int propIndex =1;

	//用于计时泡泡包围的时间
    private long startTime = 0;
    private long totalElapsed = 0;
    private boolean isFirstEntry = true;
    
    
	//展示道具速度控制变量
	private int aniCount = 0;
	private final int ANI_DELAY = 20;
	
	//用于存储玩家类的一个实例
	Play Play1 = null;
	
	
	//获取道具类型的getset
	public int getTypes() {
		return types;
	}
	public void setTypes(int types) {
		this.types = types;
	}
	
	
	@Override
	public void showElement(Graphics g) {//展示道具函数
		try {
			g.drawImage(this.getIcon().getImage(), this.getX()* MapManager.dxdy+ElementObj.final_x, this.getY()* MapManager.dxdy-21+ElementObj.final_y, this.getW(), this.getH(), null);
		}catch (Exception e){
	//		System.out.println("出错了");
		}
	}
	//prop内的move方法被改为动画方法，只作为动画操作
	//道具的动画有4帧
	public void move() {
		if(aniCount >= ANI_DELAY) {
			aniCount = 0;
			if(propIndex <4){
				propIndex++;//小于4就自增
			}else{//当等于或者大于4时进行置1
				propIndex =1;
			}
		}else {
			aniCount++;
		}
		setIcon(GameLoad.imgMap.get(types+"prop"+ propIndex));
		//6是被泡泡困住的效果实现
		if(types == 6) {
			boolean judege = timeRecord();
			if(judege) {
				die();
				Play1.setBubbleWarp(false);
			}else {
				if(aniCount >= ANI_DELAY) {
					aniCount = 0;
					if(propIndex <4){
						propIndex++;//小于4就自增
					}else{//当等于或者大于4时进行置1
						propIndex =1;
					}
				}else {
					aniCount++;
				}
				setIcon(GameLoad.imgMap.get(types+"prop"+ propIndex));
			}
		}

	}
	//此函数接收进来的是单位化xy
	public ElementObj build(int x,int y,int types) { 

		setH(64);
		setW(42);
		setX(x);
		setY(y);
		sethHit(45);
		setwHit(45);
		this.types=types;
		setIcon(GameLoad.imgMap.get(types+"prop"+ propIndex));
		return this;
	}
	//重写一个build使得玩家类创建该类的时候能把自己给传入创建的这个类里
	public ElementObj build(int x,int y,int types,Play play1) { 
		setH(64);
		setW(42);
		setX(x);
		setY(y);
		sethHit(45);
		setwHit(45);
		this.types=types;
		this.Play1 = play1;
		setIcon(GameLoad.imgMap.get(types+"prop"+ propIndex));
		return this;
	}

	/*
	 * 记录时间函数
	 * */
	public boolean timeRecord() {
		if (isFirstEntry) {
            // 第一次进入：记录开始时间
            startTime = System.currentTimeMillis();
            isFirstEntry = false;
        } else {
            // 后续进入：计算距离上次退出的时间差
            long currentTime = System.currentTimeMillis();
            long gap = currentTime - startTime;
            totalElapsed += gap;
            startTime = currentTime;  // 更新开始时间
        }
		if(totalElapsed >= 2000) {
			totalElapsed = 0;
			isFirstEntry = true;
			return true;
		}else {
			return false;
		}
	}
	
	
	public void get(Play play){//此函数让玩家捡起道具

	//	System.out.println("prop");
		MusicPlayer get=new MusicPlayer("sound/eatProp.wav",false);
		get.start();

		//下面通过types产生不同效果
		switch (types) {
			case 1://bombpower
				play.setBombPower(play.getBombPower()+1);
				break;
			case 2://fatser
				play.setV(play.getV()+1);
				break;
			case 3://morebomb
				play.setBombNum(play.getBombNum()+1);
				break;
			case 4://morelife
				play.setHp(play.getHp()+1);
				break;
			case 5://BubbleWarp
				die();//这里得先让5死了才能把6加进去
				play.BubbleWrap(getX(),getY());
				break;
			default:
				break;
		}

	}

	public Rectangle getRectangle() {//这里的碰撞格是需要元素自己改变的

		Rectangle myRectangle=new Rectangle(getX()*45,getY()*45,gethHit(),getwHit());
		return myRectangle;
	}

	public void die() {//死亡方法，此方法只有在道具被炸弹破坏的情况下才会被使用
		MapManager.mapList[getY()][getX()]=null;
	}
}
