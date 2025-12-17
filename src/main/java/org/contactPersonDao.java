package org;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class contactPersonDao {

    private final DataSource dataSource = DataSourceFactory.getDataSource();

    public Connection getConnection() throws Exception
    {
        return dataSource.getConnection();
    }

    public List<contactperson>  getContacts()
    {
        List<contactperson> contacts = new ArrayList<>();
        String sql = " SELECT * FROM contact_person";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                contactperson person = new contactperson(

                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );

                contacts.add(person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;

    }



    public  void  addContact(contactperson person)
    {
        String sql = "INSERT INTO contact_person (name, email, phone) VALUES(?,?,?)";

        try( Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1,person.getName());
            ps.setString(2,person.getEmail());
            ps.setString(3,person.getPhone());

            ps.executeUpdate();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addContactsBatch(List<contactperson> contacts) throws SQLException
    {
        String sql = """
                INSERT INTO contact_person(name, email, phone)
                                    VALUES (?, ?, ?)
                """;
        Connection conn = dataSource.getConnection();PreparedStatement ps = conn.prepareStatement(sql);
        try
        {
            conn.setAutoCommit(false);

            for (contactperson c : contacts)
            {
                ps.setString(1,c.getName());
                ps.setString(2,c.getEmail());
                ps.setString(3,c.getPhone());

                ps.addBatch();

            }

            ps.executeBatch();
            conn.commit();

            System.out.println("Batch inserted successfully ✔");
        }
        catch (Exception e)
        {

            conn.rollback();
            System.out.println("Transaction rolled back ❌");
        }
    }

}

