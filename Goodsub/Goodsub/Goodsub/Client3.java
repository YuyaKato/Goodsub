package Goodsub;

import java.net.Socket;

import connection.Connection;

public class Client3 {

	public static void main(String[] args) {
		new Client3().run();
	}

	public void run() {
		try(Socket sock = new Socket("localhost", 10000)){
			Connection conn = new Connection(sock);
			newConnectionOpened(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void newConnectionOpened(Connection conn) {
		try{
			System.out.println("client opened");
			conn.shakehandForClient();
			System.out.println("client established");
			conn.write("hoge");
			conn.close();
			System.out.println("client closed");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
