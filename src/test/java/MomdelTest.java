import com.example.sample_news.model.News;
import com.example.sample_news.model.NewsDAO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Log4j2
public class MomdelTest {
    private NewsDAO newsDAO = new NewsDAO();


    @Test
    public void testConn(){
        Connection connection = newsDAO.open();
        log.info(connection);
    }

    @Test
    public void testInsertNews() throws SQLException {

       for(int i = 0; i<9; i++){
           News news = new News();
           news.setTitle("titleTest" +i);
           news.setImg("ImgTest" +i);
           news.setContent("ContentTest" +i);

           newsDAO.insertNews(news);
       }
    }

    @Test
    public void testSelectAll() throws SQLException {
        List<News> list = newsDAO.selectAll();

        for(News ll : list){
            log.info(ll);
        }
    }

    @Test
    public void testSelectOne() throws SQLException{
        News news = new News();
        news = newsDAO.selectOne(2);

        log.info(news);
    }

    @Test
    public void testDeleteNews() throws SQLException{
        newsDAO.deleteNews(10);
    }
}
