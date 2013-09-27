package Goodsub;

public class SyncControl {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		new SyncControl().run();
	}
	
	public void run() throws Exception{
		
		SendClient2 sc1 = new SendClient2();
		SendClient2 sc2 = new SendClient2();	
		SendClient2 sc3 = new SendClient2();
		
		new SyncServer2().run();
		sc1.run(10000, "A");
		sc2.run(10001, "B");
		sc3.run(10002, "C");
	}

}
