package io.aby.moviecatalogservice.resources;

import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.aby.moviecatalogservice.models.CatalogItem;
import io.aby.moviecatalogservice.models.Movie;
import io.aby.moviecatalogservice.models.Rating;
import io.aby.moviecatalogservice.models.UserRating;
import io.aby.moviecatalogservice.services.MovieInfo;
import io.aby.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        //get all rated movie IDs
        UserRating ratings = userRatingInfo.getUserRating(userId);

        return ratings.getUserRating().stream().map(rating->  movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());

    }


}


//for each movie ID, call movie info service and get details

//Put them all together

         /*
                    Movie movie = webClientBuilder.build()
                            .get()
                            .uri("Http://localhost:8082/movies/"+ rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();

                     */