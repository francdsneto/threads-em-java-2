package estudo.curso.alura.threads.servidor;

import java.io.PrintStream;

public class ComandoC2 implements Runnable{
    private final PrintStream respostaServidor;

    public ComandoC2(PrintStream respostaServidor) {
        this.respostaServidor = respostaServidor;
    }

    @Override
    public void run() {
        respostaServidor.println("Executando comando c2");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Exception no comando c2");

        //respostaServidor.println("Comando c2 executado com sucesso!");
    }
}
