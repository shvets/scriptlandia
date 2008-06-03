package osgitut.movies.impl;
 
import osgitut.movies.*;
 
public class BasicMovieFinderImpl implements MovieFinder {
  private static final Movie[] MOVIES = new Movie[] {
    new Movie("The Godfather", "Francis Ford Coppola"),
    new Movie("Spirited Away", "Hayao Miyazaki")
  };
 
  public Movie[] findAll() { return MOVIES; }
}
