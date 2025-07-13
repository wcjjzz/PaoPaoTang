package com.pop.element;

import java.awt.*;

import javax.swing.ImageIcon;


import com.pop.controller.MusicPlayer;
import com.pop.manager.*;
import com.pop.show.GameMainJPanel;

import static java.lang.Thread.sleep;


/*
* @此类是玩家类，内部有玩家各种方法（放置炸弹等）
* 注意，发现传入的icon的宽不正确，为-1
* */
public class Play extends ElementObj {

	boolean upBoolean = false;
	boolean downBoolean = false;
	boolean leftBoolean = false;
	boolean rightBoolean = false;

	boolean fireNow = false;//开火状态

	boolean dieAndLive= true;//可能死亡状态，用于播放死亡动画，true活flase死

	public String getFxString() {
		return fxString;
	}

	//面对方向和imageIndex一起组成了最终的icon图片显示
	protected String fxString = "up";
	int imageIndex =1;
	int deadIndex  =1;//死亡动画

	protected String playNumber = "1";//是几号玩家,具有set，get方法


	int bombNum =1;// 能够放置炸弹的最大数目
	int bombNow =0;// 炸弹目前的数目
	int bombPower =1;//炸弹威力
	int v = 3;// 速度
	int beginx;
	int beginy;

	int score=10;//分数


	public Play(int x, int y, String playNumber, ImageIcon icon) {
		this.setX(x);
		this.setY(y);
		this.setPlayNumber(playNumber);
		this.setIcon(icon);
		this.sethHit(20);
		this.setwHit(20);
		this.beginx=x;
		this.beginy=y;
	}

	//以下是所有的set，get方法

	public boolean isDieAndLive() {
		return dieAndLive;
	}

	public void setDieAndLive(boolean dieAndLive) {
		setW(55);
		this.dieAndLive = dieAndLive;
	}

	public String getPlayNumber() {
		return playNumber;
	}

	public void setPlayNumber(String playNumber) {
		this.playNumber = playNumber;
	}

	public int getBombNum() {
		return bombNum;
	}

	public void setBombNum(int bombNum) {
		this.bombNum = bombNum;
	}

	public int getBombNow() {
		return bombNow;
	}

	public void setBombNow(int bombNow) {
		this.bombNow = bombNow;
	}

	public int getBombPower() {
		return bombPower;
	}

	public void setBombPower(int bombPower) {
		if (bombPower<5){
			this.bombPower = bombPower;
		}
	}

	public void setV(int v) {
		if (this.v<5){
			this.v = v;
		}
	}
	public int getV() { return v; }
	//以下将是类内主要的逻辑函数
	/*
	 * model()整个类运行的主要函数，游戏内play类将会依照这个主函数执行整个顺序
	 * */
	@Override
	public void model() {
		// TODO Auto-generated method stub
		updateImage();
		move();
		behavior();
	}

	/*
	* 绘画方法
	* */
	@Override
	public void showElement(Graphics g) {
		try {
			g.drawImage(this.getIcon().getImage(), this.getX()+ElementObj.final_x, this.getY()+ElementObj.final_y, this.getW(), this.getH(), null);
//			if(playNumber.equals("1")){
//				g.setFont(new Font("Times New Roman", Font.BOLD, 20));
//				g.setColor(new Color(48,86,118));//蓝色
//				g.drawString(getHp()+"",145,55+120*0+20);//生命值
//				g.drawString(bombNum+"",145,55+120*0+45);//炸弹数
//				g.drawString(bombPower+"",145,55+120*0+70);//药水
//				g.drawString(v+"",145,55+120*0+95);//速度
//				g.drawString("10",80,55+120*0+95);//分数
//
//				g.setFont(new Font("Times New Roman", Font.BOLD, 20));
//				g.setColor(new Color(48,86,118));//蓝色
//				g.drawString("1",145,55+120*1+20);//生命值
//				g.drawString("1",145,55+120*1+20);//生命值
//				g.drawString("1",145,55+120*1+45);//炸弹数
//				g.drawString("1",145,55+120*1+70);//药水
//				g.drawString("1",145,55+120*1+95);//速度
//				g.drawString("10",80,55+120*1+95);//分数
//			}
//			if(playNumber.equals("2")){
				g.setFont(new Font("Times New Roman", Font.BOLD, 20));
				g.setColor(new Color(48,86,118));//蓝色
				g.drawString(getHp()+"",145,55+120*(Integer.parseInt(playNumber)-1)+20);//生命值
				g.drawString(bombNum+"",145,55+120*(Integer.parseInt(playNumber)-1)+45);//炸弹数
				g.drawString(bombPower+"",145,55+120*(Integer.parseInt(playNumber)-1)+70);//药水
				g.drawString(v+"",145,55+120*(Integer.parseInt(playNumber)-1)+95);//速度
				g.drawString(getScore()+"",80,55+120*(Integer.parseInt(playNumber)-1)+95);//分数
//			}
		}catch (Exception e){
			System.out.println(playNumber+" "+fxString+" "+imageIndex);
		}
	}

