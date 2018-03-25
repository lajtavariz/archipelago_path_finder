package hu.ppke.itk.mi;

import asg.cliche.Command;
import asg.cliche.ShellFactory;

import java.io.IOException;

public class App
{

    MapGenerator mapGenerator;

    public App() {
        mapGenerator = new MapGenerator();
        mapGenerator.setTitle("MapGenerator");
        mapGenerator.setVisible(true);
    }

    @Command
    public void changeThreshold(float threshold){
        mapGenerator.changeThreshold(threshold);
    }

    @Command
    public void changeFeatureSize(String featureSize){
        mapGenerator.changeFeatureSize(Byte.valueOf(featureSize));
    }

    public static void main(String[] args ) throws IOException
    {
        ShellFactory.createConsoleShell("app", "", new App())
                .commandLoop();
    }
}
