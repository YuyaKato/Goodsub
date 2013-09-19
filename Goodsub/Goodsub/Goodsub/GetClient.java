package Goodsub;

import java.io.*;
import java.net.*;

public class GetClient {

	/**
	 * @param args
	 */
	
	static final String IP = "192.168.1.159";    // デフォルトIP
	static final int GET_PORT = 10001;         // 受信用ポート番号
	
	TextFeald gctfd; // クライアント受信用テキストフィールド
	ObjectInputStream ois;
	Object obj;              // 受信したオブジェクトを格納
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// new GetClient().run();
	}

	
	void run() throws Exception{
		// TODO Auto-generated method stub
		
		gctfd = new TextFeald("getclient", null);
		gctfd.setVisible(true);
		
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
									gctfd.jta.setText((String) obj);
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
		
	}
	
	public static void addMember() throws Exception{
		
			new GetClient().run();
	}
	
}
