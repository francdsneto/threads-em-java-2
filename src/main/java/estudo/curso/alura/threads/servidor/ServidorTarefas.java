package estudo.curso.alura.threads.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {

    private ServerSocket servidor;
    private ExecutorService threadPool;

    /**
     * O AtomicBoolean tem o mesmo efeito do uso da palavra chave volatile seguido do tipo, 'volatile boolean".
     * O volatile serve para que alterações em um atributo de um objeto sejam refletidos para este atributo
     * dentro da thread onde o objeto está sendo usado. Sem o uso do volatile a thread acessará um cache do objeto
     * em questão, sendo assim, a alteração externa do atributo não reflete no objeto cache usado dentro da thread.
     */
    private AtomicBoolean estaRodando;
    private BlockingQueue<String> fila;

    public ServidorTarefas() throws IOException {
        System.out.println("--- Iniciando Servidor ---");
        this.servidor = new ServerSocket(12345);
        this.threadPool = Executors.newCachedThreadPool(new FabricaDeThreads(Executors.defaultThreadFactory()));  //newCachedThreadPool();
        this.estaRodando = new AtomicBoolean(true);
        this.fila = new ArrayBlockingQueue<>(2);
        iniciarConsumidores();
    }

    private void iniciarConsumidores() {

        for (int i = 0; i < 2; i++) {
            TarefaConsumir tarefa = new TarefaConsumir(fila);
            this.threadPool.execute(tarefa);
        }

    }

    public void rodar() throws IOException {
        while (this.estaRodando.get())
        {
            try {
                Socket socket = servidor.accept();
                System.out.println("Aceitando novo cliente na porta " + socket.getPort());
                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(this, socket, threadPool, fila);
                threadPool.execute(distribuirTarefas);
            } catch (SocketException e) {
                System.out.println("SocketException, está rodando? " + this.estaRodando);
            }
        }
    }

    public void parar() throws IOException {
        this.estaRodando.set(false);
        this.servidor.close();
        this.threadPool.shutdown();
    }

    public static void main(String[] args) throws IOException {
        ServidorTarefas servidor = new ServidorTarefas();
        servidor.rodar();
    }

}
