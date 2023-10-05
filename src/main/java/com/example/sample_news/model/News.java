package com.example.sample_news.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data//겟 셋 다들어가는거
@NoArgsConstructor
@AllArgsConstructor
public class News {
   private int aid;
   private String title;
   private String img;
   private String date;
   private String content;

}
