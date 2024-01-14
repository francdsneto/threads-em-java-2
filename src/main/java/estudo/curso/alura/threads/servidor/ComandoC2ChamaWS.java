package estudo.curso.alura.threads.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWS implements Callable<String> {
    private final PrintStream respostaServidor;

    public ComandoC2ChamaWS(PrintStream respostaServidor) {
        this.respostaServidor = respostaServidor;
    }

    @Override
    public String call() throws Exception {

        System.out.println("Servidor recebeu comando c2 - WS");

        respostaServidor.println("Executando comando c2 - WS");

        Thread.sleep(25000);

        int numero = new Random().nextInt(100);

        System.out.println("Servidor finalizou o comando c2 - WS");

        return String.valueOf(numero);
    }

}
