package com.chainsys.bookMyShow.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.bookMyShow.model.MovieDetails;

public class ShowDetailsMapper implements RowMapper<MovieDetails> {
    public MovieDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        MovieDetails details = new MovieDetails();
        details.setShowtimeId(rs.getInt("showtime_id"));
        details.setMovieId(rs.getInt("movie_id"));
        details.setTitle(rs.getString("title"));
        details.setGenre(rs.getString("genre"));
        details.setBase64(Base64.getEncoder().encodeToString(rs.getBytes("image_url")));
        details.setTheaterId(rs.getInt("theater_id"));
        details.setCityId(rs.getInt("city_id"));
        details.setTheaterName(rs.getString("theater_name"));
        details.setShowDate(rs.getString("show_date"));
        details.setShowTime(rs.getString("show_time"));
        details.setTheaterDeleteUser(rs.getString("theater_delete_user"));
        details.setAdminId(rs.getInt("admin_id"));
        return details;
    }
}
