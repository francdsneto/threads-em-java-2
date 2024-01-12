package estudo.curso.alura.threads.servidor;

import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {
    private final Socket socket;

    public DistribuirTarefas(Socket socket
    ) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Dristribuindo tarefas para " + socket);

            Scanner entradaCliente = new Scanner(socket.getInputStream());

            while(entradaCliente.hasNextLine()) {
                String comando = entradaCliente.nextLine();
                System.out.println(comando);
            }

            entradaCliente.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
