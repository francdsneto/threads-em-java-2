package estudo.curso.alura.threads.servidor;

import java.io.PrintStream;

public class ComandoC1 implements Runnable{
    private final PrintStream respostaServidor;

    public ComandoC1(PrintStream respostaServidor) {
        this.respostaServidor = respostaServidor;
    }

    @Override
    public void run() {
        respostaServidor.println("Executando comando c1");

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        respostaServidor.println("Comando c1 executado com sucesso!");
    }
}
