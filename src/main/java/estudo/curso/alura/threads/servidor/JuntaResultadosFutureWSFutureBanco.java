package estudo.curso.alura.threads.servidor;

import java.io.PrintStream;
import java.util.concurrent.*;

public class JuntaResultadosFutureWSFutureBanco implements Callable<Void> {

    private final Future<String> futureWS;
    private final Future<String> futureBanco;
    private final PrintStream respostaServidor;

    public JuntaResultadosFutureWSFutureBanco(Future<String> futureWS, Future<String> futureBanco, PrintStream respostaServidor) {
        this.futureWS = futureWS;
        this.futureBanco = futureBanco;
        this.respostaServidor = respostaServidor;
    }

    @Override
    public Void call() {

        System.out.println("Aguardando resultados do  future WS e future Banco");

        try {
            String resultadoWS = futureWS.get(20, TimeUnit.SECONDS);
            String resultadoBanco = futureBanco.get(20,TimeUnit.SECONDS);

            this.respostaServidor.println("Resultado comando c2 : " + resultadoWS + ", " + resultadoBanco);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Timeout: Cancelando execução do comando c2");
            this.respostaServidor.println("Timeout na execução do comando C2");
            this.futureWS.cancel(true);
            this.futureBanco.cancel(true);
        }

        System.out.println("Finalizou JuntaResultadosFutureWSFutureBanco");

        return null;
    }
}
