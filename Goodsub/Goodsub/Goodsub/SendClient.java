package Goodsub;

import java.io.*;
import java.net.*;

public class SendClient {

	/**
	 * @param args
	 */

	static final String IP = "192.168.1.159";    // デフォルトIP
	static final int SEND_PORT = 10000;        // 送信用ポート番号
	static final int GET_PORT = 10001;         // 受信用ポート番号
	
	private TextFeald sctfd;   // クライアント送信用テキストフィールド
	TextFeald gctfd; // クライアント受信用テキストフィールド
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Object obj;              // 受信したオブジェクトを格納
	
	
	public static void main(String[] args) throws Exception {
		new SendClient().run();
	}
	
	
	void run() throws Exception{
		// TODO Auto-generated method stub
		
		// テキストエリアの生成
		// ctfd = new TextFeald("client", this);
		// ctfd.setVisible(true);
		
		sctfd = new TextFeald("sclient", this);
		gctfd = new TextFeald("gclient", this);
		
		// 送信用
		Thread sth = new Thread(){
			private Socket sock;

			public void run(){
				sctfd.setVisible(true);
				try {
					sock = new Socket(IP, SEND_PORT);
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
				// gctfd.setVisible(true);
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

	// キーボードをタイプするたびにオブジェクトを出力
	public void keyTyped() {
		try {
			oos.writeObject(sctfd.jta.getText());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
