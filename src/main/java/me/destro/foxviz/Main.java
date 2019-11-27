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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Main extends PApplet {
    public static Arguments arguments;
    //public static TwitterApiHelper api;
    public static TwitterDataLoader twitterDataLoader;
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

        //api = new TwitterApiHelper();
        twitterDataLoader = new TwitterDataLoader(arguments.twitter);
        //phrasesDataFetcher = new PhrasesDataFetcher();
        phrasesDataLoader = new PhrasesDataLoader(arguments.tablePhrases);
        //ai = new AiDataFetcher();
        //ai = new AiDataLoader(arguments.ai);
        //phrasesDataFetcher = new PhrasesDataFetcher();

        final String poo = "A pile of poo: 💩";
        //final String poo = "💩";
        //final String onlypoo = "\uD83D\uDCA9";

        System.out.println(poo);
        // Length of chars doesn't equals the "real" length, that is: the number of actual codepoints
        System.out.println(poo.length() + " vs " + poo.codePointCount(0, poo.length()));

        int offset = 0;
        Stream.Builder<char[]> builder = Stream.builder();
        while (offset < poo.length()) {
            int i1 = poo.codePointAt(offset);
            offset += Character.charCount(i1);

            builder.add(Character.toChars(i1));
        }

        Stream<char[]> build = builder.build();

        build.forEach(System.out::println);

        poo.codePoints().forEach(System.out::println);

        IntStream.range(0x1F600, 0x1F60F).forEach(integer -> {
            System.out.println(Character.toChars(integer));
        });
        //PApplet.main(ProcessingApplication.class, args);
    }


    class UnicodeIterator{

    }
}
