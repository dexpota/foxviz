package me.destro.foxviz.api;

import com.github.javafaker.Faker;
import io.reactivex.Observable;
import me.destro.foxviz.model.Tweet;
import me.destro.foxviz.utilities.MathUtilities;

public class TwitterApiHelper {
    private Faker faker;

    public TwitterApiHelper() {
        this.faker = new Faker();
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
