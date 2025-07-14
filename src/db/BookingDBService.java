package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import dto.Booking;
import dto.Resource;
import dto.User;
import props.PropertiesProvider;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDBService {
    private static final String ADD_USER = "INSERT INTO users (username, password) VALUES(?,?)";
    private static final String READ_USERS = "SELECT id, username FROM users";
    private static final String ADD_RESOURCE = "INSERT INTO resources (name, location) VALUES(?,?)";
    private static final String READ_RESOURCE = "SELECT * FROM resources";
    private static final String ADD_BOOKING = "INSERT INTO bookings (user_id, resource_id, start_time, end_time) VALUES (?, ?, ?, ?)";
    private static final String READ_BOOKING = "SELECT b.id AS booking_id, u.username, r.name AS resource_name, b.start_time, b.end_time, b.created_at FROM bookings b JOIN users u ON b.user_id = u.id JOIN resources r ON b.resource_id = r.id";
    private final MysqlDataSource DS;

    public BookingDBService() {
        DS = new MysqlDataSource();
        DS.setServerName(PropertiesProvider.PROPERTIES.getProperty("host"));
        DS.setPortNumber(Integer.parseInt(PropertiesProvider.PROPERTIES.getProperty("port")));
        DS.setDatabaseName(PropertiesProvider.PROPERTIES.getProperty("db_name"));
        DS.setUser(PropertiesProvider.PROPERTIES.getProperty("uname"));
        DS.setPassword(PropertiesProvider.PROPERTIES.getProperty("pwd"));
    }

    public MysqlDataSource getDS() {
        return DS;
    }

    public void registerUser(String username, String password) {
        try (Connection connection = getDS().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(ADD_USER)){
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already exists.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> showUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DS.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(READ_USERS)){
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        null
                        );
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void addResource(String name, String location) {
        try (Connection connection = getDS().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(ADD_RESOURCE)){
            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Name already exists");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Resource> showResources() {
        List<Resource> resourceList = new ArrayList<>();
        try (Connection connection = DS.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(READ_RESOURCE)){
            while (rs.next()) {
                Resource resource = new Resource(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location")
                        );
                resourceList.add(resource);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resourceList;
    }

    public void createBooking(int userId, int resourceId, LocalDateTime startTime, LocalDateTime endTime) {
        try (Connection connection = getDS().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(ADD_BOOKING)){
            pstmt.setInt(1, userId);
            pstmt.setInt(2, resourceId);
            pstmt.setTimestamp(3, Timestamp.valueOf(startTime));
            pstmt.setTimestamp(4, Timestamp.valueOf(endTime));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> showBookings() {
        List<Booking> bookingList = new ArrayList<>();
        try (Connection connection = DS.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(READ_BOOKING)) {
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getString("username"),
                        rs.getString("resource_name"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime(),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingList;
    }
}
