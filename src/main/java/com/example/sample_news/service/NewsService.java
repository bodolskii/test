package com.example.sample_news.service;

import com.example.sample_news.model.News;
import com.example.sample_news.model.NewsDAO;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.List;

@Log4j2
public class NewsService {
    private final NewsDAO newsDAO;

    public NewsService(){
        newsDAO = new NewsDAO();
    }

    private String getFileName(Part part){//파일 이름 넘겨주기
        /*Part 객체로 전달된 이미지 파일로 부터 파일 이름을 추출하기 위한 메소드 */
        String fileName = null;

        //파일 이름이 들어있는 헤더 영역을 가지고 옴.
        String header = part.getHeader("content-disposition");
        log.info("File Header : " + header);

        //파일 이름이 들어있는 속성 부분의 시작위치를 가져와 쌍따음표 사이의 값 부분만 가지고 옴
        int start = header.indexOf("filename=");
        fileName = header.substring(start+10, header.length()-1);
        log.info("파일명 : " + fileName);
        return fileName;
    }

    public void addNews(HttpServletRequest request){//이미지 파일 저장
        /*뉴스 기사 등록하기 위한 요청을 처리하는 메서드*/
        News news = new News();

        try{
            //이미지 파일 저장을 위해 request로 부터 part 객체 참조
            Part part = request.getPart("file");
            String fileName = this.getFileName(part);
            if(fileName != null && !fileName.isEmpty()){//isEmpty() : null 은 아니지만 빈문자열 일경우
                part.write(fileName);//파일 이름이 있으면 파일 저장
            }

            //BeanUtils.populote() : 파라미터로 전달된 nome 속성과 일치하는 News클레스 의 멤버 변수를 찾아 값을 전달
            BeanUtils.populate(news, request.getParameterMap());//

            //이미지파일 이름을 News 객체에 저장
            news.setImg("/upload/"+fileName);//db 에저장 이 된다(news 에 저장되는 값은 db에 저장된다)
            newsDAO.insertNews(news);
        }catch (Exception e){
            log.info(e.getMessage());
            log.info("뉴스 추가 과정에서 문제 발생");
            request.setAttribute("error", "뉴스가 정상적으로 등록되지 않았습니다.");
        }
    }

    public void listNews(HttpServletRequest request){
        /* newsList.jsp 에서 뉴스 목록을 보여주기 위한 요청을 처리하는 메서드*/
        List<News> newsList;
        try{
            newsList = newsDAO.selectAll();
            request.setAttribute("newsList", newsList);
        }catch (Exception e){
            log.info(e.getMessage());
            log.info("뉴스 목록 생성 과정에서 문제 발생");
            request.setAttribute("error", "뉴스가 목록이 정상적으로 처리되지 않았습니다 .");
        }
    }

    public void getNews(HttpServletRequest request){
        /*특정 뉴스 기사를 클랙했을 때 호출하기 위한 요청을 처리하는 메서드*/
        int aid = Integer.parseInt(request.getParameter("aid"));
        try{
            News news = newsDAO.selectOne(aid);
            request.setAttribute("news", news);
        }catch (Exception e){
            log.info(e.getMessage());
            log.info("뉴스 목록 생성 과정에서 문제 발생");
            request.setAttribute("error", "뉴스가 를 정상적으로 가져오지 못헸습니다.");
        }
    }

    public void removeNews(HttpServletRequest request){
        /*뉴스를 삭제하기 위한 메서드*/
        int aid = Integer.parseInt(request.getParameter("aid"));
        try{
            newsDAO.deleteNews(aid);
        }catch (Exception e){
            log.info(e.getMessage());
            log.info("뉴스 목록 삭제 과정에서 문제 발생");
            request.setAttribute("error", "뉴스가 를 정상적으로 삭제하지 못헸습니다.");
        }
    }
}
