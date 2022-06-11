import java.net.*;
import java.io.*;
import java.util.Scanner;


public class Client {
	private Socket socket = null;
	private PrintWriter out;
	private BufferedReader in;
	private Scanner sc;

	public void start(String address, int port) {
		try {
			socket = new Socket(address, port);
			sc = new Scanner(System.in);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}

		Thread receiver = new Thread(new Runnable() {
			String command;

			public void run() {
				try {
					while ((command = in.readLine())!=null) {
						if (command.substring(4).equals("ask ")) {
							System.out.println(command.substring(4, command.length()));
							sc = new Scanner(System.in);
							out.println(sc);
						}
						System.out.println("Server: " + command);
					}
					System.out.println("Server closed.");
					out.close();
					socket.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		});

		receiver.start();

		String line = "";

		while (!line.equals("Over")) {
			line = sc.nextLine();
			out.println(line);
		}

		try {
			in.close();
			out.close();
			socket.close();
			sc.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.start("127.0.0.1",5000);
	}
}