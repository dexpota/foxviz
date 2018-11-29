package me.destro.foxviz.data;

import io.reactivex.Observable;
import me.destro.foxviz.data.model.Tweet;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

import java.util.List;

public interface TwitterService {
  @GET("accepted")
  Observable<List<Tweet>> list();

  @GET()
  Observable<ResponseBody> search(@Url String url);
}