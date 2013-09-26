package Goodsub;

import java.io.*;
import java.net.*;

public class SendClient {

	/**
	 * @param args
	 */

	static final String IP = "192.168.1.159"; // デフォルトIP
	
	private int sendPort = 10000;             // 送信用ポート番号	
	private TextFeald sctfd;                  // クライアント送信用テキストフィールド
	private ObjectOutputStream oos;
	
	private static int member = SyncServer.member;
	
	
	public static void main(String[] args) throws Exception {
		new SendClient().run(0);
	}
	
	
	void run(final int port) throws Exception{
		// TODO Auto-generated method stub
		
		// テキストエリア生成
		// sctfd = new TextFeald("sclient", this);
		sctfd.setVisible(true);
		
		// 送信用スレッド
		Thread sth = new Thread(){
			private Socket sock;

			public void run(){
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
			// sendテキストエリアの文字列を全て取得して送信
			oos.writeObject(sctfd.jta.getText());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	// クライアント数をセット
	static void setMember(int num){
		member = num;
		System.out.println("setMember(send) : " + member);
	}
	
}
