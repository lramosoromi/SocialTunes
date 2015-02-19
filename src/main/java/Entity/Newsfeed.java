package Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skylight on 19/02/2015.
 */
@Entity
@Table(name = "NEWSFEED_TABLE")
public class Newsfeed {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="NEWSFEED_NEWS", joinColumns=@JoinColumn(name="newsfeed_id"))
    @Column(name="news")
    private List<String> news = new ArrayList<>();

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public List<String> getNews() { return news; }
    public void setNews(List<String> news) { this.news = news; }
    public void addNews(String aNews) { news.add(aNews);}
}
