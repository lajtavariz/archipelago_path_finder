package hu.ppke.itk.ai.model;

import hu.ppke.itk.ai.model.search.impl.BFS;

public class MainEvaluator {

    public void start() {
        Evaluator evaluator = new Evaluator();
        new Thread(evaluator).run();
    }

    private class Evaluator implements Runnable {
        MapModel mapModel;
        BFS bfs;

        @Override
        public void run() {
            mapModel = new MapModel();

            float new_threshold = 0.0f;
            for (int i = 0; i <= 10; i++) {
                mapModel.setThreshold(new_threshold);
                mapModel.reGenerateNodes();
                new_threshold = new_threshold + 0.1f;

                int totalSteps = 0;
                bfs = (BFS) new BFS(mapModel).setSleepTime(1);
                try {
                    synchronized (bfs) {
                        for (int j = 1; j <= 10; j++) {
                            mapModel.startComputation(bfs);
                            bfs.wait();
                            totalSteps += bfs.getNrOfSteps();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Average steps for threshold " + mapModel.getThreshold() + ": " + totalSteps / 10);
            }
        }
    }

}
