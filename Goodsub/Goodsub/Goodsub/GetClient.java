package Goodsub;

import java.io.*;
import java.net.*;


public class GetClient {
	
	
	static final String IP = "192.168.1.159";    // デフォルトIP
	int getPort = 10003;         // 受信用ポート番号
	
	TextFeald gctfd; // クライアント受信用テキストフィールド
	ObjectInputStream ois;
	Object obj;              // 受信したオブジェクトを格納
	private static int member = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new GetClient().run(0);
	}
	
	public void run(final int port){
		
		// テキストエリアの生成
		gctfd = new TextFeald("gclient" + member, null);
		
		// 受信用スレッド
		Thread gth = new Thread(){
			public void run(){
				Socket sock;
				gctfd.setVisible(true);
				try {
					// 接続処理
					getPort -= member*2;
					System.out.println("getPort : " + getPort);
					sock = new Socket(IP, port);
					InputStream in = sock.getInputStream();
					ois = new ObjectInputStream(in);
					
					// オブジェクト読み込みスレッド
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
								if(obj instanceof String){
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
	
	
	static void setMember(int num){
		member = num;
		System.out.println("setMember(get) : " + member);
	}

}
