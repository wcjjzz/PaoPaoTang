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

	@Override
	public void showElement(Graphics g) {//展示道具函数
		try {
			g.drawImage(this.getIcon().getImage(), this.getX()* MapManager.dxdy+ElementObj.final_x, this.getY()* MapManager.dxdy-21+ElementObj.final_y, this.getW(), this.getH(), null);
		}catch (Exception e){
	//		System.out.println("出错了");
		}
	}
	//prop内的move方法被改为动画方法，只作为动画操作
	//道具的动画有51帧
	public void move() {

		if(propIndex <51){
			propIndex++;//小于51就自增
		}else{//当等于或者大于51时进行置1
			propIndex =1;
		}
		setIcon(GameLoad.imgMap.get(types+"prop"+ propIndex));

	}
	//此函数接收进来的是单位化xy
	public ElementObj build(int x,int y,int types) { ;

		setH(66);
		setW(51);
		setX(x);
		setY(y);
		sethHit(45);
		setwHit(45);
		this.types=types;
		setIcon(GameLoad.imgMap.get(types+"prop"+ propIndex));
		return this;
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
