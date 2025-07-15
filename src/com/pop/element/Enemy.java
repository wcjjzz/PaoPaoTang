package com.pop.element;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.pop.controller.MusicPlayer;
import com.pop.manager.EffectTypes;
import com.pop.manager.ElementManager;
import com.pop.manager.GameElement;
import com.pop.manager.MapManager;

public class Enemy extends Play {

	int walkFlag=0;
	int fireFlag = 0; // 新增：用于控制放炸弹频率
	Random random = new Random();

	// 路径跟随相关变量
	private List<int[]> safePath = new ArrayList<>(); // 当前要走的安全路径
	private int pathStep = 0; // 当前走到路径的第几步

	public Enemy(int x, int y, String playNumber, ImageIcon icon) {
		super(x, y, playNumber, icon);
	}

	public void autoWalk() {
		// 控制移动频率
		if (walkFlag < 30) {
			walkFlag++;
			return;
		}
		walkFlag = 0;

		// 获取当前位置和朝向
		int dx = (getX() + getW() / 2) / MapManager.dxdy;
		int dy = (getY() + getH()) / MapManager.dxdy;

		// 检查前方是否有障碍
		boolean blocked = false;
		switch (fxString) {
			case "up":
				if (dy > 0 && MapManager.mapList[dy - 1][dx] != null) blocked = true;
				break;
			case "down":
				if (dy + 1 < MapManager.mapList.length && MapManager.mapList[dy + 1][dx] != null) blocked = true;
				break;
			case "left":
				if (dx > 0 && MapManager.mapList[dy][dx - 1] != null) blocked = true;
				break;
			case "right":
				if (dx + 1 < MapManager.mapList[0].length && MapManager.mapList[dy][dx + 1] != null) blocked = true;
				break;
		}

		// 如果前方被挡住，随机换一个方向
		if (blocked) {
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
			}
		}
		// 否则有概率继续前进
		// 不需要额外处理，方向布尔值已设置
	}

	// 新增：自动放置炸弹
	public void autoFire() {
		// 控制放炸弹频率
		if (fireFlag < 60) { // 每60帧最多放一次
			fireFlag++;
			return;
		}
		fireFlag = 0;

		// 有一定概率放炸弹
		if (random.nextDouble() < 0.2) { // 20%概率
			this.fireNow = true;
		}
	}

	// 危险区域识别：true为危险，false为安全
	public boolean[][] getDangerZone() {
		int rows = MapManager.mapList.length;
		int cols = MapManager.mapList[0].length;
		boolean[][] dangerZone = new boolean[rows][cols];
		// 1. 标记炸弹所在格为危险
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				if (MapManager.mapList[y][x] instanceof com.pop.element.Bomb) {
					dangerZone[y][x] = true;
					// 以炸弹威力为半径，横纵扩散标记危险（假设炸弹威力为1，后续可扩展）
					int power = 1;
					try {
						power = ((com.pop.element.Bomb) MapManager.mapList[y][x]).power;
					} catch (Exception e) {}
					// 上下左右
					for (int i = 1; i <= power; i++) {
						if (y - i >= 0) dangerZone[y - i][x] = true;
						if (y + i < rows) dangerZone[y + i][x] = true;
						if (x - i >= 0) dangerZone[y][x - i] = true;
						if (x + i < cols) dangerZone[y][x + i] = true;
					}
				}
				// 2. 标记爆炸特效所在格为危险
				if (MapManager.mapList[y][x] instanceof com.pop.element.BombEffect) {
					dangerZone[y][x] = true;
				}
			}
		}
		return dangerZone;
	}

	// BFS自动寻路到最近安全区，返回路径（每步[y, x]）
	public List<int[]> findSafePath(int startY, int startX, boolean[][] dangerZone) {
		int rows = dangerZone.length;
		int cols = dangerZone[0].length;
		boolean[][] visited = new boolean[rows][cols];
		int[][][] prev = new int[rows][cols][2]; // 记录前驱
		for (int y = 0; y < rows; y++)
			for (int x = 0; x < cols; x++)
				prev[y][x] = new int[]{-1, -1};

		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[]{startY, startX});
		visited[startY][startX] = true;

		int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}}; // 上下左右

		while (!queue.isEmpty()) {
			int[] curr = queue.poll();
			int y = curr[0], x = curr[1];
			// 找到安全格
			if (!dangerZone[y][x]) {
				// 回溯路径
				List<int[]> path = new ArrayList<>();
				int cy = y, cx = x;
				while (!(cy == startY && cx == startX)) {
					path.add(0, new int[]{cy, cx});
					int[] p = prev[cy][cx];
					cy = p[0];
					cx = p[1];
				}
				return path;
			}
			// 扩展四个方向
			for (int[] d : dirs) {
				int ny = y + d[0], nx = x + d[1];
				if (ny >= 0 && ny < rows && nx >= 0 && nx < cols && !visited[ny][nx]) {
					// 只走可达格（非障碍物）
					if (MapManager.mapList[ny][nx] == null || MapManager.mapList[ny][nx] instanceof com.pop.element.BombEffect) {
						visited[ny][nx] = true;
						prev[ny][nx][0] = y;
						prev[ny][nx][1] = x;
						queue.add(new int[]{ny, nx});
					}
				}
			}
		}
		// 没有安全区
		return new ArrayList<>();
	}

	@Override
	public void model() {
		// 1. 获取当前位置
		int dx = (getX() + getW() / 2) / MapManager.dxdy;
		int dy = (getY() + getH()) / MapManager.dxdy;
		// 2. 获取危险区
		boolean[][] dangerZone = getDangerZone();
		// 3. 如果当前位置危险，或当前路径已走完，则重新寻路
		boolean needFindPath = false;
		if (dangerZone[dy][dx]) {
			needFindPath = true;
		} else if (safePath == null || pathStep >= safePath.size()) {
			needFindPath = true;
		}
		if (needFindPath) {
			safePath = findSafePath(dy, dx, dangerZone);
			pathStep = 0;
		}
		// 4. 沿路径移动
		if (safePath != null && pathStep < safePath.size()) {
			int[] next = safePath.get(pathStep);
			int nextY = next[0], nextX = next[1];
			// 计算方向
			if (nextY < dy) {
				this.downBoolean = false; this.upBoolean = true;
				this.rightBoolean = false; this.leftBoolean = false;
				fxString = "up";
			} else if (nextY > dy) {
				this.rightBoolean = false; this.leftBoolean = false;
				this.downBoolean = true; this.upBoolean = false;
				fxString = "down";
			} else if (nextX < dx) {
				this.downBoolean = false; this.upBoolean = false;
				this.leftBoolean = true; this.rightBoolean = false;
				fxString = "left";
			} else if (nextX > dx) {
				this.rightBoolean = true; this.leftBoolean = false;
				this.upBoolean = false; this.downBoolean = false;
				fxString = "right";
			}
			// 如果已经到达该格，走下一步
			if (dx == nextX && dy == nextY) {
				pathStep++;
			}
		}
		// 5. 自动放置炸弹（如被困时）
		if (dangerZone[dy][dx] && (safePath == null || safePath.isEmpty())) {
			this.fireNow = true;
		} else {
			autoFire(); // 保留原有的定时随机放炸弹
		}
		// 6. 正常执行父类逻辑
		super.model();
	}
}
