package me.destro.foxviz.api;

import com.github.javafaker.Faker;
import io.reactivex.Observable;
import me.destro.foxviz.model.AiWord;
import me.destro.foxviz.model.Tweet;
import me.destro.foxviz.utilities.MathUtilities;

public class AiApiHelper {
    private Faker faker;
    private String categories[] = {"a", "b", "c"};

    public AiApiHelper() {
        this.faker = new Faker();
    }

    public Observable<AiWord> generateWord() {
        return Observable.create(emitter -> {
            while (true) {
                Thread.sleep(1000 * MathUtilities.random(1, 2));
                emitter.onNext(generateFake());
            }
        });
    }

    private AiWord generateFake() {
        int index = MathUtilities.random(0, 3);
        String word = faker.lorem().word();
        String category = categories[index];
        AiWord aiWord = new AiWord();
        aiWord.word = word;
        aiWord.category = category;
        return aiWord;
    }
}
