package me.destro.foxviz;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import processing.core.PApplet;

public class Main extends PApplet {

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("prog").build()
                .description("Process some integers.");
        parser.addArgument("--width")
                .dest("width")
                .required(true)
                .type(Integer.class)
                .help("Width of the screen.");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
            ns.getInt("width");
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        PApplet.main(Main.class, args);
    }

    @Override
    public void setup() {
        super.setup();
    }

    @Override
    public void draw() {
        super.draw();

        line(5, 5, 50, 50);
    }
}
