package hu.ppke.itk.ai.model.search;

import hu.ppke.itk.ai.model.MapModel;

public abstract class AbstractSearch implements Runnable {
    MapModel mapModel;
    private volatile boolean running = true;

    public AbstractSearch(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public void terminate() {
        running = false;
    }

    public void run() {
        try {
            while (running) {
                Thread.sleep(40);
                makeStep();
            }
        } catch (InterruptedException exc) {
            System.err.println(exc);
            running = false;
        }
    }

    protected abstract void makeStep();
}
