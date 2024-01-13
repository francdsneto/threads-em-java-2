package estudo.curso.alura.threads.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class DistribuirTarefas implements Runnable {
    private ServidorTarefas servidor;
    private Socket socket;
    private ExecutorService threadPool;

    public DistribuirTarefas(ServidorTarefas servidor, Socket socket, ExecutorService threadPool) {
        this.servidor = servidor;
        this.socket = socket;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        try {
            System.out.println("Dristribuindo tarefas para " + socket);

            Scanner entradaCliente = new Scanner(socket.getInputStream());
            PrintStream respostaServidor = new PrintStream(socket.getOutputStream());

            while(entradaCliente.hasNextLine()) {

                String comando = entradaCliente.nextLine();

                switch (comando) {
                    case "c1" : {
                        ComandoC1 c1 = new ComandoC1(respostaServidor);
                        this.threadPool.execute(c1);
                        break;
                    }
                    case "c2" : {
                        ComandoC2 c2 = new ComandoC2(respostaServidor);
                        this.threadPool.execute(c2);
                        break;
                    }
                    case "sair" : {
                        respostaServidor.println("Desligando o servidor");
                        servidor.parar();
                        break;
                    }
                    default: {
                        respostaServidor.println("Comando "+comando+" desconhecido");
                        break;
                    }
                }

                System.out.println(comando);
            }

            entradaCliente.close();
            respostaServidor.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
