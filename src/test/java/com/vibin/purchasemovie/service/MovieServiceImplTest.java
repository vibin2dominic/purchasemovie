package com.vibin.purchasemovie.service;

import com.vibin.purchasemovie.model.Movie;
import com.vibin.purchasemovie.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class MovieServiceImplTest {

    @Autowired
    MovieService movieService;

    // mock
    @MockBean
    private MovieRepository movieRepository;


    @Test
    void addMovie() {
        Movie movie = new Movie("Test Movie", "20 min", "PG", "4pm", "5pm" , 2);
        Movie stubbedResponse = new Movie("Test Movie", "20 min", "PG", "4pm", "5pm" , 2);

        //mock
        when(movieRepository.save(movie)).thenReturn(stubbedResponse);

        //test
        Movie returnedMovie= movieService.addMovie(movie);

        //assertions
        assertEquals("Test Movie", returnedMovie.getTitle());
        assertTrue(movie.equals(returnedMovie));

    }

    @Test
    void findAll(){

        Movie movie = new Movie("Batman", "20 min", "PG", "4pm", "5pm" , 2);
        Movie movie2 = new Movie("Superman", "20 min", "PG", "4pm", "5pm" , 2);

        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies.add(movie2);

        // mock
        when(movieRepository.findAll()).thenReturn(movies);

        // running the actual method
        List<Movie> returnedList = movieService.findAll();

        assertEquals(2, returnedList.size());

        String expectedTitle = "Batman";
        String actualTitle= returnedList.get(0).getTitle();

        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    void findById(){

        Movie movie = new Movie("Batman", "20 min", "PG", "4pm", "5pm" , 2);

        //Mock
        when(movieRepository.findById(10)).thenReturn(Optional.of(movie));

        // running the actual method
        Movie actualMovie = movieService.findById(10);

        assertEquals(movie, actualMovie);
    }

    @Test
    void deleteById(){
        Movie movie = new Movie("Batman", "20 min", "PG", "4pm", "5pm" , 2);
        movie.setId(1);

        // mock
        doNothing().when(movieRepository).deleteById(1);

        movieService.deleteById(1);

        verify(movieRepository, times(1)).deleteById(1);

    }
}