	// 提醒函数
	public void keyClick(boolean puss, int dis) {
		//System.out.println(dis);
		if (puss) {
			if (this.playNumber.equals("1")) {
				switch (dis) {
				case 38:
					this.downBoolean=false;this.upBoolean=true;
					this.rightBoolean =false;this.leftBoolean=false;
					fxString = "up";
					break;
				case 40:
					this.rightBoolean =false;this.leftBoolean=false;
					this.downBoolean=true; this.upBoolean=false;
					fxString = "down";
					break;
				case 37:
					this.downBoolean=false;this.upBoolean=false;
					this.leftBoolean=true; this.rightBoolean =false;
					fxString = "left";
					break;
				case 39:
					this.rightBoolean =true;this.leftBoolean=false;
					this.upBoolean=false; this.downBoolean=false;
					fxString = "right";
					break;
				case 10:
					fireNow = true;
					break;
				default:
					break;
				}
			}
			else
			{
				switch (dis) {
					case 87:
						this.downBoolean=false;this.upBoolean=true;
						this.rightBoolean =false;this.leftBoolean=false;
						fxString = "up";
						break;
					case 83:
						this.rightBoolean =false;this.leftBoolean=false;
						this.downBoolean=true; this.upBoolean=false;
						fxString = "down";
						break;
					case 65:
						this.downBoolean=false;this.upBoolean=false;
						this.leftBoolean=true; this.rightBoolean =false;
						fxString = "left";
						break;
					case 68:
						this.rightBoolean =true;this.leftBoolean=false;
						this.upBoolean=false; this.downBoolean=false;
						fxString = "right";
						break;
					case 32:
						fireNow = true;
						break;
					default:
						break;
				}
			}
		} else {
			if (this.playNumber.equals("1")) {
				switch (dis) {
				case 38:
					this.upBoolean=false;
					break;
				case 40:
					 this.downBoolean=false;
					break;
				case 37:
					 this.leftBoolean=false;
					break;
				case 39:
					this.rightBoolean =false;
					break;
				case 10:
					fireNow = false;
					break;
				default:
					break;
				}
			}
			else
			{
				switch (dis) {
					case 87:
						this.upBoolean=false;
						break;
					case 83:
						this.downBoolean=false;
						break;
					case 65:
						this.leftBoolean=false;
						break;
					case 68:
						this.rightBoolean =false;
						break;
					case 32:
						fireNow = false;
						break;
					default:
						break;
				}
			}
		}
	}

	@Override
	// 换装函数，注意行走动画需要依照朝向改变icon，并且需要不断更换图片显示,也就是记录当前的图片下标。
	//面对方向和imageindex一起组成了最终的icon图片显示
	public void updateImage() {
		String iconkey= playNumber;
		switch (fxString) {
			case "up":
				iconkey=iconkey+"up"+ imageIndex;
				setW(63);
				setH(80);
				break;
			case "down":
				iconkey=iconkey+"down"+ imageIndex;
				setW(63);
				setH(80);
				break;
			case "left":
				iconkey=iconkey+"left"+ imageIndex;
				setW(51);
				setH(76);
				break;
			case "right":
				iconkey=iconkey+"right"+ imageIndex;
				setW(51);
				setH(76);
				break;
			default:
				break;
		}
		setIcon(GameLoad.imgMap.get(iconkey));//设置相应的文件，一个例子：1 up 1(imageindex)
		//System.out.printf(iconkey+"\n");

		//注意到是移动才会导致imageindex的变化，因此在move函数内增加imageindex
	}

