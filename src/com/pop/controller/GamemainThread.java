package com.pop.controller;

import com.pop.element.*;
import com.pop.manager.ElementManager;
import com.pop.manager.GameElement;
import com.pop.manager.GameLoad;
import com.pop.manager.MapManager;
import com.pop.show.GameJFrame;
import com.pop.show.GameMainJPanel;
import com.pop.show.GameOverJPanel;

import java.util.List;
import java.util.Map;

import javax.swing.*;




public class GamemainThread extends Thread {

	GameOverJPanel jp_over = new GameOverJPanel();

	private ElementManager em = ElementManager.getManager();// 得到元素管理器对象;
	private static int allTime = 600*1000; //10分钟

	int Level;
	boolean onePlay;
	GameMainJPanel MainJpanl;
	MusicPlayer musicPlayer=null;

	boolean Lose=false;
	boolean Win=false;

	public GamemainThread(int level, boolean one, GameMainJPanel MainJpanl){
		super();
		Level=level;
		this.onePlay=one;
		this.MainJpanl=MainJpanl;
	}
	@Override
	public void run() {
		//这里需要播放面板音乐
		musicPlayer=new MusicPlayer("sound/gameMusic_"+Level+".wav",true);
		musicPlayer.start();
		while (true) {// true可变为变量控制结束
			// 游戏开始,进行相应的资源加载
			gameload();
			// 游戏运行
			gamerun();
			// 游戏结束
			gameover();
		}
	}

	private void gameload() {
		// TODO Auto-generated method stub

		// 实时缓存地图
		//System.out.println(Level);
		GameLoad.MapLoad(Level);

		// 加载玩家

		load();
	}

	public void load() {
//		图片导入
//四个角标：40, 20   670, 530   40,530  670, 20
		if (onePlay){
			ImageIcon icon1 = GameLoad.imgMap.get("1down1");
			Play obj1 = new Play(40, 20,"1", icon1);// 实例化玩家1
			em.addElement(obj1, GameElement.PLAY);
			loadEnemy(3);
		}else{
			ImageIcon icon1 = GameLoad.imgMap.get("1down1");
			ImageIcon icon2 = GameLoad.imgMap.get("2down1");
			Play obj1 = new Play(40, 20,"1", icon1);// 实例化玩家1
			Play obj2 = new Play(670, 530,"2", icon2);// 实例化玩家2
			// 直接添加
			em.addElement(obj2, GameElement.PLAY);
			em.addElement(obj1, GameElement.PLAY);
			loadEnemy(2);
		}
	}

	private void loadEnemy(int i) {//生成敌人函数
		//四个角标：40, 20   670, 530   40,530  670, 20
		if (i==3){
			ImageIcon icon1 = GameLoad.imgMap.get("2down1");
			ImageIcon icon2 = GameLoad.imgMap.get("3down1");
			ImageIcon icon3 = GameLoad.imgMap.get("4down1");
			Enemy Enemy1 = new Enemy(40, 530,"2", icon1);// 实例化敌人2
			Enemy Enemy2 = new Enemy(670, 20,"3", icon2);// 实例化敌人3
			Enemy Enemy3 = new Enemy(670, 530,"4", icon2);// 实例化敌人4
			// 直接添加
			em.addElement(Enemy2, GameElement.ENEMY);
			em.addElement(Enemy1, GameElement.ENEMY);
			em.addElement(Enemy3, GameElement.ENEMY);
		}else{
			ImageIcon icon1 = GameLoad.imgMap.get("3down1");
			ImageIcon icon2 = GameLoad.imgMap.get("4down1");
			Enemy Enemy1 = new Enemy(40, 530,"3", icon1);// 实例化敌人3
			Enemy Enemy2 = new Enemy(670, 20,"4", icon2);// 实例化敌人4
			// 直接添加
			em.addElement(Enemy2, GameElement.ENEMY);
			em.addElement(Enemy1, GameElement.ENEMY);
		}
	}

