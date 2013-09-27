package Goodsub;

import java.io.*;
import java.net.*;
import java.util.*;

public class SyncServer2 {

	/**
	 * @param args
	 */

	static final int GET_PORT_A = 10000;   // 受信用ポート番号
	static final int GET_PORT_B = 10001;
	static final int GET_PORT_C = 10002;
	static final int SEND_PORT_A = 20000;  // 送信用ポート番号
	static final int SEND_PORT_B = 20001;
	static final int SEND_PORT_C = 20002;
	
	
	private Object obj;
	
	
	// オブジェクトの出力先を格納
	private List<ObjectOutputStream> outputsA = new ArrayList<ObjectOutputStream>();
	private List<ObjectOutputStream> outputsB = new ArrayList<ObjectOutputStream>();
	private List<ObjectOutputStream> outputsC = new ArrayList<ObjectOutputStream>();
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		new SyncServer2().run();
	}
	
	
	void run() throws Exception{

		// A受信
		Thread gtha = new Thread(){

			private ServerSocket serverSocket;

			public void run(){
				try {
					// クライアントとの接続
					serverSocket = new ServerSocket(GET_PORT_A);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("gServer accept");
						new GetClient2().run(SEND_PORT_B, "A from B");
						new GetClient2().run(SEND_PORT_C, "A from C");

						Thread gtha2 = new Thread(){
							public void run(){
								InputStream in;
								try {
									in = sock.getInputStream();
									ObjectInputStream ois = new ObjectInputStream(in);
									while(true){
										send(ois, GET_PORT_A);
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

		// A送信
		Thread stha = new Thread(){
			private ServerSocket serverSocket;

			public void run(){
				try {
					serverSocket = new ServerSocket(SEND_PORT_A);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("sServer accept");

						Thread stha2 = new Thread(){
							public void run(){
								OutputStream out;
								try {
									out = sock.getOutputStream();
									ObjectOutputStream oos = new ObjectOutputStream(out);
									outputsA.add(oos);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						};
						stha2.start();

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

		
		// B受信
		Thread gthb = new Thread(){

			private ServerSocket serverSocket;

			public void run(){
				try {
					// クライアントとの接続
					serverSocket = new ServerSocket(GET_PORT_B);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("gServer accept");
						new GetClient2().run(SEND_PORT_A, "B from A");
						new GetClient2().run(SEND_PORT_C, "B from C");

						Thread gthb2 = new Thread(){
							public void run(){
								InputStream in;
								try {
									in = sock.getInputStream();
									ObjectInputStream ois = new ObjectInputStream(in);
									while(true){
										send(ois, GET_PORT_B);
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
						gthb2.start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// B送信
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
									ObjectOutputStream oos = new ObjectOutputStream(out);
									outputsB.add(oos);
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
		
		
		
		// C受信
		Thread gthc = new Thread(){

			private ServerSocket serverSocket;

			public void run(){
				try {
					// クライアントとの接続
					serverSocket = new ServerSocket(GET_PORT_C);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("gServer accept");
						new GetClient2().run(SEND_PORT_A, "C from A");
						new GetClient2().run(SEND_PORT_B, "C from B");

						Thread gthc2 = new Thread(){
							public void run(){
								InputStream in;
								try {
									in = sock.getInputStream();
									ObjectInputStream ois = new ObjectInputStream(in);
									while(true){
										send(ois, GET_PORT_C);
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
						gthc2.start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// C送信
		Thread sthc = new Thread(){
			private ServerSocket serverSocket;

			public void run(){
				try {
					serverSocket = new ServerSocket(SEND_PORT_C);
					while(true){
						final Socket sock = serverSocket.accept();
						System.out.println("sServer accept");

						Thread sthc2 = new Thread(){
							public void run(){
								OutputStream out;
								try {
									out = sock.getOutputStream();
									ObjectOutputStream oos = new ObjectOutputStream(out);
									outputsC.add(oos);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						};
						sthc2.start();

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
		stha.start();
		gthb.start();
		sthb.start();
		gthc.start();
		sthc.start();
		
	}
	

	// サーバテキストフィールドに表示
	void send(ObjectInputStream sois, int port) throws IOException{
		try {
			obj = sois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(obj instanceof String){
			if(port == GET_PORT_A){
				for (ObjectOutputStream output : outputsA) {
					output.writeObject((String) obj);
				}
			}
			else if(port == GET_PORT_B){
				for (ObjectOutputStream output : outputsB) {
					output.writeObject((String) obj);
				}
			}
			else if(port == GET_PORT_C){
				for (ObjectOutputStream output : outputsC) {
					output.writeObject((String) obj);
				}
			}
		}	
	}
	
}
