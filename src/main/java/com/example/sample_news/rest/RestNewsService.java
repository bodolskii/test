package com.example.sample_news.rest;

import com.example.sample_news.model.News;
import com.example.sample_news.model.NewsDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/news")
public class RestNewsService {
    NewsDAO dao;

    public RestNewsService(){
        dao = new NewsDAO();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)//클라이언트 요청에 포함된 미디어타입을 지정. JSON 을 사용
    public String addNews(News news){//따로 news 타입 객체를 만들어서 하나하나 set 으로 값을 넣지 않아도 변수 값이 데이터 이름과 같으면 set 해줌
        /*뉴스 등록*/
        try{
            dao.insertNews(news);
            //@Consumes 설정에 따라 HTTP Body 에 포합된 JSON 문자열이 자동으로 News로 변환
            //이를 위해서 JSON 문자열의 키와 News 객체의 멤버변수명이 동일해야함
        }catch (Exception e){
            e.printStackTrace();
            return "News API: 뉴스 등록 실패";
        }
        return "News API: 뉴스 등록됨!!!";
    }

    @GET
    @Path("{aid}")
    @Produces(MediaType.APPLICATION_JSON)
    public News getNews(@PathParam("aid") int aid){
        News news = null;
        try{
            news = dao.selectOne(aid);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return news;
        //@Produces 설정으로 News 객체가 JSON 문자열로 출력
    }

    @DELETE
    @Path("{aid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteNews(@PathParam("aid") int aid){
        News news = null;
        try{
            dao.deleteNews(aid);

        }catch (SQLException e){
            e.printStackTrace();
            return "뉴스 목록에서 삭제 실패";
        }
        return "뉴스 목록에서 삭제 성공";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> AllNews() {
        List<News> newsList = new ArrayList<>();

        try{
            newsList = dao.selectAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return newsList;
    }
}
