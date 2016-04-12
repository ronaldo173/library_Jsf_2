package ua.ronaldo173.library.web.controllers;

import ua.ronaldo173.library.web.beans.Genre;
import ua.ronaldo173.library.web.db.Database;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 11.04.2016.
 */
@ManagedBean
@ApplicationScoped
public class GenreController implements Serializable {
    private List<Genre> genreList;

    public GenreController() {
        fillGenresAll();
    }

    public static void main(String[] args) {
        List<Genre> genreList = new GenreController().genreList;
        for (Genre genre : genreList) {
            System.out.println(genre);
        }
    }

    private void fillGenresAll() {

        ResultSet resultSet = null;
        genreList = new ArrayList<>();
        String query = "select * from genre order by name";

        try {
            resultSet = Database.getResultByQuery(query);

            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getLong("id"));
                genre.setName(resultSet.getString("name"));

                genreList.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.getStatement().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<Genre> getGenreList() {
        return genreList;
    }
}
