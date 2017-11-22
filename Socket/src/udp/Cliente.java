package udp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

public class Cliente {

	public static void main(String[] args) throws UnknownHostException {
		
		// endereço do servidor
		String servidor = "127.0.0.1";
		int porta = 3333;
		
		if (args.length >= 1) {
			servidor = args [0];
		}if (args.length == 2) {
			porta = Integer.valueOf(args[1]);
		}
		
		try {
			DatagramSocket socket = new DatagramSocket();
			byte [] buffer = new byte[8];
			InetAddress end = InetAddress.getByName(servidor);
			DatagramPacket pct = new DatagramPacket(buffer, buffer.length, end, porta);
			System.out.println("Enviando solicitação de data para " +end.toString());
			try {
				socket.send(pct);
			} catch (IOException e) {
				System.out.println("Erro: envio de mensagem para servidor" +e.getMessage());
			}
			pct = new DatagramPacket(buffer, buffer.length);
			System.out.println("Aguardando data do servidor");
			try {
				socket.receive(pct);
			} catch (Exception e) {
				System.out.println("Erro: recebimento de mensagem do servidor!" +e.getMessage());
			}
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(buffer));
			try {
				Date data = new Date(dis.readLong());
				System.out.println("Data recebida: "+data);
			} catch (IOException e) {
				System.out.println("Erro: conversão da data!" +e.getMessage());
			}
			
		} catch (SocketException e) {
			System.out.println("Erro: criação do socket" +e.getMessage());
		}

	}

}
