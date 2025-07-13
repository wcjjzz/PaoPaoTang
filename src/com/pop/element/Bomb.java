package com.pop.element;


import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.pop.controller.MusicPlayer;
import com.pop.manager.*;
import com.pop.show.GameMainJPanel;


/*
* @炸弹类，由玩家生成的炸弹类产生
* @bomb类内有一个变量 “倒数”，在 “倒数” 结束的时候将会按一个类内部变量 “威力” 产生横向竖向爆炸特效，并且自己会原地消失
* 注意，除了玩家类以外的类中xy的含义都是网格中的xy
* */
public class Bomb extends ElementObj {


	Play host=null;//它的主人

	int power=1;
	int time=4;//炸弹倒数
	int bombIndex=1;
	ImageIcon a=GameLoad.imgMap.get("bomb1");

	@Override
	public void showElement(Graphics g) {//展示炸弹函数
		// TODO Auto-generated method stub
		//注意，由于炸弹图片比网格大，因此需要再把炸弹向上移动一个差值 geth-dxdy=66-45=21，我们的目标是底部对齐其所在的网格
		g.drawImage(this.getIcon().getImage(), this.getX()* MapManager.dxdy+ElementObj.final_x, this.getY()* MapManager.dxdy-21+ElementObj.final_y, this.getW(), this.getH(), null);
	}
	//bomb内的move方法被改为动画方法，只作为动画操作
	//炸弹爆炸动画有27帧
	public void move() {

		if(bombIndex<27){
			bombIndex++;//小于27就自增
		}else{//当等于或者大于27时进行置1,并且泡泡倒数减一
			bombIndex=1;
			time=time-1;
		}
		setIcon(GameLoad.imgMap.get("bomb"+bombIndex));
		if (time<=0) {
			setIslive(false);
		}
	}
	//此函数接收进来的是单位化xy，炸弹类中的xy将就是这个
	public ElementObj build(int x,int y,int bombPower,Play host) {

		this.host=host;
		this.power=bombPower;

		setH(66);
		setW(51);
		setX(x);
		setY(y);
		sethHit(45);
		setwHit(45);
		//System.out.print(getX()+"  "+getY()+"\n");

		setIcon(a);
		return this;
	}

	public void die() {//死亡方法,向二维数组内添加一个对象,这个对象将会播放动画(因人而异)，注意，这个死亡会由动画触发或者是倒计时结束触发
		// TODO Auto-generated method stub
		//System.out.print(" bomb "+ host.bombNow+"\n");
		int now=host.getBombNow();
		now=now-1;
		host.setBombNow(now);//炸弹计数减少

		MusicPlayer bomb=new MusicPlayer("sound/bomb.wav",false);
		bomb.start();


		//下面生成中心爆炸特效
		ElementObj bombEffect=new BombEffect().build (getX(), getY(),59,59,"center","center");
		MapManager.mapList[getY()][getX()]=bombEffect;

		//下面需要依照炸弹威力，对二维数组中的   x-power x+power y-power y+power  各段依次进行障碍物查看
		see( true,getY(),getX());
		see( false,getY(),getX());

	}

