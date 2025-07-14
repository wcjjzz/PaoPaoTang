package com.pop.show;

import com.pop.controller.MusicPlayer;
import com.pop.element.ElementObj;
import com.pop.game.GameStart;
import com.pop.manager.ElementManager;
import com.pop.manager.GameElement;
import com.pop.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;



/**
 * 游戏开始界面
 * @author xay,drh
 */
public class GameBeginJPanel extends ShowObj implements ActionListener{
    private ImageIcon img;
    private int w;
    private int h;
    private ImageIcon apple = new ImageIcon("image/bg/apple.png");//可移动背景
    public GameBeginJPanel(){
        this.w = GameJFrame.GameX;//窗体宽度
        this.h = GameJFrame.GameY;//窗体高度
        init();
    }
    JLabel ap=new JLabel();//可移动背景的面板
    int apple_x=0;//可移动图片的X坐标
    int apple_y=0;//可移动图片的Y坐标
    int apple_w = apple.getIconWidth();//获取可移动图片的宽度
    int apple_h = apple.getIconHeight();//获取可移动图片的高度

    boolean onePlay=false;
  //  MusicPlayer musicPlay

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

    private void init(){
        this.setLayout(null);
        GameJFrame.getGameJFrame().setSize(767,677);//设置面板大小
        
        // 只显示title.png，铺满整个面板
        ImageIcon bgIcon = new ImageIcon("image/bg/title.png");
        bgIcon.setImage(bgIcon.getImage().getScaledInstance(767,677,Image.SCALE_DEFAULT));
        JLabel jLabelBg = new JLabel(bgIcon);
        jLabelBg.setBounds(0,0,767,677);

        JButton onePlayerButton = new JButton();//单人玩家按钮
        onePlayerButton.setIcon(new ImageIcon("image/bg/rect1.png"));
        onePlayerButton.setBounds(520, 250, 180, 60);
        onePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onePlay=true;
                GameBeginJPanel.this.actionPerformed(e);
            }
        });

        JButton twoPlayerButton = new JButton();//双人玩家按钮
        twoPlayerButton.setIcon(new ImageIcon("image/bg/rect2.png"));
        twoPlayerButton.setBounds(520, 350, 180, 60);
        twoPlayerButton.addActionListener(this);

        this.add(onePlayerButton);
        this.add(twoPlayerButton);
        this.add(jLabelBg);

        this.setVisible(true);
        this.setOpaque(true);
    }
    private static int i=0;
    @Override
    public void actionPerformed(ActionEvent e) {
        //musicPlayer.end();



        GameJFrame.getGameJFrame().remove(this);
        GameJFrame.getGameJFrame().setjPanel(new GameMainJPanel(onePlay));
        ++i;
    }
}

