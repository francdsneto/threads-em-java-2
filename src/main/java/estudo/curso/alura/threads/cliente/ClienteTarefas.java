package estudo.curso.alura.threads.cliente;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 12345);

        System.out.println("Conexão Estabelecida.");

        Thread threadEnviaComando = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    System.out.println("Pode enviar comandos!");
                    PrintStream saida = new PrintStream(socket.getOutputStream());

                    Scanner teclado = new Scanner(System.in);

                    while(teclado.hasNextLine())
                    {
                        String linha = teclado.nextLine();
                        saida.println(linha);
                        if(linha.trim().equalsIgnoreCase("sair"))
                        {
                            break;
                        }
                    }

                    saida.close();
                    teclado.close();
                }
                catch(Exception e)
                {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread threadRecebeRespostaServidor = new Thread(() -> {
            try
            {
                System.out.println("Recebendo dados do servidor");

                Scanner respostaServidor = new Scanner(socket.getInputStream());

                while(respostaServidor.hasNextLine()) {
                    String comando = respostaServidor.nextLine();
                    System.out.println(comando);
                }

                respostaServidor.close();
            }
            catch(Exception e)
            {
                throw new RuntimeException(e);
            }
        });

        threadEnviaComando.start();
        threadRecebeRespostaServidor.start();

        /**
         * Esse comando faz com que a Thread main espere até essa thread terminar para continuar
         * e só após essa thread terminar o socket será fechado.
         */
        threadEnviaComando.join();

        System.out.println("Fechando socket do cliente!");
        socket.close();

    }

}
