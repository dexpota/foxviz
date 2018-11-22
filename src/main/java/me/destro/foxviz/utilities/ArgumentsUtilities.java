package me.destro.foxviz.utilities;

import me.destro.foxviz.cli.Arguments;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class ArgumentsUtilities {
    private static ArgumentParser buildArgumentParser() {
        ArgumentParser parser = ArgumentParsers.newFor("prog").build()
                .description("Process some integers.");

        parser.addArgument("--width")
                .dest("width")
                .required(true)
                .type(Integer.class)
                .help("Width of the screen.");

        parser.addArgument("--height")
                .dest("height")
                .required(true)
                .type(Integer.class)
                .help("Height of the screen.");

        return parser;
    }

    public static Arguments parseArguments(String[] args) {
        ArgumentParser parser = ArgumentsUtilities.buildArgumentParser();

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        return new Arguments(ns.getInt("width"), ns.getInt("height"));
    }
}
