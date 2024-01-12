package estudo.curso.alura.threads.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

    private ServerSocket servidor;
    private ExecutorService threadPool;
    private boolean estaRodando;

    public ServidorTarefas() throws IOException {
        System.out.println("--- Iniciando Servidor ---");
        this.servidor = new ServerSocket(12345);
        this.threadPool = Executors.newCachedThreadPool();
        this.estaRodando = true;
    }

    public void rodar() throws IOException {
        while (this.estaRodando)
        {
            try {
                Socket socket = servidor.accept();
                System.out.println("Aceitando novo cliente na porta " + socket.getPort());

                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(this, socket);

                threadPool.execute(distribuirTarefas);
            } catch (SocketException e) {
                System.out.println("SocketException, está rodando? " + this.estaRodando);
            }
        }
    }

    public void parar() throws IOException {
        this.estaRodando = false;
        this.servidor.close();
        this.threadPool.shutdown();
    }

    public static void main(String[] args) throws IOException {
        ServidorTarefas servidor = new ServidorTarefas();
        servidor.rodar();
    }

}
