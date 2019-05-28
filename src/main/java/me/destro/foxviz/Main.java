package me.destro.foxviz;

import com.github.javafaker.Faker;
import com.squareup.moshi.Moshi;
import io.reactivex.Observable;
import me.destro.foxviz.data.*;
import me.destro.foxviz.cli.Arguments;
import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.data.model.TopWord.TopWordAdapter;
import me.destro.foxviz.utilities.ArgumentsUtilities;
import me.destro.foxviz.utilities.Utilities;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class Main extends PApplet {
    public static Arguments arguments;
    public static TwitterApiHelper api;
    //public static AiDataFetcher ai;
    public static AiDataLoader ai;
    //public static PhrasesDataFetcher phrasesDataFetcher;
    public static PhrasesDataLoader phrasesDataLoader;
    public static Logger logger = LogManager.getLogger();
    public static OkHttpClient client = new OkHttpClient();

    public static PFont title;
    public static PFont firstScreen;
    public static PFont secondScreen;

    public static void main(String[] args) {
        arguments = ArgumentsUtilities.parseArguments(args);

        api = new TwitterApiHelper();
        //phrasesDataFetcher = new PhrasesDataFetcher();
        phrasesDataLoader = new PhrasesDataLoader(arguments.tablePhrases);
        //ai = new AiDataFetcher();
        ai = new AiDataLoader(arguments.ai);

        PApplet.main(ProcessingApplication.class, args);
    }

}
