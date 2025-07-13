package com.pop.element;

import com.pop.manager.EffectTypes;
import com.pop.manager.GameLoad;
import com.pop.manager.MapManager;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

/*
* @炸弹效果类，专门处理炸弹效果
* */
public class BombEffect extends ElementObj {

	String types;
	int time;
	int stoptime=5;
	String fxstring;
	int showx=0;
	int showy=0;
	int showh=0;
	int showw=0;



	private void settime(int time) {
		this.time=time;
	}

	private HashMap<EffectTypes, List<ImageIcon>> EffectMap = new HashMap<>();

	@Override
	public void showElement(Graphics g) {// 动画展示
		// TODO Auto-generated method stub

		try {
			g.drawImage(this.getIcon().getImage(),  this.showx+ElementObj.final_x, this.showy+ElementObj.final_y,  this.showw,  this.showh, null);
		}catch (Exception e){
			//System.out.println(time);
		}
	}

	//产生动画
	public ElementObj build(int x, int y,int h, int w, String types,String fxstring) {

		settime(17);

		this.setX(x);
		this.setY(y);
		this.setH(h);
		this.setW(w);

		this.setwHit(45);
		this.sethHit(45);

		this.types=types;
		this.fxstring=fxstring;
		setIcon(GameLoad.imgMap.get(types+ (18 - time)));

		if (types=="center"){
			this.showy=getY()*MapManager.dxdy-15;
			this.showx=getX()*MapManager.dxdy-15;
			this.showw=80;
			this.showh=80;
		}else if (types=="end"){
			this.showw=45;
			this.showh=45;
			this.showy=getY()*MapManager.dxdy;
			this.showx=getX()*MapManager.dxdy+2;
		}else if (types=="middle"){

			this.showw=50;
			this.showh=45;
			switch (fxstring) {
				case "up":
					this.showy = getY() * MapManager.dxdy-5;
					this.showx = getX() * MapManager.dxdy;
					break;
				case "down":
					this.showy = getY() * MapManager.dxdy+5;
					this.showx = getX() * MapManager.dxdy;
					break;
				case "left":
					this.showy = getY() * MapManager.dxdy;
					this.showx = getX() * MapManager.dxdy-10;
					break;
				case "right":
					this.showy = getY() * MapManager.dxdy;
					this.showx = getX() * MapManager.dxdy+10;
					break;
				default:
					break;
			}
		}

		return this;
	}


	@Override
	public void model() {
		// TODO Auto-generated method stub
		behavior();
		updateImage();
	}

	@Override
	public void die() {//物体消失方法,这里是自我消失以外的另一种死亡可能性——触发消失
		if (types=="center"){//如果是中心爆炸效果，不进行消失,而是运行动画
			//System.out.println(getY()+" "+getX()+"不消失"+time);
			behavior();
			return;
		}
		MapManager.mapList[getY()][getX()]=null;//毁灭操作
	}

	@Override
	protected void behavior() {// 特殊动画函数
		// TODO Auto-generated method stub
		//注意这里的动画需要因为上下来进行
		if (time > 0) {
			/*
			if (time>=11&&time<=15&&stoptime>=0) {//循环节，在中间的爆炸特效短暂的重复一会
				if (types == "center") {
					setIcon(GameLoad.imgMap.get(types + (18 - time)));//middle 1 up
				} else {
					setIcon(GameLoad.imgMap.get(types + (18 - time) + fxstring));//middle 1 up
				}
				if (time==11){
					time=15;
					stoptime--;
				}
				time--;
			}else{*/
				if (types == "center") {
					setIcon(GameLoad.imgMap.get(types + (18 - time)));//middle 1 up
				} else {
					setIcon(GameLoad.imgMap.get(types + (18 - time) + fxstring));//middle 1 up
				}
				time--;
			//}
		}else{
			MapManager.mapList[getY()][getX()]=null;//毁灭操作，将数组对应位置置为空
		}
	}

}
