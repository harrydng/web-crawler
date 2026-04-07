import java.io.*;
import java.net.*;
import java.util.*;

public class Crawler {
    private String server;
    private int port;
    private String username;
    private String password;

    public Crawler(String server, int port, String username, String password) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void run() {
        String request = "GET / HTTP/1.0\r\n\r\n";

        System.out.println("Request to " + server + ":" + port);
        System.out.println(request);

        try (Socket socket = new Socket(server, port)) {
            OutputStream out = socket.getOutputStream();
            out.write(request.getBytes("US-ASCII"));
            out.flush();

            InputStream in = socket.getInputStream();
            byte[] data = new byte[1000];
            int bytesRead = in.read(data);
            if (bytesRead != -1) {
                String response = new String(data, 0, bytesRead, "US-ASCII");
                System.out.println("Response:\n" + response);
            } else {
                System.out.println("Response:");
                System.out.println("Socket closed by " + this.server);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String server = "fakebook.khoury.northeastern.edu";
        int port = 443;
        List<String> positionalArgs = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-s")) {
                if (i + 1 < args.length) {
                    server = args[++i];
                } else {
                    System.err.println("Error: -s requires a server address.");
                    return;
                }
            } else if (args[i].equals("-p")) {
                if (i + 1 < args.length) {
                    try {
                        port = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException e) {
                        System.err.println("Error: -p requires an integer port number.");
                        return;
                    }
                } else {
                    System.err.println("Error: -p requires a port number.");
                    return;
                }
            } else {
                positionalArgs.add(args[i]);
            }
        }

        if (positionalArgs.size() < 2) {
            System.err.println("Usage: java Crawler [-s server] [-p port] username password");
            return;
        }

        String username = positionalArgs.get(0);
        String password = positionalArgs.get(1);

        Crawler crawler = new Crawler(server, port, username, password);
        crawler.run();
    }
}
