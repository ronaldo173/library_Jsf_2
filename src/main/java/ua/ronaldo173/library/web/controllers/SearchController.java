package ua.ronaldo173.library.web.controllers;

import ua.ronaldo173.library.web.beans.Book;
import ua.ronaldo173.library.web.db.Database;
import ua.ronaldo173.library.web.enums.SearchType;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Developer on 11.04.2016.
 */
@ManagedBean(eager = true)
@SessionScoped
public class SearchController implements Serializable {

    private static Map<String, SearchType> searchList = new HashMap<>();
    private SearchType searchType;
    private String searchString;
    private List<Book> currentBookList;

    public SearchController() {
        fillBooksAll();

        ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.put(bundle.getString("author_name"), SearchType.AUTHOR);
        searchList.put(bundle.getString("book_name"), SearchType.TITLE);
    }

    public static void main(String[] args) {

        SearchController searchController = new SearchController();
        searchController.setSearchType(SearchType.AUTHOR);
        searchController.setSearchString("goe");
        searchController.fillBookBySearch();
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    private void fillBooksAll() {
        String query = "select g.*, b.name, b.id as book_id, b.isbn, b.image, b.page_count, b.publish_year, a.fio, p.publisher from (select genre.id, genre.name as genre from genre) g\n" +
                "join book b on b.genre_id=g.id\n" +
                "join author a on a.id=b.author_id \n" +
                "join (select publisher.id, publisher.name as publisher from publisher) p on p.id=b.publisher_id" +
                " order by name";

        fillBooksBySql(query);

    }

    public void fillBooksByGenre() {
        Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer genre_id = Integer.valueOf(parameterMap.get("genre_id"));
        String query = "select g.*, b.name, b.id as book_id, b.isbn, b.image, b.page_count, b.publish_year, a.fio, p.publisher from (select genre.id, genre.name as genre from genre) g\n" +
                "join book b on b.genre_id=g.id\n" +
                "join author a on a.id=b.author_id \n" +
                "join (select publisher.id, publisher.name as publisher from publisher) p on p.id=b.publisher_id where g.id = "
                + genre_id + " order by name";

        fillBooksBySql(query);
    }

    private void fillBooksBySql(String query) {

        ResultSet resultSet = null;
        try {
            resultSet = Database.getResultByQuery(query);
            currentBookList = new ArrayList<>();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("book_id"));
                book.setName(resultSet.getString("name"));
                book.setGenre(resultSet.getString("genre"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setAuthor(resultSet.getString("fio"));
                book.setPageCount(resultSet.getInt("page_count"));
                book.setPublishDate(resultSet.getInt("publish_year"));
                book.setPublisher(resultSet.getString("publisher"));
                book.setImage(resultSet.getBytes("image"));
//                book.setDescr(resultSet.getString("descr"));

                currentBookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.getStatement().getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (Book book : currentBookList) {
            System.out.println(book);
        }
    }

    public void fillBookBySearch() {
        if (searchString.trim().length() == 0) {
            fillBooksAll();
            return;
        }

        StringBuilder query = new StringBuilder("select g.*, b.name, b.id as book_id, b.isbn, b.image, b.page_count, b.publish_year, a.fio, p.publisher from (select genre.id, genre.name as genre from genre) g\n" +
                "join book b on b.genre_id=g.id\n" +
                "join author a on a.id=b.author_id \n" +
                "join (select publisher.id, publisher.name as publisher from publisher) p on p.id=b.publisher_id ");

        switch (searchType) {
            case AUTHOR:
                query.append("where lower(a.fio) like '%");
                break;
            case TITLE:
                query.append("where lower(b.name) like '%");
                break;
        }
        query.append(searchString.toLowerCase()).append("%' order by b.name");

        fillBooksBySql(query.toString());
    }

    public void fillBookByLetter() {
        Map<String , String > params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String searchLetter = params.get("letter");

        StringBuilder query = new StringBuilder("select g.*, b.name, b.id as book_id, b.isbn, b.image, b.page_count, b.publish_year, a.fio, p.publisher from (select genre.id, genre.name as genre from genre) g\n" +
                "join book b on b.genre_id=g.id\n" +
                "join author a on a.id=b.author_id \n" +
                "join (select publisher.id, publisher.name as publisher from publisher) p on p.id=b.publisher_id where substr(b.name,1,1) = '" + searchLetter + "' order by b.name");
        fillBooksBySql(query.toString());
    }

    public byte[] getImage(int id) {

        ResultSet resultSet = null;
        byte[] image = null;
        String query = "select image from book where id = " + id;
        try {
            resultSet = Database.getResultByQuery(query);
            while (resultSet.next()) {
                image = resultSet.getBytes("image");
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
        return image;
    }

    public Character[] getLetters() {
        Character[] letters = new Character[32];
        char a = 'А';
        for (int i = 0; i < 32; i++) {
            letters[i] = a++;
        }
        return letters;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public List<Book> getCurrentBookList() {
        return currentBookList;
    }

    public Map<String, SearchType> getSearchList() {
        return searchList;
    }
}
