package estudo.curso.alura.threads.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {
    private ServidorTarefas servidor;
    private Socket socket;

    public DistribuirTarefas(ServidorTarefas servidor, Socket socket) {
        this.servidor = servidor;
        this.socket = socket;
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
                    case "c1", "c2", "c3": {
                        respostaServidor.println("Comando "+comando+" recebido");
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
