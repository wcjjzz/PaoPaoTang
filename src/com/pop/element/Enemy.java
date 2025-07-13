package com.pop.element;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;

import com.pop.controller.MusicPlayer;
import com.pop.manager.EffectTypes;
import com.pop.manager.ElementManager;
import com.pop.manager.GameElement;

public class Enemy extends Play {

	int walkFlag=0;

	public Enemy(int x, int y, String playNumber, ImageIcon icon) {
		super(x, y, playNumber, icon);
	}

	public void autoWalk() {
		if (walkFlag < 50){
			walkFlag++;
			return;
		}
		walkFlag = 0;
		Random random = new Random();
		int ran = random.nextInt(4);
		switch (ran) {
			case 0:
				this.downBoolean = false;
				this.upBoolean = true;
				this.rightBoolean = false;
				this.leftBoolean = false;
				fxString = "up";
				break;
			case 1:
				this.rightBoolean = false;
				this.leftBoolean = false;
				this.downBoolean = true;
				this.upBoolean = false;
				fxString = "down";
				break;
			case 2:
				this.downBoolean = false;
				this.upBoolean = false;
				this.leftBoolean = true;
				this.rightBoolean = false;
				fxString = "left";
				break;
			case 3:
				this.rightBoolean = true;
				this.leftBoolean = false;
				this.upBoolean = false;
				this.downBoolean = false;
				fxString = "right";
				break;
			case 4:
				fireNow = true;
				break;
			default:
				break;
		}
	}
}
