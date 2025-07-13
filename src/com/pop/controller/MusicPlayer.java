package com.pop.controller;

import com.pop.element.Play;

import java.io.File;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * @ʹ��ʱ��Ҫ�ڹ��캯���д����ļ�·��string����
 * */
public class  MusicPlayer extends Thread {
	File soundFile;


	public void setname(String name) {
		this.name = name;
	}

	String name;
	boolean Loop;
	boolean stop=false;

	public MusicPlayer(String file,boolean Loop) {
		this.name = file;
		this.Loop=Loop;
	}
	public void run() {

		if (Loop){
			while(true){
				play();
				if(stop){
					break;
				}
			}
		}else{
			play();
		}

	}

	public void play() {
		soundFile = new File(name);// ������Ҫ���ŵ���Ƶ�ļ�
		AudioInputStream inputStream = null;
		SourceDataLine auLine = null;
		try {
			inputStream = AudioSystem.getAudioInputStream(soundFile);
			// ����һ����������׼�������ļ������ݣ������������ȥ�������ڴ��У�����֮���ٷ��뵽audiosystem����������
			AudioFormat format = inputStream.getFormat();
			// ��Ҫ��ȡ���ļ��ĸ�ʽ����˳������
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			// �����ָ����line��info�����е�ƥ���ж���
			auLine = (SourceDataLine) AudioSystem.getLine(info);
			// ����һ��������׼����audiosystem�Ļ�Ƶ��������Ƶ���ݣ�������������Ĭ���������Ϊ������

			// getSourceDataLine(AudioFormat format) ʹ�ô��ɣ������ԣ���������������У�����Ч����ͬ
			// SourceDataLine auLine=AudioSystem.getSourceDataLine(format);
			// �����ϲ��Կɵã�����õ�����audiosystem������˿ڣ�Ψһ��Ҫ��ֻ�ǽ��뷽ʽ
			// ��������Ƶ�ļ���ʽδ֪�йء���wav��AIFF, AU �Ĳ�ͬ

			auLine.open(format);// ��������
			auLine.start();

			int bytecount = 0;
			byte[] auBuffer = new byte[1024 * 128];
			while (bytecount != -1) {

				if(stop){

					break;
				}
				bytecount = inputStream.read(auBuffer, 0, auBuffer.length);
				if (bytecount >= 0) {
					auLine.write(auBuffer, 0, bytecount);
				}
			}

		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			auLine.drain();
			auLine.close();
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		}

	}


	public void end() {
		stop=true;
	}
}
