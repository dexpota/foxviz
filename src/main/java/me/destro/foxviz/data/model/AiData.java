package me.destro.foxviz.data.model;

import com.squareup.moshi.Json;

import java.util.List;
import java.util.Map;


public class AiData {
    @Json(name = "Top350")
    public List<TopWord> top350;

    @Json(name = "Top50")
    public List<TopWord> top50;

    @Json(name = "TablesConnectionsByWords")
    public Map<String, List<Integer>> tablesConnectionsByWord;
}
