package ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Movie;
import org.example.repository.MovieRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest(classes = org.example.MovieServiceApplication.class)
@AutoConfigureMockMvc
class MovieControllerIntTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MovieRepo movieRepo;

    @BeforeEach
    void cleanUp() {
        movieRepo.deleteAll();
    }

    @Test
    void givenMovie_whenCreateMovie_thenReturnSavedMovie() throws Exception {

        // Given
        Movie movie = new Movie();
        movie.setName("rrr");
        movie.setHero("ss rajamouli");
        movie.setActors(List.of("ntr", "ramcharan", "aliabhatt"));

        //When create movie
        var response = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        //Then verify saved movie
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                //.andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }

    @Test
    void givenMovieId_whenFetchMovie_thenReturnMovie() throws Exception {
        // Given
        Movie movie = new Movie();
        movie.setName("rrr");
        //movie.setDirector("ss rajamouli");
        movie.setActors(List.of("ntr", "ramcharan", "aliabhatt"));

        Movie savedMovie = movieRepo.save(movie);

        // When
        var response = mockMvc.perform(get("/movies/" + savedMovie.getId()));

        //Then verify saved movie
        response.andDo(print())
                .andExpect(status().isOk())
               // .andExpect(jsonPath("$.id", is(savedMovie.getId().intValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                //.andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }


    @Test
    void givenSavedMovie_WhenUpdateMovie_thenMovieUpdatedInDb() throws Exception {

        // Given
        Movie movie = new Movie();
        movie.setName("rrr");
        movie.setHero("Ramcharan");
        movie.setHeroin("Alia batt");
        movie.setActors(List.of("ntr", "ramcharan", "aliabhatt"));

        Movie savedMovie = movieRepo.save(movie);
        Long id = savedMovie.getId();

        // update movie
        movie.setActors(List.of("ntr", "ramcharan", "aliabhatt","shreya"));

        var response = mockMvc.perform(put("/movies/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        //Then verify updated movie
        response.andDo(print())
                .andExpect(status().isOk());

        var fetchResponse = mockMvc.perform(get("/movies/" + id));

        fetchResponse.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.hero",is(movie.getHero())))
                .andExpect(jsonPath("$.heroin",is(movie.getHeroin())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));

    }

    @Test
    void givenMovie_whenDeleteRequest_thenMovieRemovedFromDb() throws Exception {
        // Given
        Movie movie = new Movie();
        movie.setName("rrr");
        //movie.setDirector("ss rajamouli");
        movie.setActors(List.of("ntr", "ramcharan", "aliabhatt"));

        Movie savedMovie = movieRepo.save(movie);
        Long id = savedMovie.getId();

        //Then
        mockMvc.perform(delete("/movies/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(movieRepo.findById(id).isPresent());

    }
}
