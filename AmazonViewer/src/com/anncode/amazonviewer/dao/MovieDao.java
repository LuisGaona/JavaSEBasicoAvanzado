package com.anncode.amazonviewer.dao;

import static com.ancode.amazonviwer.db.DataBase.ID_TMATERIALS;
import static com.ancode.amazonviwer.db.DataBase.TMOVIE;
import static com.ancode.amazonviwer.db.DataBase.TMOVIE_CREATOR;
import static com.ancode.amazonviwer.db.DataBase.TMOVIE_DURATION;
import static com.ancode.amazonviwer.db.DataBase.TMOVIE_GENRE;
import static com.ancode.amazonviwer.db.DataBase.TMOVIE_ID;
import static com.ancode.amazonviwer.db.DataBase.TMOVIE_TITLE;
import static com.ancode.amazonviwer.db.DataBase.TMOVIE_YEAR;
import static com.ancode.amazonviwer.db.DataBase.TUSER_IDUSUARIO;
import static com.ancode.amazonviwer.db.DataBase.TVIEWED;
import static com.ancode.amazonviwer.db.DataBase.TVIEWED_IDELEMENT;
import static com.ancode.amazonviwer.db.DataBase.TVIEWED_IDMATERIAL;
import static com.ancode.amazonviwer.db.DataBase.TVIEWED_IDUSUARIO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ancode.amazonviwer.db.IDBConnection;
import com.anncode.amazonviewer.model.Movie;


public interface MovieDao extends IDBConnection {

	default Movie setMovieViwed(Movie movie) {
		try(Connection conn=connectToDB()) {
		   Statement statement= conn.createStatement();
		   String query="insert into "+ TVIEWED +
				   "("+TVIEWED_IDMATERIAL+","+TVIEWED_IDELEMENT+","+TVIEWED_IDUSUARIO+")"+
				   "values("+ID_TMATERIALS[0]+","+movie.getId()+","+TUSER_IDUSUARIO+")";
		if(statement.executeUpdate(query)>0) {
			System.out.println("Se marco en Visto");
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return movie;

	}

	default ArrayList<Movie> read() {
		ArrayList<Movie> movies = new ArrayList();
		try(Connection connection=connectToDB()){
			String query="select * from " + TMOVIE;
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()) {
			Movie movie= new Movie(
					rs.getString(TMOVIE_TITLE),
					rs.getString(TMOVIE_GENRE),
					rs.getString(TMOVIE_CREATOR),
					Integer.valueOf(rs.getString(TMOVIE_DURATION)),
					Short.valueOf(rs.getString(TMOVIE_YEAR)));
			       movie.setId(Integer.valueOf(rs.getString(TMOVIE_ID)));
			       movie.setViewed(getMovieViewed(preparedStatement, connection, Integer.valueOf(rs.getString(TMOVIE_ID))));
			       movies.add(movie);
					
			}
		}catch(SQLException e) {
			
		}
		return movies;
	}

	private boolean getMovieViewed(PreparedStatement preparedStatement, Connection conn, int idMovie) {
		boolean viewed=false;
		String query="select * from " + TVIEWED +
				" where "+ TVIEWED_IDMATERIAL + "= ? "+
				"and " + TVIEWED_IDELEMENT + "= ? " +
				"and " + TVIEWED_IDUSUARIO + "= ? ";
		ResultSet rs=null;
		try {
			preparedStatement=conn.prepareStatement(query);
			preparedStatement.setInt(1,ID_TMATERIALS[0]);
			preparedStatement.setInt(2,idMovie);
			preparedStatement.setInt(3,TUSER_IDUSUARIO);
			
			rs=preparedStatement.executeQuery();
			viewed=rs.next();


		}catch(Exception e) {
			e.printStackTrace();
		}
		return viewed;
	}

}
