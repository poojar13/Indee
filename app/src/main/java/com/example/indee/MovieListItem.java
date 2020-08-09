package com.example.indee;

import java.util.ArrayList;

public class MovieListItem {
    public ArrayList<TestData> testDataArrayList;
    public static class TestData{
        public long id;
        public String name;
        public String payment_plan;
        public long release_year;
        public String type;
        public String created_on;
        public String updated_on;
        public String posterLink;
        public String shortDescription;
        public String description;
        public String video_duration;

    }
}
