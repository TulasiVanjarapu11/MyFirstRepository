package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.model.Movie;
import org.example.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable long id) {
        Movie movie = movieService.read(id);
        log.info("Returned movie with ID :{}" ,id);
        return ResponseEntity.ok(movie);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie createdMovie = movieService.create(movie);
        log.info("Created movie with ID :{}", createdMovie.getId());

        return ResponseEntity.ok(createdMovie);
    }

    @PutMapping("/{id}")
    public void updateMovie(@PathVariable long id, @RequestBody Movie movie) {

        movieService.update(id, movie);
        log.info("Updated movie with ID :{}" ,id);

    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable long id){

        movieService.delete(id);
        log.info("Deleted movie with ID :{}" ,id);

    }

}
