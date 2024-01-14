package estudo.curso.alura.threads.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBanco implements Callable<String> {
    private final PrintStream respostaServidor;

    public ComandoC2AcessaBanco(PrintStream respostaServidor) {
        this.respostaServidor = respostaServidor;
    }

    @Override
    public String call() throws Exception {

        System.out.println("Servicor recebeu comando c2 - Banco");

        respostaServidor.println("Executando comando c2 - Banco");

        Thread.sleep(25000);

        int numero = new Random().nextInt(100) + 1;

        System.out.println("Servidor finalizou o comando c2 - Bnco");

        return String.valueOf(numero);
    }

}
