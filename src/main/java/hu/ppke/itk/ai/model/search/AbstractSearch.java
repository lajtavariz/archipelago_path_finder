package hu.ppke.itk.ai.model.search;

import hu.ppke.itk.ai.model.MapModel;

import static hu.ppke.itk.ai.config.Config.DEFAULT_SLEEP_TIME;

public abstract class AbstractSearch implements Runnable {
    protected MapModel mapModel;
    private volatile boolean running = true;
    protected int sleepTime = DEFAULT_SLEEP_TIME;
    private int nrOfSteps = 0;

    public AbstractSearch(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public void terminate() {
        running = false;
    }

    public void run() {
        try {
            while (running) {
                Thread.sleep(sleepTime);
                makeStep();
            }
        } catch (InterruptedException exc) {
            System.err.println(exc);
            running = false;
        }
    }

    protected abstract void makeStep() throws InterruptedException;

    protected void increaseNrOfSteps() {
        nrOfSteps++;
    }

    public AbstractSearch setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    public int getNrOfSteps() {
        return nrOfSteps;
    }
}
