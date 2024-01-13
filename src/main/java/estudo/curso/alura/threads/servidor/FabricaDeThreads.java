package estudo.curso.alura.threads.servidor;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {

    private ThreadFactory defaultFactory;

    public FabricaDeThreads(ThreadFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }

    //private static int numero = 1;
    @Override
    public Thread newThread(Runnable r) {

        /**
         * Usando a Default ThreadFactory para criar a thread
         */
        //Thread thread = new Thread(r, "Thread Servidor Tarefas " + numero);
        //numero++;
        Thread thread = this.defaultFactory.newThread(r);
        thread.setUncaughtExceptionHandler(new TratadorDeException());
        thread.setDaemon(true);
        return thread;
    }
}
