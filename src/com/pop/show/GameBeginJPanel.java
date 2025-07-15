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

    private boolean showIntroduce = false;
    private JLabel introduceLabel = null;

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
        GameJFrame.getGameJFrame().setSize(1000,830);//设置面板大小为1000x830
        
        // 只显示title.png，铺满整个面板
        ImageIcon bgIcon = new ImageIcon("image/bg/title.png");
        bgIcon.setImage(bgIcon.getImage().getScaledInstance(1000,830,Image.SCALE_DEFAULT));
        JLabel jLabelBg = new JLabel(bgIcon);
        jLabelBg.setBounds(0,0,1000,830);

        JButton onePlayerButton = new JButton();//单人玩家按钮
        onePlayerButton.setIcon(new ImageIcon("image/bg/rect1.png"));
        onePlayerButton.setBounds(60, 250, 180, 60);//移动到左侧
        onePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onePlay=true;
                GameBeginJPanel.this.actionPerformed(e);
            }
        });

        JButton twoPlayerButton = new JButton();//双人玩家按钮
        twoPlayerButton.setIcon(new ImageIcon("image/bg/rect2.png"));
        twoPlayerButton.setBounds(60, 350, 180, 60);//移动到左侧
        twoPlayerButton.addActionListener(this);

        JButton introduceButton = new JButton();
        introduceButton.setIcon(new ImageIcon("image/bg/rect3.png"));
        introduceButton.setBounds(60, 450, 180, 60);
        introduceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIntroduce = !showIntroduce;
                if (showIntroduce) {
                    if (introduceLabel == null) {
                        ImageIcon introduceIcon = new ImageIcon("image/bg/introduce.png");
                        introduceIcon.setImage(introduceIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
                        introduceLabel = new JLabel(introduceIcon);
                        introduceLabel.setBounds(260, 200, 600, 600);
                    }
                    add(introduceLabel);
                    introduceLabel.setVisible(true);
                    setComponentZOrder(introduceLabel, 0);
                } else {
                    if (introduceLabel != null) {
                        introduceLabel.setVisible(false);
                    }
                }
                repaint();
            }
        });

        this.add(onePlayerButton);
        this.add(twoPlayerButton);
        this.add(introduceButton);
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

