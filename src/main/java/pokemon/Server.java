package pokemon;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import models.CONSTS;
import models.User;

public class Server implements Runnable {
	private ServerSocketChannel serverSocketChannel;
	private Selector selector;
	private ArrayList<Game> games;
	HashMap<SocketChannel,Long > players = new HashMap<SocketChannel,Long>();
	IService s;
	ByteBuffer bb = ByteBuffer.allocate(1024);

	public static void main(String[] args) {
		Server server=new Server();
		new Thread(server).start();
	}
	public Server() {
		try {
			s = new Service();
			System.out.println("created registry at "+CONSTS.port);
			LocateRegistry.createRegistry(CONSTS.port);
			Naming.rebind(CONSTS.rmiUrl, s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			serverSocketChannel=ServerSocketChannel.open();
			
			serverSocketChannel.socket().bind(new InetSocketAddress(CONSTS.socketPort));
			serverSocketChannel.configureBlocking(false);
			selector=Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void run() {
		
		try {
			Iterator<SelectionKey> iter;
			SelectionKey key;
			
			while(this.serverSocketChannel.isOpen()) 
			{
				selector.select();
				iter = selector.selectedKeys().iterator();
				
				while(iter.hasNext()) 
				{
					key = iter.next();
					iter.remove();

					if(key.isAcceptable()) 
						acceptConnection(key);
					
					if(key.isReadable()) 
						readMessage(key);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void readMessage(SelectionKey key)throws Exception {
		SocketChannel sc = (SocketChannel) key.channel();
		StringBuilder sb = new StringBuilder();
		int read=0;
		while(  (read=sc.read(bb)) > 0 ) 
		{
			bb.flip();
			byte[] bytes = new byte[bb.limit()];
			bb.get(bytes);
			sb.append(new String(bytes));
			bb.clear();
		}
		if(read==-1)
		{
			Long closedId=players.get(sc);
			players.remove(sc);
			System.out.println("Client dissconnected: "+closedId );
			System.out.println("Remaining clients: "+players.size() );
			sc.close();
			return;
		}
		System.out.println(sb.toString());
		String[] msg=sb.toString().trim().split(":");
		if(msg[0].equals("LOGIN")) {
			User newUser=new User();
			newUser.setUsername(msg[1].trim());
			newUser.setPassword(msg[2].trim());
			String message;
			User res=s.login(newUser);
			if(res == null) 
			{
				message="BADLOGIN";
				ByteBuffer buff = ByteBuffer.wrap(message.getBytes());
				sc.write(buff);
			}
			else {
				message="ACCEPTED"+":"+res.id;
				players.put(sc,res.id);
				ByteBuffer buff = ByteBuffer.wrap(message.getBytes());
				sc.write(buff);
				
			}
			
		}
		
		
	}
	private void acceptConnection(SelectionKey key) throws Exception {
		SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
		
		String address = (new StringBuilder( sc.socket().getInetAddress().toString() )).append(":").append( sc.socket().getPort() ).toString();
		sc.configureBlocking(false);
	
		System.out.println("adress "+address);
		
		bb.clear();
		int read = 0;
		StringBuilder sb = new StringBuilder();
		
		sc.configureBlocking(true);
		
		while( (read = sc.read(bb)) > 0 ) 
		{
			bb.flip();
			byte[] bytes = new byte[bb.limit()];
			bb.get(bytes);
			sb.append(new String(bytes));
			bb.clear();
			sc.configureBlocking(false);
		}
		
		String message = sb.toString();
		
		System.out.println("message: " + message);
		
		String []words = message.split(":");
		System.out.println(words[0]);
		if(words[0].trim().equals("LOGIN") == false)
		{
			System.out.println("Not Login : ");
			return;
		}
		User newUser=new User();
		newUser.setUsername(words[1].trim());
		newUser.setPassword(words[2].trim());
		
		User res=s.login(newUser);
		if(res == null) 
		{
			message="BADLOGIN";
			ByteBuffer buff = ByteBuffer.wrap(message.getBytes());
			sc.write(buff);
			sc.register(selector, SelectionKey.OP_READ, address);
		}
		else {
			message="ACCEPTED"+":"+res.id;
			players.put(sc,res.id);
			ByteBuffer buff = ByteBuffer.wrap(message.getBytes());
			sc.write(buff);

			sc.register(selector, SelectionKey.OP_READ, address);
			
		}
		
		
		
		
		
	}

}
