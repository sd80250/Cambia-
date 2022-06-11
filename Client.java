import java.net.*;
import java.io.*;

public class Client {
	private Socket socket = null;
	private PrintWriter out;
	private BufferedReader in;

	public Client(String address, int port) {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");

			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}

		String line = "";

		while (!line.equals("Over")) {
			try {
				line = in.readLine();
				out.println(line);
			} catch (IOException i) {
				System.out.println(i);
			}
		}

		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String[] args) {
		Client client = new Client("127.0.0.1",5000);
	}
}