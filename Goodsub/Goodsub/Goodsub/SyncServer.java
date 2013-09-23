package Goodsub;

import java.io.*;
import java.net.*;
import java.util.*;

public class SyncServer {

	/**
	 * @param args
	 */

	static final int GET_PORT_A = 10000;   // 受信用ポート番号
	static final int GET_PORT_B = 10002;
	static final int SEND_PORT_A = 10001;
	static final int SEND_PORT_B = 10003;  // 送信用ポート番号
	TextFeald stfd1;                      // サーバ用テキストフィールド
	TextFeald stfd2;
	private int member = 0;
	Object obj1;
	Object obj2;
	ObjectOutputStream oos1;
	ObjectOutputStream oos2;
	
	
	// List<ObjectInputStream> inputs = new ArrayList<ObjectInputStream>();
	
	// オブジェクトの出力先を格納
	List<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		new SyncServer().run();
	}
	
	
	void run() throws Exception{
		
		//テキストエリアを開く
		stfd1 = new TextFeald("sever1", null);
		stfd1.setVisible(true);
		
		//テキストエリアを開く
		stfd2 = new TextFeald("sever2", null);
		stfd2.setVisible(true);

		// 受信用スレッドA
		Thread gtha = new Thread(){
			private ServerSocket serverSocket;
			
			public void run(){
				try {
					
					// クライアントとの接続
					serverSocket = new ServerSocket(GET_PORT_A);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("gServer accept");
						member++;
						SendClient.setMember(member);
						new GetClient().run(SEND_PORT_B);
						GetClient.setMember(member);
						
						// オブジェクトの読み込み
						Thread gtha2 = new Thread(){
							public void run(){
								InputStream in;
								try {
									in = sock.getInputStream();
									ObjectInputStream ois = new ObjectInputStream(in);
									while(true){
										// obj1 = ois.readObject();
										showText1(ois);
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
						gtha2.start();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		// 送信用スレッドB
		Thread sthb = new Thread(){
			private ServerSocket serverSocket;

			public void run(){
				try {
					serverSocket = new ServerSocket(SEND_PORT_B);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("sServer accept");

						// オブジェクトの書き込み
						Thread sthb2 = new Thread(){
							public void run(){
								OutputStream out;
								try {
									out = sock.getOutputStream();
									oos1 = new ObjectOutputStream(out);
									/*
									while(true){
										oos.writeObject(stfd1.jta.getText());
									}
									*/
									// outputs.add(oos);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						};
						sthb2.start();

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

		
		// 受信用スレッドB
		Thread gthb = new Thread(){
			private ServerSocket serverSocket;
			public void run(){
				try {
					
					// クライアントとの接続
					serverSocket = new ServerSocket(GET_PORT_B);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("gServer accept");
						member++;
						SendClient.setMember(member);
						new GetClient().run(SEND_PORT_A);
						GetClient.setMember(member);

						// オブジェクトの読み込み
						Thread gtha2 = new Thread(){
							public void run(){
								InputStream in;
								try {
									in = sock.getInputStream();
									ObjectInputStream ois = new ObjectInputStream(in);
									while(true){
										// obj2 = ois.readObject();
										showText2(ois);
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
						gtha2.start();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		// 送信用スレッドA
		Thread stha = new Thread(){
			private ServerSocket serverSocket;

			public void run(){
				try {
					serverSocket = new ServerSocket(SEND_PORT_A);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("sServer accept");

						// オブジェクトの書き込み
						Thread sthb2 = new Thread(){
							public void run(){
								OutputStream out;
								try {
									out = sock.getOutputStream();
									oos2 = new ObjectOutputStream(out);
									/*
									while(true){
										oos.writeObject(stfd2.jta.getText());
									}
									*/
									// outputs.add(oos);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						};
						sthb2.start();

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


		gtha.start();
		gthb.start();
		sthb.start();
		stha.start();

	}
	

	// サーバテキストフィールドに表示
	void showText1(ObjectInputStream sois) throws IOException{
		// Object sobj = null;
		try {
			obj1 = sois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(obj1 instanceof String){
			// send((String) sobj);
			oos1.writeObject((String) obj1);
			stfd1.jta.setText((String) obj1);
		}	
	}
	
	
	void showText2(ObjectInputStream sois) throws IOException{
		// Object sobj = null;
		try {
			obj2 = sois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(obj2 instanceof String){
			// send((String) sobj);
			oos2.writeObject((String) obj2);
			stfd2.jta.setText((String) obj2);
		}	
	}
	
	
	// オブジェクトの出力
	void send(String str) throws IOException{
		for (ObjectOutputStream output : outputs) {
			output.writeObject(str);
		}
	}

}
