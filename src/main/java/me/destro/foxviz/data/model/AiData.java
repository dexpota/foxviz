package me.destro.foxviz.data.model;

import com.squareup.moshi.Json;

import java.util.List;


public class AiData {
    @Json(name = "Top350")
    public List<TopWord> top350;
}
