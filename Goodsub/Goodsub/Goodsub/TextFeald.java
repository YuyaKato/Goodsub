package Goodsub;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TextFeald extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SendClient client;
	/**
	 * @param args
	 */
	
	static final int MAXROWS = 20;
	static final int MAXCOLMNS = 20;
	
	JTextArea jta;       // テキストエリア
	// String text;  // テキストエリアの文字列
	// JButton button;      // sendボタン
	
	
	TextFeald(String title, SendClient client){
		
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

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		if(client != null){
			client.keyTyped();
		}
	}


}
