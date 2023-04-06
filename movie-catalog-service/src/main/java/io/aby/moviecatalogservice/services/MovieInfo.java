package io.aby.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.aby.moviecatalogservice.models.CatalogItem;
import io.aby.moviecatalogservice.models.Movie;
import io.aby.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("Http://movie-info-service/movies/"+ rating.getMovieId(), Movie.class);
        return new CatalogItem("Movie Name", " Description in movie-catalog-service/services/movieInfo ", rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating){
        return new CatalogItem("Movie Name Not Found", "No description ", rating.getRating());
    }
}
