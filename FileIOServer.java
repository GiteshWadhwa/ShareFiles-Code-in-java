//ckage fileioserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.Scanner;

public class FileIOServer 
{
	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnknownHostException 
	{
		Scanner frmKbd = new Scanner(System.in);
		System.out.println("Developed by: Aar Softronix\naarsoftronix@gmail.com\n7696788553");
		System.out.println("\nEnter name and extension of file i.e. abc.jpg");
		String fileName = frmKbd.nextLine();
		File fileWriteLoc = new File("D:\\"+ fileName);
		String localAddress = InetAddress.getLocalHost().getHostAddress(); 
		System.out.println("Inform sender about IP: "+localAddress);
		System.out.println("Waiting for sender...");
		try 
		{
			FileOutputStream sendDataOut = new FileOutputStream(fileWriteLoc);
			ServerSocket serverSocket = new ServerSocket(1111);
			Socket clientSocket = serverSocket.accept();
			DataInputStream getFromNetwork = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream sendToNetwork = new DataOutputStream(clientSocket.getOutputStream());
			int byteCounter = 0;
			int fileLength = getFromNetwork.readInt();
			while(byteCounter < fileLength)
			{
				sendToNetwork.writeUTF("\r");
				sendDataOut.write(getFromNetwork.readByte());
				byteCounter++;
				System.out.println("Receiving "+(byteCounter/1024)+" KBs of "+(fileLength/1024)+" KBs");
			}
			System.out.println("\n\nFile received successfully!");
			System.out.println("File has been placed in: Local Disk (C:)");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
}