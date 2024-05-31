package com.example.test_ms_sql.repo;


import com.example.test_ms_sql.domain.Information;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.engine.jdbc.connections.internal.DriverConnectionCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class ProcedureInformationRepository {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    public List<Information> callStoredProcedure()  {
        List<Information> result = new ArrayList<>();

        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try{
            if (dataSource != null){

                con = dataSource.getConnection();
                cs = con.prepareCall("{call spGetInfo}");
                rs = cs.executeQuery();

                while (rs.next()){
                    result.add(
                            Information.builder()
                                    .id(getId(rs))
                                    .name(rs.getString("NAME"))
                                    .address(rs.getString("ADDRESS"))
                                    .birthday(getBirthday(rs))
                                    .build()
                    );

                }
            }
        }catch (SQLException e){
            logger.severe(e.getMessage());
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }



        return result;
    }

    private Date getBirthday(ResultSet rs) {
        Date result = null;
        try{
            result = rs.getDate("BIRTHDAY");
        }catch (SQLException e){
            logger.severe("getId() :: "+e.getMessage());
        }
        return result;
    }

    private Integer getId(ResultSet rs){
        Integer id = null;
        try{
            id = rs.getInt("ID");
        }catch (SQLException e){
            logger.severe("getId() :: "+e.getMessage());
        }
        return id;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "url","user","pwd"
        );
    }
}