	//此函数为功能函数，辅助die函数产生特效
	private void see(Boolean yfixedOrnot,int yIndex,int xIndex){
		if (yfixedOrnot){//固定y
			//注意末尾特效和中间特效不同，因此循环需要缩短一个单位，最后判断一次末尾特效

			//注意使用扩散式判断
			boolean stop=false;//是否受到阻碍判断变量，用于判断是否需要添加末尾特效
			int dx;
			//System.out.println(power);
			for (dx = xIndex+1; dx <= xIndex+power-1; dx++) {//向右判断
				if(MapManager.mapList[yIndex][dx]==null) {//判断空值
					//生成特效
					ElementObj bombEffect=new BombEffect().build (dx, yIndex,41,37,"middle","right");
					MapManager.mapList[yIndex][dx]=bombEffect;
				}else{
					if (!(MapManager.mapList[yIndex][dx] instanceof BombEffect)){//如果碰到炸弹特效就不需要要置空了
						MapManager.mapList[yIndex][dx].setIslive(false);
					}
					stop=true;
					break;
				}
			}

			//添加末尾特效,在stop为真的情况下不需要添加,如果没有被阻碍或者是唯里太小没有走上面的循环，一样会这样
			//注意末尾也会导致破坏，需要在内部进行判断
			if (!stop) {
				if(MapManager.mapList[yIndex][dx]==null){
					ElementObj bombEffect = new BombEffect().build(dx, yIndex, 31, 37, "end", "right");
					MapManager.mapList[yIndex][dx] = bombEffect;
				}else{
					if (!(MapManager.mapList[yIndex][dx] instanceof BombEffect)){//如果碰到炸弹特效就不需要要置空了
						MapManager.mapList[yIndex][dx].setIslive(false);
					}
				}
			}

			dx = xIndex-1;
			stop=false;

			for (; dx >= xIndex-power+1; dx--) {//向左判断
				if(MapManager.mapList[yIndex][dx]==null) {//判断空值
					//生成特效
					ElementObj bombEffect=new BombEffect().build (dx, yIndex,41,37,"middle","left");
					MapManager.mapList[yIndex][dx]=bombEffect;
				}else{
					if (!(MapManager.mapList[yIndex][dx] instanceof BombEffect)){//如果碰到炸弹特效就不需要要置空了
						MapManager.mapList[yIndex][dx].setIslive(false);
					}
					stop=true;
					break;
				}
			}
			if (!stop){//添加末尾特效
				if(MapManager.mapList[yIndex][dx]==null) {
				ElementObj bombEffect=new BombEffect().build (dx, yIndex,31,37,"end","left");
				MapManager.mapList[yIndex][dx]=bombEffect;
				}else{
					if (!(MapManager.mapList[yIndex][dx] instanceof BombEffect)){//如果碰到炸弹特效就不需要要置空了
						MapManager.mapList[yIndex][dx].setIslive(false);
					}
				}
			}

		}else{//固定x
			boolean stop=false;//是否受到阻碍判断变量，用于判断是否需要添加末尾特效
			int dy;
			for (dy = yIndex+1; dy <= yIndex+power-1; dy++) {//向下判断
				if(MapManager.mapList[dy][xIndex]==null) {//判断空值
					//生成特效
					ElementObj bombEffect=new BombEffect().build (xIndex, dy,41,37,"middle","down");
					MapManager.mapList[dy][xIndex]=bombEffect;
				}else{
					if (!(MapManager.mapList[dy][xIndex] instanceof BombEffect)){//如果碰到炸弹特效就不需要要置空了
						MapManager.mapList[dy][xIndex].setIslive(false);
					}
					stop=true;
					break;
				}
			}
			if (!stop){//添加末尾特效
				if(MapManager.mapList[dy][xIndex]==null){
					ElementObj bombEffect=new BombEffect().build (xIndex,dy,31,37,"end","down");
					MapManager.mapList[dy][xIndex]=bombEffect;
				}else{
					if (!(MapManager.mapList[dy][xIndex] instanceof BombEffect)){//如果碰到炸弹特效就不需要要置空了
						MapManager.mapList[dy][xIndex].setIslive(false);
					}

				}

			}

			dy = yIndex-1;
			stop=false;

			for (; dy >= yIndex-power+1; dy--) {//向上判断
				if(MapManager.mapList[dy][xIndex]==null) {//判断空值
					//生成特效
					ElementObj bombEffect=new BombEffect().build (xIndex, dy,41,37,"middle","up");
					MapManager.mapList[dy][xIndex]=bombEffect;
				}else{
					if (!(MapManager.mapList[dy][xIndex] instanceof BombEffect)){//如果碰到炸弹特效就不需要要置空了
						MapManager.mapList[dy][xIndex].setIslive(false);
					}
					stop=true;
					break;
				}
			}
			if (!stop){//添加末尾特效
				if(MapManager.mapList[dy][xIndex]==null) {
				ElementObj bombEffect=new BombEffect().build (xIndex, dy,31,37,"end","up");
				MapManager.mapList[dy][xIndex]=bombEffect;
				} else{
					if (!(MapManager.mapList[dy][xIndex] instanceof BombEffect)){//如果碰到炸弹特效就不需要要置空了
						MapManager.mapList[dy][xIndex].setIslive(false);
					}
				}
			}

		}

	}
}
