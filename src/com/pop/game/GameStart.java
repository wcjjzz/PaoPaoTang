package com.pop.game;


import com.pop.controller.GamemainThread;
import com.pop.controller.MainController;
import com.pop.show.GameBeginJPanel;
import com.pop.show.GameJFrame;
import com.pop.show.GameMainJPanel;
import com.pop.show.GameOverJPanel;

public class GameStart {

	public static void main(String[] args) {
		GameJFrame gj=GameJFrame.getGameJFrame();
		/**实例化面板,监听器，线程，注入到jframe中*/
		GameBeginJPanel jp_begin = new GameBeginJPanel();
		GameOverJPanel jp_over = new GameOverJPanel();
		//GameMainJPanel jp=new GameMainJPanel();
		MainController controller=new MainController();


//		注入
		gj.setjPanel(jp_begin);
		gj.setKeyListener(controller);
		gj.start();
	
	}

}
