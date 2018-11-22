package me.destro.foxviz;

import com.github.javafaker.Faker;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.api.TwitterApiHelper;
import me.destro.foxviz.cli.Arguments;
import me.destro.foxviz.utilities.ArgumentsUtilities;
import processing.core.PApplet;


public class Main extends PApplet {
    public static Arguments arguments;
    public static TwitterApiHelper api;

    public static void main(String[] args) {
        arguments = ArgumentsUtilities.parseArguments(args);

        Faker f = new Faker();
        api = new TwitterApiHelper();

        PApplet.main(ProcessingApplication.class, args);
    }

}
