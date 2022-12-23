package pokemon;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import models.CONSTS;

public class Server {

	public static void main(String[] args) {
		try {
			IService s=new Service();
			System.out.println("created registry at 5555");
			LocateRegistry.createRegistry(5555);
			Naming.rebind(CONSTS.rmiUrl, s);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
