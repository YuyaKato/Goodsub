package Goodsub;

import java.io.*;
import java.net.*;
import java.util.*;

public class SyncServer {

	/**
	 * @param args
	 */

	static final int GET_PORT = 10000;   // 受信用ポート番号
	static final int SEND_PORT = 10001;  // 送信用ポート番号
	TextFeald stfd;                      // サーバ用テキストフィールド
	static int member;
	
	// オブジェクトの出力先を格納
	List<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		new SyncServer().run();
	}
	
	
	void run() throws Exception{
		
		//テキストエリアを開く
		stfd = new TextFeald("sever", null);
		stfd.setVisible(true);

		// 受信用スレッド
		Thread gth = new Thread(){
			private ServerSocket serverSocket;
			public void run(){
				try {
					// クライアントとの接続
					serverSocket = new ServerSocket(GET_PORT);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("gServer accept");

						GetClient.addMember();
						
						// オブジェクトの読み込み
						Thread gth2 = new Thread(){
							public void run(){
								InputStream in;
								try {
									in = sock.getInputStream();
									ObjectInputStream ois = new ObjectInputStream(in);
									while(true){
										showText(ois);
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
						gth2.start();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		gth.start();
		
		// 送信用ソケット
		Thread sth = new Thread(){
			private ServerSocket serverSocket;

			public void run(){
				try {
					serverSocket = new ServerSocket(SEND_PORT);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("sServer accept");
						
						// オブジェクトの書き込み
						Thread sth2 = new Thread(){
							public void run(){
								OutputStream out;
								try {
									out = sock.getOutputStream();
									ObjectOutputStream oos = new ObjectOutputStream(out);
									/*
									SyncClient sref = new SyncClient();
									System.out.println(stfd.jta.getText());
									sref.setText(stfd.jta.getText());
									*/
									oos.writeObject(stfd.jta.getText());
									outputs.add(oos);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
						sth2.start();
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		sth.start();
	}
	

	// サーバテキストフィールドに表示
	void showText(ObjectInputStream sois) throws IOException{
		Object sobj = null;
		try {
			sobj = sois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sobj instanceof String){
			send((String) sobj);
			stfd.jta.setText((String) sobj);	
		}	
	}
	
	
	// オブジェクトの出力
	void send(String str) throws IOException{
		for (ObjectOutputStream output : outputs) {
			output.writeObject(str);
		}
	}

}
