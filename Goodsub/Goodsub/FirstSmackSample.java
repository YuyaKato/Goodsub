import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
//import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class FirstSmackSample {

	private XMPPConnection connection = null;
	private Chat chat = null;
	private boolean isRunning = true;
	private FirstSmackSample xmpp;

	public static void main(String[] args) {

		new FirstSmackSample().run();
	}

	public void run() {

		String username = null;
		String password = null;
		String buddyname = null;

		xmpp = new FirstSmackSample();

		List<BufferedReader> accounts = new ArrayList<BufferedReader>();
		accounts.add(new BufferedReader(new InputStreamReader(System.in)));
		accounts.add(new BufferedReader(new InputStreamReader(System.in)));
		accounts.add(new BufferedReader(new InputStreamReader(System.in)));

		try {
			username = accounts.get(0).readLine();
			password = accounts.get(1).readLine();
			buddyname = accounts.get(2).readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 接続してチャットを開始
		xmpp.connect("xmpp.jp", username, password);
		xmpp.chatOpen(buddyname);

		// メッセージの送信処理
		while (xmpp.isRunning()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			String message = null;
			try {
				message = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if ("@close".equals(message.trim())) {
				xmpp.chatClose();
			} else {
				xmpp.sendMessage(message);
			}
		}

		// 終了
		xmpp.destroy();
	}

	/* サーバへの接続とログイン */
	public void connect(String server, String username, String password) {
		try {
			// 接続の設定
			SmackConfiguration.setPacketReplyTimeout(5000);
			ConnectionConfiguration config = new ConnectionConfiguration(server);
			config.setSASLAuthenticationEnabled(false);

			// サーバに接続してログインする
			this.connection = new XMPPConnection(config);
			this.connection.connect();
			this.connection.login(username, password);
			if (this.connection.isConnected()) {
				System.out.println("connected");
			}
		} catch (XMPPException ex) {
			ex.printStackTrace();
		}
	}

	/* チャットの開始 */
	public void chatOpen(String buddyId) {
		// ChatManagerを取得し、チャットを開始する
		ChatManager chatManager = this.connection.getChatManager();
		this.chat = chatManager.createChat(buddyId, new MessageListener() {
			/* メッセージを受信したら呼び出される */
			public void processMessage(Chat chat, Message message) {
				if (message.getBody() != null) {
					System.out.println(chat.getParticipant() + ": "
							+ message.getBody());
				}
			}
		});
	}

	/* メッセージの送信 */
	public void sendMessage(String message) {
		try {
			this.chat.sendMessage(message);
		} catch (XMPPException ex) {
			ex.printStackTrace();
		}
	}

	/* チャットが継続中か否か */
	public boolean isRunning() {
		return this.isRunning;
	}

	/* チャットの終了 */
	public void chatClose() {
		this.isRunning = false;
	}

	/* 接続の終了 */
	public void destroy() {
		this.connection.disconnect();
	}

}
