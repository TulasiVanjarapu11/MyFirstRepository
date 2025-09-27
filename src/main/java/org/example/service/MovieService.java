package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Movie;
import org.example.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepo movieRepo;
    //CRUD

    public Movie create(Movie movie) {
        if (movie == null) {
            throw new RuntimeException("Invalid movie");
        }
       return movieRepo.save(movie);
    }
    public Movie read(long id){
       return movieRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Movie not found"));
    }

    public void update(long id, Movie update){
        if(update ==  null || id <= 0) {
            throw new RuntimeException("Invalid movie");
        }
        //check if exist
       if(movieRepo.existsById(id)){
           Movie movie = movieRepo.getReferenceById(id);
           movie.setName(update.getName());
           movie.setHero(update.getHero());
           movie.setHeroin(update.getHeroin());
           movie.setActors(update.getActors());
           movieRepo.save(movie);
       }
       else{
           throw new RuntimeException("Movie not found");
       }
    }

    public void delete(long id){
        if(movieRepo.existsById(id)){
            movieRepo.deleteById(id);
        }
        else {
            throw new RuntimeException("Movie not found");
        }
    }
}
