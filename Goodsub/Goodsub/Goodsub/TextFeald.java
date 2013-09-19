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
	String text;  // テキストエリアの文字列
	// JButton button;      // sendボタン
	
	
	TextFeald(String title, SendClient client){
		
		this.client = client;
		
		Container cont = getContentPane();
		
		// テキストエリアの設定
		jta = new JTextArea(MAXROWS, MAXCOLMNS);
		setTitle(title);
		cont.add(jta);
		setSize(200,200);
		
		// ボタンの配置
		// button = new JButton("send");
		// cont.add(button,BorderLayout.SOUTH);
		
		// キーボードリスナーの追加
		jta.addKeyListener(this);

		/*
		// ボタンの動作
		button.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
//						text = jta.getText();
//						System.out.println(text);
					}
				}
				);
		*/
		
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
		
		//　キーボードがタイプされたときに呼ばれる
		// System.out.println("Press: " + e.getKeyChar());
		/*
		if(text == null){
			text = String.valueOf(e.getKeyChar());
		}else{
			text = text + String.valueOf(e.getKeyChar());
		}
		*/
		/*
		if(client != null){
			client.keyTyped();
		}
		*/
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
