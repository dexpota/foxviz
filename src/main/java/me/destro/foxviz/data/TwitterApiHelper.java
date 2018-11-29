package me.destro.foxviz.data;

import com.github.javafaker.Faker;
import com.squareup.moshi.Moshi;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.destro.foxviz.Configuration;
import me.destro.foxviz.data.model.TopWord;
import me.destro.foxviz.data.model.Tweet;
import me.destro.foxviz.utilities.MathUtilities;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TwitterApiHelper {
    private Faker faker;
    public TwitterService service;
    private static Moshi moshi = new Moshi.Builder().build();

    public TwitterApiHelper() {
        this.faker = new Faker();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.twitterServer)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(TwitterService.class);

        Observable
                .create((emitter) -> {
                    List<TopWord> top50Words = AiDataStorage.getTop50Words();

                    if (top50Words != null && top50Words.size() > 0) {
                        List<String> sList = new ArrayList<String>();
                        for (TopWord top : top50Words) {
                            sList.add(top.word);
                        }

                        String str = sList.stream().collect(Collectors.joining("/"));
                        service.search(str).blockingSubscribe();
                    }
                    emitter.onComplete();
                })
                .retryWhen(f -> f.delay(20, TimeUnit.MILLISECONDS))
                .repeatWhen(completed -> completed.delay(20, TimeUnit.SECONDS))
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                    System.out.println("Tweeets");
                }, error -> {
                    System.out.println("Error sending top words.");
                });

        filteredTweet()
                .retry()
                .repeatWhen(completed -> completed.delay(20, TimeUnit.SECONDS))
                .subscribeOn(Schedulers.computation())
                .flatMapIterable(list -> list)
                .map(item -> item.text)
                .toList()
                .subscribe(tweets -> {
                    System.out.println(String.format("Selected tweets %d", tweets.size()));
                    TweetsDataStorage.set(tweets);
                }, error -> {
                    System.out.println("Error! retrieving tweets");
                });

        filteredTweet()
                .flatMapIterable(list -> list)
                .map(item -> item.text)
                .toList()
                .repeatWhen(completed -> completed.delay(20, TimeUnit.SECONDS))
                .subscribeOn(Schedulers.io()).subscribe(tweets -> TweetsDataStorage.set(tweets));
    }

    public Observable<List<Tweet>> filteredTweet() {
        return service.list();
    }

    public Observable<Tweet> searchTweets() {
        return io.reactivex.Observable.create(emitter -> {
            while (true) {
                Thread.sleep(1000 * MathUtilities.random(1, 9));
                emitter.onNext(generateFake());
            }
        });
    }

    private Tweet generateFake() {
        Tweet t = new Tweet();


        int num = MathUtilities.random(1, 1);

        String text = "";
        for (int i=0; i<num; ++i) {
            int r = MathUtilities.random(10, 256);
            text += faker.lorem().fixedString(r) + "\n\n\n";
        }

        t.text = text;
        return t;
    }
}
