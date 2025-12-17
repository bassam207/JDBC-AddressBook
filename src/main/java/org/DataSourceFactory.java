package org;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Properties;

public class DataSourceFactory {

    private static DataSource dataSource;

    public  static DataSource getDataSource()
    {
        try
        {
            Properties pros = new Properties();
            pros.load(DataSourceFactory.class.getClassLoader().getResourceAsStream("db.properties"));

            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setURL(pros.getProperty("db.url"));
            ds.setUser(pros.getProperty("db.user"));
            ds.setPassword(pros.getProperty("db.password"));

            dataSource = ds;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}
