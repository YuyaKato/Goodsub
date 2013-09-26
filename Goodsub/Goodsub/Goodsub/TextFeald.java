package Goodsub;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TextFeald extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SendClient2 client;
	/**
	 * @param args
	 */
	
	static final int MAXROWS = 20;   // テキストエリアの列数
	static final int MAXCOLMNS = 20; // テキストエリアの行数
	
	JTextArea jta;                   // テキストエリア
	
	
	TextFeald(String title, SendClient2 client){
		
		this.client = client;
		
		Container cont = getContentPane();
		
		// テキストエリアの設定
		jta = new JTextArea(MAXROWS, MAXCOLMNS);
		setTitle(title);
		cont.add(jta);
		setSize(200,200);
		
		// キーボードリスナーの追加
		jta.addKeyListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		TextFeald textFeald = new TextFeald("test", null);
		textFeald.setVisible(true);
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// キーボードタイプ後リリースされた時
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		// SendClientクラスで処理
		if(client != null){
			client.keyTyped();
		}
	}


}
