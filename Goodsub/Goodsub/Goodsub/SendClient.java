package Goodsub;

import java.io.*;
import java.net.*;

public class SendClient {

	/**
	 * @param args
	 */

	static final String IP = "192.168.1.159";    // デフォルトIP
	int sendPort = 10000;        // 送信用ポート番号
	// static final int GET_PORT = 10001;         // 受信用ポート番号
	
	
	TextFeald sctfd;   // クライアント送信用テキストフィールド
	// TextFeald gctfd; // クライアント受信用テキストフィールド
	ObjectOutputStream oos;
	// ObjectInputStream ois;
	// Object obj;              // 受信したオブジェクトを格納
	private static int member = 0;
	
	
	public static void main(String[] args) throws Exception {
		new SendClient().run(0);
	}
	
	
	void run(final int port) throws Exception{
		// TODO Auto-generated method stub
		
		// テキストエリア生成
		sctfd = new TextFeald("sclient", this);
		// gctfd = new TextFeald("gclient", this);
		
		// 送信用スレッド
		Thread sth = new Thread(){
			private Socket sock;

			public void run(){
				sctfd.setVisible(true);
				try {
					// 接続・送信処理
					sendPort += member*2;
					System.out.println("sendport : " + sendPort);
					sock = new Socket(IP, port);
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
	
	}

	
	// キーボードをタイプするたびにオブジェクトを出力
	public void keyTyped() {
		try {
			oos.writeObject(sctfd.jta.getText());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	static void setMember(int num){
		SendClient.member = num;
		System.out.println("setMember(send) : " + member);
	}
	
}
