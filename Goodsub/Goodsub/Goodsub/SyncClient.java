package Goodsub;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class SyncClient {

	/**
	 * @param args
	 */

	static final String IP = "192.168.0.3";    // デフォルトIP
	static final int SEND_PORT = 10000;        // 送信用ポート番号
	static final int GET_PORT = 10001;         // 受信用ポート番号
	
	static TextFeald ctfd;   // クライアント用テキストフィールド
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Object obj;              // 受信したオブジェクトを格納
	
	// static List<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
	
	public static void main(String[] args) throws Exception {
		new SyncClient().run();
	}
	
	
	void run() throws Exception{
		// TODO Auto-generated method stub
		
		// テキストエリアの生成
		ctfd = new TextFeald("client", this);
		ctfd.setVisible(true);
		
		// 送信用
		Thread sth = new Thread(){
			public void run(){
				try {
					Socket sock = new Socket(IP, SEND_PORT);
					OutputStream out = sock.getOutputStream();
					oos = new ObjectOutputStream(out);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		sth.start();
		
		// 受信用
		Thread gth = new Thread(){
			public void run(){
				Socket sock;
				try {
					sock = new Socket(IP, GET_PORT);
					InputStream in = sock.getInputStream();
					ois = new ObjectInputStream(in);
					
					// オブジェクトの読み込み
					Thread gth2 = new Thread(){
						public void run(){
							while(true){
								try {
									obj = ois.readObject();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								// System.out.println(obj);
								if(obj instanceof String){
									// System.out.println("client get: " + obj);
									ctfd.jta.setText((String) obj);
								}
							}
						}
					};
					gth2.start();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		gth.start();
		
//		while(true){
//			/*
//			ctfd.button.addActionListener(
//					new ActionListener(){
//						public void actionPerformed(ActionEvent event){
//							try {
//								oos.writeObject(ctfd.jta.getText());
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					);
//					*/
//			oos.writeObject(ctfd.text);
//			
//		}
	}

	// キーボードをタイプするたびにオブジェクトを出力
	public void keyTyped() {
		try {
			oos.writeObject(ctfd.text);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
