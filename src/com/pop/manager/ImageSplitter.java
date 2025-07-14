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
            
            int[] cropParams = {
            		//down方向
            		25, 42, width-50, height-42,
            		//left方向
            		25, 42, width-50, height-42,
            		//right方向
            		25, 42, width-50, height-42,
            		//up方向
            		25, 42, width-50, height-42,
            };
            
            // 分割图片
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                	// 获取当前方向的裁剪参数
                    int baseIndex = row * 4;
                    int cropX = cropParams[baseIndex];
                    int cropY = cropParams[baseIndex+1];
                    int cropWidth = cropParams[baseIndex+2];
                    int cropHeight = cropParams[baseIndex+3];
                    
                    // 计算原始子图位置
                    int srcX = col * width;
                    int srcY = row * height;
                    
                    //创建裁剪后的子图
                    BufferedImage subImage = originalImage.getSubimage(
                		srcX + cropX, 
                        srcY + cropY, 
                        cropWidth, 
                        cropHeight
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
                    
                 // 构建输出文件夹路径
                    String folderPath = String.format("image/Characters/newCharacers_1");
                    File folder = new File(folderPath);

                    // 如果文件夹不存在，则自动创建
                    if (!folder.exists()) {
                        folder.mkdirs(); // 创建多级目录（如果有父目录也不存在）
                    }

                    // 构建输出文件路径
                    String filePath = String.format("%s/newCharacter_%s%d.png", folderPath, direction, col + 1);
                    File output = new File(filePath);
                    ImageIO.write(subImage, "png", output);

                }
            }
            
            System.out.println("图片分割完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
