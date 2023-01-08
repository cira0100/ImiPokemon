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
import java.util.Map.Entry;

import models.CONSTS;
import models.GameStatus;
import models.User;
import models.UserListWrapper;

public class Server implements Runnable {
	private ServerSocketChannel serverSocketChannel;
	private Selector selector;
	private ArrayList<Game> games=new ArrayList<>();
	HashMap<SocketChannel,Long > players = new HashMap<SocketChannel,Long>();
	ArrayList<SocketChannel> inGame=new ArrayList<SocketChannel>();
	IService s;
	ByteBuffer bb = ByteBuffer.allocate(2048);

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
	private void leftGame(SocketChannel sc)throws Exception {
		if(!players.containsKey(sc))
			return;
		long id=players.get(sc);
		Game game=null;
		for(Game tempGame :games) {
			if(tempGame.player1Id==id || tempGame.player2Id==id) {
				game=tempGame;
				break;
			}
		}
		if(game==null)
			return;
		long notifyId=-1;
		if(game.player1Id==id)
			notifyId=game.player2Id;
		else if(game.player2Id==id)
			notifyId=game.player1Id;
		
		if(notifyId==-1)
			return;
		
		SocketChannel notifySocket=null;
		for(Entry<SocketChannel, Long> player : players.entrySet()) {
			if(player.getValue()==notifyId) {
				notifySocket=player.getKey();
				break;
			}
		}
		if(notifySocket==null)
			return;
		
		inGame.remove(sc);
		inGame.remove(notifySocket);
		games.remove(game);
		ByteBuffer buff = ByteBuffer.wrap("OPPONENTLEFT".getBytes());
		notifySocket.write(buff);
		
		
		
		
	}
	private void readMessage(SelectionKey key)throws Exception {
		SocketChannel sc = (SocketChannel) key.channel();
		StringBuilder sb = new StringBuilder();
		int read=0;
		try {
			while(  (read=sc.read(bb)) > 0 ) 
			{
				bb.flip();
				byte[] bytes = new byte[bb.limit()];
				bb.get(bytes);
				sb.append(new String(bytes));
				bb.clear();
			}
			
		} catch (Exception e) {
			Long closedId=players.get(sc);
			leftGame(sc);
			players.remove(sc);
			System.out.println("Client dissconnected: "+closedId );
			System.out.println("Remaining clients: "+players.size() );
			sc.close();
			sendAvailablePlayers();
			return;
		}
		if(read==-1)
		{
			Long closedId=players.get(sc);
			leftGame(sc);
			players.remove(sc);
			System.out.println("Client dissconnected: "+closedId );
			System.out.println("Remaining clients: "+players.size() );
			sc.close();
			sendAvailablePlayers();
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
			if(res == null ||res.monsterId==0) 
			{
				//ukoliko pogresan login ili nema izabranog pokemona
				message="BADLOGIN";
				ByteBuffer buff = ByteBuffer.wrap(message.getBytes());
				sc.write(buff);
			}
			else {
				if(!res.admin) {
					message="ACCEPTED"+":"+res.id;
					System.out.println(message);
					players.put(sc,res.id);
					ByteBuffer buff = ByteBuffer.wrap(message.getBytes());
					sc.write(buff);
					sendAvailablePlayers();
				}
				else {
					message="BADLOGINADMIN";
					ByteBuffer buff = ByteBuffer.wrap(message.getBytes());
					sc.write(buff);
					
				}
				
			}
			
		}else
		if(msg[0].equals("REQUESTUSERS")) {
			System.out.println("In");
			UserListWrapper wp=new UserListWrapper();
			for(Entry<SocketChannel,Long> player:players.entrySet()) {
				if(!inGame.contains(player.getKey())) {
					User temp=s.getUserById(player.getValue());
					if(temp != null)
						wp.users.add(temp);
				}
				
				
			}
			System.out.println(wp.toString());
			ByteBuffer buff = ByteBuffer.wrap(wp.toString().getBytes());
			sc.write(buff);
			
			
		}else if(msg[0].equals("SELECTOPPONENT")) {
			long opponentId=Long.parseLong(msg[1]);
			SocketChannel opponentSocket=null;
			for(Entry<SocketChannel, Long> player : players.entrySet()) {
				if(player.getValue()==opponentId) {
					opponentSocket=player.getKey();
					break;
				}
			}
			inGame.add(sc);
			inGame.add(opponentSocket);
			sendAvailablePlayers();
			User player1=s.getUserById(players.get(sc));
			User player2=s.getUserById(opponentId);
			System.out.println(player1);
			Game game=new Game();
			game.setPlayer1Id(player1.id);
			game.setPlayer2Id(player2.id);
			game.player1Turn=true;
			game.status=GameStatus.WAITING_FOR_SECOND_PLAYER;
			game.monster1=s.getMonsterViewModel(player1.monsterId);
			game.monster2=s.getMonsterViewModel(player2.monsterId);
			game.currentHp1=game.monster1.hp;
			game.currentHp2=game.monster2.hp;
			game.shield1=0;
			game.shield2=0;
			games.add(game);
			System.out.println(game);
			ByteBuffer buff = ByteBuffer.wrap(("GAMEREQUEST:"+player1.id).getBytes());
			opponentSocket.write(buff);
			
			
		}
		else if(msg[0].equals("ACCEPTOPPONENT")) {
			long opponentId=Long.parseLong(msg[1]);
			SocketChannel opponentSocket=null;
			for(Entry<SocketChannel, Long> player : players.entrySet()) {
				if(player.getValue()==opponentId) {
					opponentSocket=player.getKey();
					break;
				}
			}
			System.out.println(opponentId);
			Game game=null;
			for(Game tempGame :games) {
				if(tempGame.player1Id==opponentId) {
					game=tempGame;
					break;
				}
			}
			game.status=GameStatus.PLAYING;
			ByteBuffer buff = ByteBuffer.wrap(game.toString().getBytes());
			System.out.println(game.toString());
			sc.write(buff);
			buff = ByteBuffer.wrap(game.toString().getBytes());
			opponentSocket.write(buff);
			
			
		}
		else if(msg[0].equals("REFUSEOPPONENT")) {
			long opponentId=Long.parseLong(msg[1]);
			Game game=null;
			for(Game tempGame :games) {
				if(tempGame.player1Id==opponentId) {
					game=tempGame;
					break;
				}
			}
			SocketChannel opponentSocket=null;
			for(Entry<SocketChannel, Long> player : players.entrySet()) {
				if(player.getValue()==opponentId) {
					opponentSocket=player.getKey();
					break;
				}
			}
			
			inGame.remove(sc);
			inGame.remove(opponentSocket);
			games.remove(game);
			ByteBuffer buff = ByteBuffer.wrap("REFUSEGAME".getBytes());
			sc.write(buff);
			ByteBuffer buff1 = ByteBuffer.wrap("REFUSEGAME".getBytes());
			opponentSocket.write(buff1);
			sendAvailablePlayers();
			
			
			
		}
		else if(msg[0].equals("CHATMESSAGE")) {
			long myId=players.get(sc);
			long opponentId=Long.parseLong(msg[1]);
			User myUser=s.getUserById(myId);
			User opponentUser=s.getUserById(opponentId);
			
			String message=msg[2];
			Game game=null;
			for(Game tempGame :games) {
				if(tempGame.player1Id==opponentId || tempGame.player2Id==opponentId) {
					game=tempGame;
					break;
				}
			}
			SocketChannel opponentSocket=null;
			for(Entry<SocketChannel, Long> player : players.entrySet()) {
				if(player.getValue()==opponentId) {
					opponentSocket=player.getKey();
					break;
				}
			}
			String myMsg="MESSAGE:"+myUser.username+":"+message;
			ByteBuffer buff = ByteBuffer.wrap(myMsg.getBytes());
			opponentSocket.write(buff);
			ByteBuffer buff1 = ByteBuffer.wrap(myMsg.getBytes());
			sc.write(buff1);
		}else if(msg[0].equals("GAMEPLAY")) {
			long myId=players.get(sc);
			
			long moveId=Long.parseLong(msg[1]);
			
			Game game=null;
			for(Game tempGame :games) {
				if(tempGame.player1Id==myId || tempGame.player2Id==myId) {
					game=tempGame;
					break;
				}
			}
			long opponentId=-1;
			
			if(game.player1Id==myId) {
				opponentId=game.player2Id;
				game.player1Move(moveId);
			}else {
				opponentId=game.player1Id;
				game.player2Move(moveId);	
			}
			
			SocketChannel opponentSocket=null;
			for(Entry<SocketChannel, Long> player : players.entrySet()) {
				if(player.getValue()==opponentId) {
					opponentSocket=player.getKey();
					break;
				}
			}
			int gameRes=game.checkWin();
			ByteBuffer buff = ByteBuffer.wrap(game.toString().getBytes());
			opponentSocket.write(buff);
			ByteBuffer buff1 = ByteBuffer.wrap(game.toString().getBytes());
			sc.write(buff1);
			
			if(gameRes==1) {
				inGame.remove(sc);
				inGame.remove(opponentSocket);
				games.remove(game);
				System.out.println("GAME FINISHED"+games.size());
				sendAvailablePlayers();
				s.addHistory(game);
			}
			
		}
		
		
	}
	private void acceptConnection(SelectionKey key) throws Exception {
		SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
		
		String address = (new StringBuilder( sc.socket().getInetAddress().toString() )).append(":").append( sc.socket().getPort() ).toString();
		sc.configureBlocking(false);
	
		System.out.println("adress "+address);
		
		bb.clear();
		sc.register(selector, SelectionKey.OP_READ, address);
	}
	public void sendAvailablePlayers() throws Exception{
		System.out.println("PlayerAddedRemovedOrJoinedGame");
		UserListWrapper wp=new UserListWrapper();
		for(Entry<SocketChannel,Long> player1:players.entrySet()) {
			if(!inGame.contains(player1.getKey())) {
				User temp=s.getUserById(player1.getValue());
				if(temp != null)
					wp.users.add(temp);
			}
		}
		for(Entry<SocketChannel, Long> player : players.entrySet()) {
			SocketChannel sc=player.getKey();
			ByteBuffer buff = ByteBuffer.wrap(wp.toString().getBytes());
			sc.write(buff);
			
			
		}
		
	}

}
