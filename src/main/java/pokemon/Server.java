package pokemon;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import models.CONSTS;

public class Server implements Runnable {

	public static void main(String[] args) {
		Server server=new Server();
		new Thread(server).start();
	}
	public Server() {
		IService s;
		try {
			s = new Service();
			System.out.println("created registry at "+CONSTS.port);
			LocateRegistry.createRegistry(CONSTS.port);
			Naming.rebind(CONSTS.rmiUrl, s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		
		try {
			while(true) {
			Thread.sleep(500);
			System.out.println("serverTest");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
