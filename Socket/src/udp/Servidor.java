package udp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class Servidor {

	public static void main(String[] args) {
		int porta = 3333; // porta padrão

		if (args.length >= 1) {
			porta = Integer.valueOf(args[0]);
		}

		// 1º passo - criar
		DatagramSocket socket;
		try {
			// 2ºobter o socket
			socket = new DatagramSocket(porta);

			/* buffer para enviar e receber os dados */

			byte[] buffer = new byte[8];
			// 3º cria o datagrampacket
			DatagramPacket pct = new DatagramPacket(buffer, buffer.length);
			// espera e recebe um pacote com a data no buffer
			System.out.println("Esperando mensagem do cliente");
			try {
				socket.receive(pct);
			} catch (IOException e) {
				System.out.println("Erro: recebimento de pacote." + e.getMessage());

			}
			// obter a data local
			Date data = new Date();
			// melhor jeito de utilizar streams
			// como o socket lê byte[], começamos criando um
			// ByteArrayOutpuStream
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			DataOutputStream dos = new DataOutputStream(baos);
			try {
				dos.writeLong(data.getTime());
			} catch (IOException e) {
				System.out.println("Erro: conversão da data para o array" + e.getMessage());
			}
			// mandar o pacote para o cliente
			System.out.println("Enviando data para cliente " + pct.getAddress());
			pct = new DatagramPacket(buffer, buffer.length, pct.getAddress(), pct.getPort());
			try {
				socket.send(pct);
			} catch (IOException e) {
				System.out.printf("Erro: envio de pacote para %s. %s\n", pct.getAddress().toString());
			}
			System.out.println("Pacote enviado para " +pct.getAddress());
			System.out.println("Data envidada: "+data);
		} catch (SocketException e) {

			System.out.println("Erro: criação do socket");
		}

	}

}
