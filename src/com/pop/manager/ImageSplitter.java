package com.pop.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSplitter {
	public static void main(String[] args) {
        try {
            // 加载原始图片
            BufferedImage originalImage = ImageIO.read(new File("image/Characters/Done_body16001_walk.png"));
            
            // 计算每个小图片的宽度和高度
            int width = originalImage.getWidth() / 4;
            int height = originalImage.getHeight() / 4;
            
            // 分割图片
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    BufferedImage subImage = originalImage.getSubimage(
                        col * width, 
                        row * height, 
                        width, 
                        height
                    );
                    
                    // 保存分割后的小图片
                    String direction;
                    switch (row) {
                        case 0: direction = "down"; break;
                        case 1: direction = "left"; break;
                        case 2: direction = "right"; break;
                        case 3: direction = "up"; break;
                        default: direction = "unknown";
                    }
                    
                    File output = new File(String.format("image/Characters/newCharacers/newCharacter_%s%d.png", direction, col+1));
                    ImageIO.write(subImage, "png", output);
                }
            }
            
            System.out.println("图片分割完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
