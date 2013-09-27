package Goodsub;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import connection.Connection;
import connection.TextFrame;

public class SyncServer3 {

	public static void main(String[] args) {
		new SyncServer3().run();
	}

	private TextFrame frame = new TextFrame();

	public void run() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 300, 500);
		frame.open();
		try (ServerSocket serverSock = new ServerSocket(10000)) {
			while (true) {
				frame.println("waiting new client..");
				final Socket sock = serverSock.accept();
				frame.println("accepted..");
				Thread th = new Thread() {
					public void run() {
						Connection conn = new Connection(sock);
						newConnectionOpened(conn);
					}
				};
				th.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void newConnectionOpened(Connection conn) {
		try {
			frame.println("opened");
			conn.shakehandForServer();
			frame.println("established");
			System.out.println(conn.read());
			conn.close();
			frame.println("closed");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
