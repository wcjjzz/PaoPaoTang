package com.pop.manager;


import com.pop.element.ElementObj;
import com.pop.show.GameMainJPanel;

import java.util.List;

/*
* @管理游戏时地图的实时数据变化（地图元素的变化）
* 由于图片大小的关系，得到坐标（数组下标）之后，各自类内需要再次微调才可正常显示，因此不提供单位转换函数，只公开最小子单位量
* 此类将可以：解决一个格子只能放一个炸弹，获取实时地图的数据影响爆炸效果延伸和爆炸后物品的破坏
* 除了显示是遍历整个二维数组，其他操作基本使用下标直接访问，不用遍历整个列表（人物有关碰撞请使用将人物xy转换为标准下表检测碰撞）
*另外也可提供给ai寻路算法作为数据源（存储玩家位置）
*
* */
public class MapManager {


    public static ElementObj[][] mapList=new ElementObj[15][17];//实时地图，将会在初始化函数中被赋值，在外部应当是读取地图配置的时候一起赋值,如果展示遍历这个数组，配置文件可以不使用二维数组的新形式

    public static int dxdy=45;//用于地图图片显示的最小子单位，需要乘上这个才能获得真正的xy坐标

    public MapManager(){
        init();
    }

    private void init() {//初始化这里首先创造一个试用数组

    }

}