	private void gamerun() {
		// TODO Auto-generated method stub

		allTime = 600*1000;
		/*
		File file=new File("sound/ikdu3-p6wm8.mp3");
		Player player = null;
			try {
				player = new Player(new FileInputStream(file));
			} catch (FileNotFoundException | JavaLayerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	        try {
				player.play();
			} catch (JavaLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
      */

		while (true) {// true可变为关卡判断
			//下面将会循环人物和敌人
			Map<GameElement, List<ElementObj>> all = em.getGameElements();

			for (GameElement ge : GameElement.values()) { // 迭代器
				List<ElementObj> list = all.get(ge);
				if(list.isEmpty()&&ge==GameElement.PLAY){//如果列表为空，说明玩家已经全部死亡，需要跳出循环
					Lose=true;break;
				}
				if(list.isEmpty()&&ge==GameElement.ENEMY){//如果列表为空，说明敌人已经全部死亡，需要跳出循环
					Win=true;break;
				}
				if (ge==GameElement.ENEMY){
					for (int i = 0; i < list.size(); i++) {
						Enemy obj = (Enemy) list.get(i);//首先判断假死状态，之后判断真死状态
						if(!obj.isDieAndLive()){
							obj.die();
							if (!obj.getislive()) {//真死状态
								list.remove(i--);
							}
							continue;
						}
						obj.autoWalk();
						obj.model();// 调用每个类的model
					}
				}else if (ge==GameElement.PLAY){
					for (int i = 0; i < list.size(); i++) {
						Play obj = (Play) list.get(i);//首先判断假死状态，之后判断真死状态
						if(!obj.isDieAndLive()){
							obj.die();
							if (!obj.getislive()) {//真死状态
								list.remove(i--);
							}
							continue;
						}
						obj.model();// 调用每个类的model
					}
				}
			}

			if (Lose||Win){//继续跳出
				break;
			}

			//使用了二维数组形式的地图，因此在这里显示需要使用二维数组循环处理
			for (int dy = 0; dy < 15; dy++) { // 迭代器
				for (int dx = 0; dx < 17; dx++) {
					if(MapManager.mapList[dy][dx]==null) {//判断空值
						continue;
					}
					if (!MapManager.mapList[dy][dx].getislive()) {
						MapManager.mapList[dy][dx].die();//在这里使用,不放入model中，节约重复判断
						//注意die方法有时会生成一个新元素在原地，因此需要需要将置空操作交给类自身决定
						continue;
					}
					MapManager.mapList[dy][dx].model();// 调用每个类的model
				}
			}


			List<ElementObj> Plays = all.get(GameElement.PLAY);
			//遍历二维数组检测碰撞
			for (int dy = 0; dy < 15; dy++) {
				for (int dx = 0; dx < 17; dx++) {
					if(MapManager.mapList[dy][dx]==null) {//判断空值
						continue;
					}
					if (MapManager.mapList[dy][dx] instanceof Mapobj){
						Elementpk(Plays, (Mapobj) MapManager.mapList[dy][dx]);
					}else if (MapManager.mapList[dy][dx] instanceof BombEffect){
						Elementpk(Plays, (BombEffect) MapManager.mapList[dy][dx]);
					} else if (MapManager.mapList[dy][dx] instanceof PropEffect){
						Elementpk(Plays, (PropEffect) MapManager.mapList[dy][dx]);
					}
				}
			}
			List<ElementObj> Enemy = all.get(GameElement.ENEMY);
			//遍历二维数组检测碰撞
			for (int dy = 0; dy < 15; dy++) {
				for (int dx = 0; dx < 17; dx++) {
					if(MapManager.mapList[dy][dx]==null) {//判断空值
						continue;
					}
					if (MapManager.mapList[dy][dx] instanceof Mapobj){
						Elementpk(Enemy, (Mapobj) MapManager.mapList[dy][dx]);
					}else if (MapManager.mapList[dy][dx] instanceof BombEffect){
						Elementpk(Enemy, (BombEffect) MapManager.mapList[dy][dx]);
					} else if (MapManager.mapList[dy][dx] instanceof PropEffect){
						Elementpk(Enemy, (PropEffect) MapManager.mapList[dy][dx]);
					}
				}
			}

			allTime = allTime - 10;
			try {
				// 玩家和障碍
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

    private void Elementpk(List<ElementObj> A, Mapobj B) {
        // TODO Auto-generated method stub
        for (int i = 0; i < A.size(); i++) {//此循环循环play列表
            Play C = ((Play) A.get(i));//获取玩家类
            if (C.pk(B)) {//如果碰撞了障碍
                switch (C.getFxString()) {
                    case "up":
                        C.setY(C.getY() + C.getV());
                        break;
                    case "down":
                        C.setY(C.getY() - C.getV());
                        break;
                    case "left":
                        C.setX(C.getX() + C.getV());
                        break;
                    case "right":
                        C.setX(C.getX() - C.getV());
                        break;
                }
            }
        }
    }

	private void Elementpk(List<ElementObj> A, PropEffect B) {
		// TODO Auto-generated method stub
		for (int i = 0; i < A.size(); i++) {//此循环循环play列表
			Play C = ((Play) A.get(i));//获取玩家类
			if (C.pk(B)) {//如果碰撞了道具
				B.get(C);//改变人物属性
				//获得完成之后，需要调用死亡方法
				B.die();
			}
		}
	}
	private void Elementpk(List<ElementObj> A, BombEffect B) {
		// TODO Auto-generated method stub
		for (int i = 0; i < A.size(); i++) {//此循环循环play列表
			Play C = ((Play) A.get(i));//获取玩家类
			if (C.pk(B)) {//如果碰撞了炸弹特效
				C.setDieAndLive(false);
			}
		}
	}

	private void gameover() {//在玩家全部死亡之后，会到达这里
		// TODO Auto-generated method stub
		musicPlayer.stop();
		if (Win){
			MainJpanl.win();
		}else if(Lose){
			MainJpanl.lose();
		}
		this.stop();
		//GameJFrame.getGameJFrame().setjPanel(jp_over);
	}
	public static int getAllTime() {
		return allTime;
	}

}
