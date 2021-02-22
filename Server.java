package finalexam_server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Server
{ 
	private ArrayList<PrintWriter> clientOutputStreams;
	HashMap<String, Double> prices;
	public void setUpNetworking() throws Exception { 
		prices = new HashMap<String, Double>();
			try {  
				clientOutputStreams = new ArrayList<PrintWriter>();
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket(4000); 
				while (true) { 
					Socket socket = serverSocket.accept(); 
					PrintWriter writer = new PrintWriter(socket.getOutputStream());
					clientOutputStreams.add(writer);
					for(Map.Entry<String, Double> entry: prices.entrySet()) {
						writer.println(entry.getKey()+","+entry.getValue());
						writer.flush();
					}
					new Thread(new ClientHandler(socket)).start();
				} 
			} 
			catch(Exception ex) { 
				System.out.println();
			}

	}

	class ClientHandler implements Runnable {
		private BufferedReader reader;

		public ClientHandler(Socket clientSocket) throws IOException {
			Socket sock = clientSocket;
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		}

		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
//					System.out.println("read " + message);
					
					notifyClients(message);
				}
			} catch (Exception e) {
				System.out.println();
			}
		}
	}
	private void notifyClients(String message) {
		String[]arr = message.split(",");
		prices.put(arr[2],Double.parseDouble(arr[0]));
		for (PrintWriter writer : clientOutputStreams) {
			writer.println(message);
			writer.flush();
		}
	}
	public static void main(String[] args) {
		try {
			new Server().setUpNetworking();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}