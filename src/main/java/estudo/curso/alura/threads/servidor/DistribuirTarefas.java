package estudo.curso.alura.threads.servidor;

import java.net.Socket;

public class DistribuirTarefas implements Runnable {
    private final Socket socket;

    public DistribuirTarefas(Socket socket
    ) {
        this.socket = socket;
    }

    @Override
    public void run() {

        System.out.println("Dristribuindo tarefas para " + socket);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
