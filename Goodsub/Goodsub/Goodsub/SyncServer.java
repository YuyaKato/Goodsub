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
	
	// private TextFeald stfd1;               // サーバ用テキストフィールド
	// private TextFeald stfd2;
	
	private Object obj1;
	private Object obj2;
	private ObjectOutputStream oos1;
	private ObjectOutputStream oos2;
	
	static int member = 0;
	
	// オブジェクトの出力先を格納
	// private List<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		new SyncServer().run();
	}
	
	
	void run() throws Exception{
		
		//テキストエリアを開く
		// stfd1 = new TextFeald("sever1", null);
		// stfd1.setVisible(true);
		
		//テキストエリアを開く
		// stfd2 = new TextFeald("sever2", null);
		// stfd2.setVisible(true);

		
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
						new GetClient().run(SEND_PORT_B);
						member++;
						// System.out.println("ServerMember : " + member);
						SendClient.setMember(member);
						// new GetClient().run(SEND_PORT+member);
						GetClient.setMember(member);

						
						Thread gtha2 = new Thread(){
							public void run(){
								InputStream in;
								try {
									in = sock.getInputStream();
									ObjectInputStream ois = new ObjectInputStream(in);
									while(true){
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

						Thread sthb2 = new Thread(){
							public void run(){
								OutputStream out;
								try {
									out = sock.getOutputStream();
									oos1 = new ObjectOutputStream(out);
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
						// System.out.println("ServerMember : " + member);
						new GetClient().run(SEND_PORT_A);
						SendClient.setMember(member);
						GetClient.setMember(member);

						Thread gtha2 = new Thread(){
							public void run(){
								InputStream in;
								try {
									in = sock.getInputStream();
									ObjectInputStream ois = new ObjectInputStream(in);
									while(true){
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

						Thread sthb2 = new Thread(){
							public void run(){
								OutputStream out;
								try {
									out = sock.getOutputStream();
									oos2 = new ObjectOutputStream(out);
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
			// stfd1.jta.setText((String) obj1);
		}	
	}
	
	
	void showText2(ObjectInputStream sois) throws IOException{
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
			// stfd2.jta.setText((String) obj2);
		}	
	}
	
	
	// オブジェクトの出力
	/*
	void send(String str) throws IOException{
		for (ObjectOutputStream output : outputs) {
			output.writeObject(str);
		}
	}
	*/
}
