package io.aby.moviecatalogservice.resources;

import io.aby.moviecatalogservice.models.CatalogItem;
import io.aby.moviecatalogservice.models.Movie;
import io.aby.moviecatalogservice.models.Rating;
import io.aby.moviecatalogservice.models.UserRating;
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

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){




        //get all rated movie IDs
        UserRating ratings = restTemplate.getForObject("Http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);


        return ratings.getUserRating().stream().map(rating-> {
                    Movie movie = restTemplate.getForObject("Http://localhost:8082/movies/"+ rating.getMovieId(), Movie.class);


                    return new CatalogItem(movie.getName(),"Test", rating.getRating());
                })
                .collect(Collectors.toList());
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

    }
}
