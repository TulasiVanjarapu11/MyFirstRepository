package org.example.service;

import jakarta.transaction.Transactional;
import org.example.Exception.InvalidDataException;
import org.example.Exception.NotFoundException;
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
            throw new InvalidDataException("Invalid movie :null");
        }
       return movieRepo.save(movie);
    }
    public Movie read(long id){
       return movieRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("Movie not found " + "ID="+id));
    }

    public void update(long id, Movie update){
        if(update ==  null || id <= 0) {
            throw new InvalidDataException("Invalid movie:null");
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
           throw new NotFoundException("Movie not found " + "ID=" +id);
       }
    }

    public void delete(long id){
        if(movieRepo.existsById(id)){
            movieRepo.deleteById(id);
        }
        else {
            throw new NotFoundException("Movie not found " + "ID=" +id);
        }
    }
}
