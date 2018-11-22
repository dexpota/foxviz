package me.destro.foxviz;

import me.destro.foxviz.cli.Arguments;
import me.destro.foxviz.utilities.ArgumentsUtilities;
import processing.core.PApplet;

public class Main extends PApplet {
    public static Arguments arguments;

    public static void main(String[] args) {
        arguments = ArgumentsUtilities.parseArguments(args);
        PApplet.main(ProcessingApplication.class, args);
    }

}
