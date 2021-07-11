package com.gianvittorio.kakfa.examples.top3sports.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ClicksByNewsType implements Comparable<ClicksByNewsType> {

    @JsonProperty("NewsType")
    private String newsType;
    @JsonProperty("Clicks")
    private Long clicks;

    @Override
    public int compareTo(@NotNull ClicksByNewsType that) {
        final int result = that.getClicks().compareTo(this.getClicks());
        if (result != 0) {
            return result;
        }

        return this.getNewsType().compareTo(that.getNewsType());
    }
}