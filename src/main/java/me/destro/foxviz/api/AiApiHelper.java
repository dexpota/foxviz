package me.destro.foxviz.api;

import com.github.javafaker.Faker;
import io.reactivex.Observable;
import me.destro.foxviz.model.Tweet;
import me.destro.foxviz.utilities.MathUtilities;

public class AiApiHelper {
    private Faker faker;

    public AiApiHelper() {
        this.faker = new Faker();
    }

    public Observable<String> generateWord() {
        return Observable.create(emitter -> {
            while (true) {
                Thread.sleep(1000 * MathUtilities.random(1, 2));
                emitter.onNext(generateFake());
            }
        });
    }

    private String generateFake() {
        return faker.lorem().word();
    }
}
