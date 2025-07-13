package com.pop.element;

import com.pop.manager.GameLoad;
import com.pop.manager.MapManager;
import com.pop.manager.Mapblock;

import java.awt.*;


public class Mapobj extends ElementObj{

	private Mapblock types;
	
	
	
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(), this.getX()*45+ElementObj.final_x, this.getY()*45-21+ElementObj.final_y, this.getW(), this.getH(), null);
	}

	//对于泡泡堂的建筑，一个地图有可通过的不可通过的元素分类，因此在生成的时候只需要区分这两个即可，生成可使用随机也可以暂时只有一个，注意可通过可破坏的需要
	public ElementObj build(String Types,String string) {//小工厂,需要生成所有类内属性
		// TODO Auto-generated method stub
		setW(46);
		setH(68);
		//设置宽和高
		this.sethHit(45);
		this.setwHit(45);
		//第一个参数负责判断可通过与不可通过类型
		switch (Types) {
			case "Breakable1":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.Breakable1;setHp(1);
				break;
			case "Breakable2":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.Breakable2;setHp(1);
				break;
			case "Breakable3":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.Breakable3;setHp(2);
				break;
			case "Breakable4":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.Breakable4;setHp(1);
				break;
			case "Breakable5":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.Breakable5;setHp(1);
				setW(47);
				setH(52);
				break;
			case "Breakable6":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.Breakable6;setHp(2);
				setW(47);
				setH(49);
				break;
			case "Breakable7":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.Breakable7;setHp(1);
				setW(50);
				setH(67);
				break;
			case "Breakable8":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.Breakable8;setHp(1);
				setW(50);
				setH(76);
				break;
			case "Breakable9":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.Breakable9;setHp(1);
				setW(48);
				setH(74);
				break;
			case "UnBreakable1":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.UnBreakable1;setHp(999);
				break;
			case "UnBreakable2":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.UnBreakable2;setHp(999);
				break;
			case "UnBreakable3":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.UnBreakable3;setHp(999);
				break;
			case "UnBreakable4":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.UnBreakable4;setHp(999);
				break;
			case "UnBreakable5":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.UnBreakable5;setHp(999);
				setW(57);
				setH(58);
				break;
			case "UnBreakable6":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.UnBreakable6;setHp(999);
				setW(53);
				setH(79);
				break;
			case "UnBreakable7":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.UnBreakable7;setHp(999);
				setW(53);
				setH(62);
				break;

			case "mid1":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.mid1;setHp(999);
				setW(131);
				setH(142);
				this.sethHit(135);
				this.setwHit(135);
				break;
			case "mid2":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.mid2;setHp(999);
				setW(136);
				setH(135);
				this.sethHit(135);
				this.setwHit(135);
				break;
			case "mid3":
				setIcon(GameLoad.imgMap.get(Types.toString()));types=Mapblock.mid3;setHp(999);
				setW(135);
				setH(150);
				this.sethHit(135);
				this.setwHit(135);
				break;
			default:
				break;
		}
		//第二个参数是 下标 的形式，需要变换
		//文件中，第一个是x第二个是y，下标中，第一个是y第二个是x
		String []arr=string.split(",");
		this.setX(Integer.parseInt(arr[0]));
		this.setY(Integer.parseInt(arr[1]));
		return this;
	}

	/*
	 * @此函数用来判断障碍种类
	 * */
	public Mapblock getTypes() {
		return types;
	}

	@Override
	public void setIslive(boolean islive) {
		// TODO Auto-generated method stub
		this.setHp(getHp()-1);
		if(this.getHp() >0) {
			return;
		}
		super.setIslive(islive);
	}

	@Override
	public void die(){//建筑死亡
		//此处需要生成道具
		int max=15,min=1;//一半概率有道具
		int ran2 = (int) (Math.random()*(max-min)+min);
		if (ran2<9){//炸出来了
			PropEffect prop=null;
			if (ran2==1){//爱心
				prop= (PropEffect) new PropEffect().build(getX(),getY(),4);
			}else if(ran2<4){//2,3移动速度
				prop= (PropEffect) new PropEffect().build(getX(),getY(),2);
			}else if(ran2<6){//4,5炸弹威力
				prop= (PropEffect) new PropEffect().build(getX(),getY(),1);
			}else if (ran2<9) {//6,7,8炸弹个数
				prop= (PropEffect) new PropEffect().build(getX(),getY(),3);
			}
			MapManager.mapList[getY()][getX()]=prop;
		}else{//屁都没有
			MapManager.mapList[getY()][getX()]=null;
		}
	}
	@Override
	public Rectangle getRectangle() {
		Rectangle myRectangle=new Rectangle(getX()*45,getY()*45,gethHit(),getwHit());
		return myRectangle;
	}

}
