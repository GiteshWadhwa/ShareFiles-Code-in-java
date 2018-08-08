//ckage fileioclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.Scanner;

public class FileIOClient 
{
	@SuppressWarnings("resource")
	public static void main(String[] args) 
	{
		System.out.println("Developed by: Aar Softronix\naarsoftronix@gmail.com\n7696788553");
		System.out.println("\nAsk receiver for IP!. Make sure that receiver is ready!");
		Scanner frmKbd = new Scanner(System.in);
		System.out.println("\nEnter path of file:");
		String filePath = frmKbd.nextLine();
		System.out.println("Enter name and extension of file i.e. abc.jpg");
		String fileName = frmKbd.nextLine();
		System.out.println("Enter receiver's IP: ");
		String ipAddress = frmKbd.nextLine();
		File fileReadLoc = new File(filePath+"\\"+fileName);
		try 
		{
			
			FileInputStream getFileData = new FileInputStream(fileReadLoc);
			Socket clientSocket = new Socket(ipAddress, 1111);
			DataOutputStream sendToNetwork = new DataOutputStream(clientSocket.getOutputStream());
			DataInputStream getFromNetwork = new DataInputStream(clientSocket.getInputStream());
			int dataIndex, byteCounter = 0;
			sendToNetwork.writeInt((int)fileReadLoc.length());
			/*Runtime rt = Runtime.getRuntime();//this is for opening of one screen to another screen 
			rt.getRuntime().exec("cmd /c start control");*/
			while((dataIndex = getFileData.read()) != -1)
			{
				if(getFromNetwork.readUTF().equals("\r"))
				{
					sendToNetwork.writeByte(dataIndex);
					byteCounter++;
					System.out.println("Sending "+(byteCounter/1024)+" KBs of "+(fileReadLoc.length()/1024)+" KBs");
				}
			}
			System.out.println("\n\nFile sent successfully!");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
}