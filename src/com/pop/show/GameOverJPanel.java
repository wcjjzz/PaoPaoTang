package com.pop.show;

import com.pop.controller.MusicPlayer;
import com.pop.element.BombEffect;
import com.pop.element.Mapobj;
import com.pop.element.PropEffect;
import com.pop.manager.ElementManager;
import com.pop.manager.GameElement;
import com.pop.manager.GameLoad;
import com.pop.manager.MapManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 游戏开始界面
 * @author xay,drh
 */
public class GameOverJPanel extends ShowObj implements ActionListener {

    GameBeginJPanel jp_begin = new GameBeginJPanel();

    MusicPlayer musicPlayer;

    private ImageIcon apple = new ImageIcon("image/bg/apple.png");//可移动背景
    JLabel ap=new JLabel();//可移动背景的面板
    int apple_x=0;//可移动图片的X坐标
    int apple_y=0;//可移动图片的Y坐标
    int apple_w = apple.getIconWidth();//获取可移动图片的宽度
    int apple_h = apple.getIconHeight();//获取可移动图片的高度

    public GameOverJPanel(){
        init();
        GameJFrame.getGameJFrame().setSize(767,677);//设置面板大小
    }

    @Override
    protected void go() {
        apple_x-=1;//可移动图片的X坐标递减
        apple_y-=1;;//可移动图片的Y坐标递减
        ap.setBounds(apple_x,apple_y,850,850);//设置面板位置
        if (apple_x<-75){//如果递减位置到75 则重新递减
            apple_x=0;
            apple_y=0;
        }
    }

    public void init(){
        this.setLayout(null);
        ImageIcon bgIcon = new ImageIcon("image/button/DefineSprite_397_uiBg/393.png");//蓝色背景
        bgIcon.setImage(bgIcon.getImage().getScaledInstance(767,677, Image.SCALE_DEFAULT));

        ImageIcon icon1 = new ImageIcon("image/bg/win.png");//win
        ImageIcon icon2 = new ImageIcon("image/bg/lose.png");//lose

        JLabel jLabel1 = new JLabel(icon1);//存放win
        jLabel1.setBounds(78,170,596,211);//设置面板大小

        JLabel jLabel2 = new JLabel(icon2);//存放lose
        jLabel2.setBounds(110,170,596,211);//设置面板大小

        JLabel jLabel3 = new JLabel(bgIcon);//存放蓝色背景
        jLabel3.setBounds(0,0,767,677);//设置面板大小

        apple.setImage(apple.getImage().getScaledInstance(1092,794,Image.SCALE_AREA_AVERAGING));
        ap.setIcon(apple);//将可移动图片注入
        ap.setBounds(apple_x,apple_y,1000,1000);//设置面板大小

        /**
         * 注入
         */


        JButton back = new JButton();//返回按钮
        back.setIcon(new ImageIcon("image/bg/rect4.png"));
        back.setBounds(300, 450, 180, 60);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                onePlay=true;
                GameOverJPanel.this.actionPerformed(e);
            }
        });
        ElementManager.refresh();
        for (int dy = 0; dy < 15; dy++) {
            for (int dx = 0; dx < 17; dx++) {
                MapManager.mapList[dy][dx]=null;
            }
        }
        if(GameMainJPanel.isLose()){//如果输了则显示lose
            this.add(jLabel2);
        }else{//通关则显示win
            this.add(jLabel1);
        }
        this.add(back);
        this.add(ap);
        this.add(jLabel3);

        this.setVisible(true);
        this.setOpaque(true);
    }

    private static int i=0;
    @Override
    public void actionPerformed(ActionEvent e) {

        GameJFrame.getGameJFrame().remove(this);
        GameJFrame.getGameJFrame().setjPanel(jp_begin);
        ++i;
    }
}
