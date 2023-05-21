
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface, Runnable {
    ChatServerInterface chatServer;
    String name = null;

    protected ChatClient(String name, ChatServerInterface chatServer) throws RemoteException {
        this.name = name;
        this.chatServer = chatServer;
        chatServer.registerChatClient(this);
    }

    public void retrieveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        String message;
        while (true) {
            message = sc.nextLine();
            try {
                chatServer.broadcastMessage(name + " : " + message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
