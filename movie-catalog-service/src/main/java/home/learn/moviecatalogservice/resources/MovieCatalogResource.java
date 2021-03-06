package home.learn.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import home.learn.moviecatalogservice.models.CatalogItem;
import home.learn.moviecatalogservice.models.Movie;
import home.learn.moviecatalogservice.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	private RestTemplate restTemplate;
	// for each movie ID, call movie info service and get details

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
//		WebClient.Builder builder = WebClient.builder();
		
		// get all rated movie IDs
		List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new Rating("5678", 3));

		return ratings.stream().map(rating -> {
//			Movie movie0 = builder.build()
//			.get()
//			.uri("http://localhost:8081/movies/" + rating.getMovieId())
//			.retrieve()
//			.bodyToMono(Movie.class)
//			.block();
			
			Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(), "Description", rating.getRating());
		}).collect(Collectors.toList());
	}
}