	@Override
	// 移动函数
	public void move() {
		int dx=(getX()+getW()/2)/ MapManager.dxdy;//对于宽度，需要获取一半长度，之后加上x坐标获取中点位置，在之后通过除法判断网格
		int dy=(getY()+getH())/ MapManager.dxdy;//对于高度，直接加上玩家高度即可
		if (this.leftBoolean) {
			try {
			if (MapManager.mapList[dy][dx-1]==null||!(MapManager.mapList[dy][dx] instanceof Bomb)){//如果要走的格子前面是炸弹就不走
				this.setX(this.getX() - v);
			}}catch (Exception e){
				System.out.println("yuejueleft"+playNumber+getH()+"!!"+getW());
			}
			//注意到imageindex最大只有24个，因此达到24之后需要置为1
			if(imageIndex <24){
				imageIndex++;//小于24就自增
			}else{//当等于或者大于24时进行置1
				imageIndex =1;
			}
		}
		if (this.upBoolean) {
			try {
			if (MapManager.mapList[dy-1][dx]==null||!(MapManager.mapList[dy][dx] instanceof Bomb)){//如果要走的格子前面是空并且没有炸弹就走
				this.setY(this.getY() - v);
			}
			}catch (Exception e){
				System.out.println("yuejueup"+playNumber+getH()+"!!"+getW());
			}
			if(imageIndex <24){
				imageIndex++;//小于24就自增
			}else{//当等于或者大于24时进行置1
				imageIndex =1;
			}
		}
		if (this.rightBoolean ) {//这里的边界直接使用常量获取
			try {
				if (MapManager.mapList[dy][dx+1]==null||!(MapManager.mapList[dy][dx] instanceof Bomb)){//如果要走的格子前面为空并且不是炸弹就走
					this.setX(this.getX() + v);
				}
			}catch (Exception e){
				System.out.println("yuejuerig"+playNumber+getH()+"!!"+getW());
			}

			if(imageIndex <24){
				imageIndex++;//小于24就自增
			}else{//当等于或者大于24时进行置1
				imageIndex =1;
			}
		}
		if (this.downBoolean) {
			try {
				if (MapManager.mapList[dy+1][dx]==null||!(MapManager.mapList[dy][dx] instanceof Bomb)) {//如果要走的格子前面是炸弹就不走
					this.setY(this.getY() + v);
				}
			}catch (Exception e){
				System.out.println("yuejuedown"+playNumber+" "+getH()+"!!"+getW());
			}


			if(imageIndex <24){
				imageIndex++;//小于24就自增
			}else{//当等于或者大于24时进行置1
				imageIndex =1;
			}
		}
	}


	@Override
	// 特殊行为函数：放置炸弹（bomb类），玩家类需要传入一个炸弹威力作为炸弹的参数，在生成bomb类之后再放入二维数组中产生爆炸
	public void behavior() {

		//这里需要通过计算得到具体炸弹存在的格子地方
		// dx和dy将是基本单位化后的坐标,
		int dx=(getX()+getW()/2)/MapManager.dxdy;//对于宽度，需要获取一半长度，之后加上x坐标获取中点位置，在之后通过除法判断网格
		int dy=(getY()+getH())/ MapManager.dxdy;//对于高度，直接加上玩家高度即可
		if (fireNow &&MapManager.mapList[dy][dx]!=null) {
			//System.out.println("po");
			return;
		}
		//对应位置不能有东西
		if (!fireNow || bombNow>=bombNum||MapManager.mapList[dy][dx]!=null) {
			return;
		}

		bombNow=bombNow+1;//增加一个炸弹计数

		//System.out.print(" play "+ bombNow+"\n");

		MusicPlayer musicPlayer=new MusicPlayer("sound/addBomb.wav",false);
		musicPlayer.start();

		//System.out.print(dx+"  "+dy+"\n");
		Bomb myBomb = (Bomb) new Bomb().build(dx, dy, bombPower,this);
		//在数组中的对应位置产生一个炸弹类
		MapManager.mapList[dy][dx]=myBomb;

		fireNow=false;//置为否，防止全自动轰炸
		score += bombNow*10;

	}
	//玩家特有的死亡函数，注意特效的改变

	public void die() {

		if (deadIndex>38){
			//在播放完之后，需要让对应的玩家重生
			if (getHp()-1>0){//尚未真正死亡
				setX(beginx);
				setY(beginy);
				setHp(getHp()-1);
				dieAndLive=true;//归还假死状态
				deadIndex=0;
				setIcon(GameLoad.imgMap.get(playNumber+"up"+ imageIndex));
//				System.out.println(getHp());
			}else{//真正死亡
				setIslive(false);
			}
		}else{
			setIcon(GameLoad.imgMap.get(playNumber+"dead"+deadIndex));
			deadIndex++;
		}
	}

	public int getScore(){
		return score;
	}



	@Override
	public Rectangle getRectangle() {//特殊处理,这里的xy需要设成和炸弹类似的xy
		Rectangle myRectangle=new Rectangle(getX()+25,getY()+64,gethHit(),getwHit());
		return myRectangle;
	}


}
