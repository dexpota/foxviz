package me.destro.foxviz;

import com.github.javafaker.Faker;
import com.squareup.moshi.Moshi;
import io.reactivex.Observable;
import me.destro.foxviz.data.AiDataFetcher;
import me.destro.foxviz.data.DataStorage;
import me.destro.foxviz.data.TwitterApiHelper;
import me.destro.foxviz.cli.Arguments;
import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.data.model.TopWord.TopWordAdapter;
import me.destro.foxviz.utilities.ArgumentsUtilities;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import processing.core.PApplet;

import java.util.concurrent.TimeUnit;


public class Main extends PApplet {
    public static Arguments arguments;
    public static TwitterApiHelper api;
    public static AiDataFetcher ai;
    public static Logger logger = LogManager.getLogger();
    public static OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {
        arguments = ArgumentsUtilities.parseArguments(args);

        api = new TwitterApiHelper();
        ai = new AiDataFetcher();

        ai.fetchData()
                .repeatWhen(completed -> completed.delay(Configuration.aiDataRepeatTime, TimeUnit.SECONDS))
                .retry()
                .subscribe(data -> DataStorage.top350Words.set(data.top350),
                        error -> System.out.println("An error."));

        PApplet.main(ProcessingApplication.class, args);
    }

}
