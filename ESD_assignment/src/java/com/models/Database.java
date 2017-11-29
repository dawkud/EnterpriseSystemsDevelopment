package com.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.chart.PieChart;

public class Database {

    // variable to access the connection of database
    private static String url = "jdbc:derby://localhost:1527/ESDDB";
    private static String user = "root";
    private static String pass = "password";

    // constant to reduce duplication and spelling/coding errors
    public static final String CLAIMS_TABLE = "claims";
    public static final String CLAIMS_TABLE_ID = "id";
    public static final String CLAIMS_TABLE_MEMBER_ID = "member_id";
    public static final String CLAIMS_TABLE_DATE = "date";
    public static final String CLAIMS_TABLE_DESCRIPTION = "discription";
    public static final String CLAIMS_TABLE_STATUS = "status";
    public static final String CLAIMS_TABLE_AMOUNT = "amount";

    public static final String MEMBERS_TABLE = "members";
    public static final String MEMBERS_TABLE_ID = "id";
    public static final String MEMBERS_TABLE_NAME = "name";
    public static final String MEMBERS_TABLE_ADDRESS = "address";
    public static final String MEMBERS_TABLE_DOB = "dob";
    public static final String MEMBERS_TABLE_DOR = "dor";
    public static final String MEMBERS_TABLE_STATUS = "status";
    public static final String MEMBERS_TABLE_BALANCE = "balance";

    public static final String PAYMENTS_TABLE = "payments";
    public static final String PAYMENTS_TABLE_ID = "id";
    public static final String PAYMENTS_TABLE_MEMBER_ID = "member_id";
    public static final String PAYMENTS_TABLE_TYPE_OF_PAYMENT = "type_of_payment";
    public static final String PAYMENTS_TABLE_AMOUNT = "amount";
    public static final String PAYMENTS_TABLE_DATE = "date";
    public static final String PAYMENTS_TABLE_TIME = "time";

    public static final String USERS_TABLE = "users";
    public static final String USERS_TABLE_ID = "id";
    public static final String USERS_TABLE_PASSWORD = "password";
    public static final String USERS_TABLE_STATUS = "status";

    // create a new member in the database
    protected boolean insertDataIntoMembers(String username, String firstName, String lastName, String address, Date dob, Date dor, String status, double balance) {

        // insertion query
        String query = "insert into members values(?,?,?,?,?,?,?)";

        //  connect to the database. Connection and PreparedStatement will auto close after use
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement pstmt = con.prepareStatement(query);) {
            String name = firstName + " " + lastName;
            // set the ? in the query
            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setString(3, address);
            pstmt.setDate(4, dob);
            pstmt.setDate(5, dor);
            pstmt.setString(6, status);
            pstmt.setDouble(7, balance);
            // if statement execute and update return true else return false
            if (pstmt.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // create a new user in the database
    protected boolean insertDataIntoUsers(String username, String password, String status) {

        // insertion query
        String query = "insert into users values(?,?,?)";

        //  connect to the database. Connection and PreparedStatement will auto close after use
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement pstmt = con.prepareStatement(query);) {
            // set the ? in the query
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, status);
            // if statement execute and update return true else return false
            if (pstmt.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
        Get all the datas from the table and return as arraylist
        To use this we need the table name to get the data from the database
        e.g. db.getAllData(USERS_TABLE);
        select the data from user table
    */
    protected ArrayList<Object> getAllData(String tableName) {

        // selection query
        String query = "select * from " + tableName;
        // object arraylist to store data
        ArrayList<Object> data = new ArrayList<>();

        // connect to the database. connection, resultSet and preparedStatement will auto close after use
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();) {

            // switch from four tables
            switch (tableName) {
                case CLAIMS_TABLE:
                    try {
                        // while rs has value, populate claim
                        while (rs.next()) {
                            // create a claim to return
                            ClaimsBean claim = new ClaimsBean();
                            claim.setID(rs.getInt(1));
                            claim.setMemberID(rs.getString(2));
                            claim.setDate(rs.getDate(3));
                            claim.setDescription(rs.getString(4));
                            claim.setState(rs.getString(5));

                            // format amount to get the amount like 10.00
                            DecimalFormat df = new DecimalFormat("#.00");
                            double amount = Double.parseDouble(df.format(rs.getDouble(6)));
                            claim.setAmount(amount);

                            // add claim into arraylist
                            data.add(claim);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // if something went wrong -> wrong column index
                        return null;
                    }
                case PAYMENTS_TABLE:
                    try {
                        // while rs has value, populate payment
                        while (rs.next()) {
                            // create a payment to return
                            PaymentBean payment = new PaymentBean();
                            payment.setID(rs.getInt(1));
                            payment.setMemberID(rs.getString(2));
                            payment.setType(rs.getString(3));

                            // format amount to get the amount like 10.00
                            DecimalFormat df = new DecimalFormat("#.00");
                            double amount = Double.parseDouble(df.format(rs.getDouble(4)));
                            payment.setAmount(amount);
                            payment.setDate(rs.getDate(5));
                            payment.setTimestamp(rs.getTime(6));
                           
                            // add payment into arraylist
                            data.add(payment);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // if something went wrong -> wrong column index
                        return null;
                    }
                case MEMBERS_TABLE:
                    try {
                        // while rs has value, populate member
                        while (rs.next()) {
                            // create a user to return
                            User member = new User();
                            member.setUsername(rs.getString(1));
                            String names[] = rs.getString(2).split(" ");
                            member.setFirstName(names[0]);
                            
                            // check if there were spaces for the last name
                            String lastName = "";
                            if (names.length > 2) {
                                for (int i = 1; i < names.length; i++) {
                                    lastName += names[i];
                                    if (i < names.length - 1) {
                                        lastName += " ";
                                    }
                                }
                            } else {
                                lastName = names[1];
                            }
                            member.setLastName(lastName);
                            member.setAddress(rs.getString(3));
                            member.setDob(rs.getDate(4));
                            member.setDor(rs.getDate(5));
                            member.setStatus(rs.getString(6));
                            member.setBalance(rs.getDouble(7));
                            
                            // add member into arraylist
                            data.add(member);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // if something went wrong -> wrong column index
                        return null;
                    }
                case USERS_TABLE:
                    try {
                        // while rs has value, populate user
                        while (rs.next()) {
                            // create a user to return
                            User user = new User();
                            user.setUsername(rs.getString(1));
                            user.setPassword(rs.getString(2));
                            user.setStatus(rs.getString(3));
                            
                            // add user into arraylist
                            data.add(user);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // if something went wrong -> wrong column index
                        return null;
                    }
                default:
                    // if no table found
                    return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //worst case -> if printstatement.executeQuery failed.
        return null;
    }

    /*
        Get all the datas from table by filter and return as arraylist
        To use this we need the table name and the object we want to search 
        e.g. db.getAllData(USERS_TABLE, USERS_TABLE_ID, 1);
        select the data from user table, column name id where id = 1
    */
    protected ArrayList<Object> getAllData(String tableName, String columnName, Object filter) {
        
        // selection query, with filter
        String query = "select * from " + tableName + " where " + columnName + "=?";
        
        // object arraylist to store data
        ArrayList<Object> data = new ArrayList<>();
        ResultSet rs = null;

        // connect to the database. connection and preparedStatement will auto close after use
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement pstmt = con.prepareStatement(query);) {

            // setobject will assing the correct set method to what object it has been given
            // look at api: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
            // set the ? in the query
            pstmt.setObject(1, filter);
            // statement execute
            rs = pstmt.executeQuery();
            
            // switch from four tables
            switch (tableName) {
                case CLAIMS_TABLE:
                    try {
                        // while rs has value, populate claim
                        while (rs.next()) {
                            // create a claim to return
                            ClaimsBean claim = new ClaimsBean();
                            claim.setID(rs.getInt(1));
                            claim.setMemberID(rs.getString(2));
                            claim.setDate(rs.getDate(3));
                            claim.setDescription(rs.getString(4));
                            claim.setState(rs.getString(5));
                            
                            // format amount to get the amount like 10.00
                            DecimalFormat df = new DecimalFormat("#.00");
                            double amount = Double.parseDouble(df.format(rs.getDouble(6)));
                            claim.setAmount(amount);
                            
                            // add claim into arraylist
                            data.add(claim);
                        }
//                        System.out.println(data);
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // if something went wrong -> wrong column index
                        return null;
                    }
                case PAYMENTS_TABLE:
                    try {
                        // while rs has value, populate payment
                        while (rs.next()) {
                            // create a payment to return
                            PaymentBean payment = new PaymentBean();
                            payment.setID(rs.getInt(1));
                            payment.setMemberID(rs.getString(2));
                            payment.setType(rs.getString(3));
                            
                            // format amount to get the amount like 10.00
                            DecimalFormat df = new DecimalFormat("#.00");
                            double amount = Double.parseDouble(df.format(rs.getDouble(4)));
                            payment.setAmount(amount);
                            payment.setDate(rs.getDate(5));
                            payment.setTimestamp(rs.getTime(6));
                            
                            // add payment into arraylist
                            data.add(payment);
                        }
//                        System.out.println(data);
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // if something went wrong -> wrong column index
                        return null;
                    }
                case MEMBERS_TABLE:
                    try {
                        // while rs has value, populate member
                        while (rs.next()) {
                            // create a user to return
                            User member = new User();
                            member.setUsername(rs.getString(1));
                            String names[] = rs.getString(2).split(" ");
                            member.setFirstName(names[0]);
                            //check if there were spaces for the last name
                            String lastName = "";
                            if (names.length > 2) {
                                for (int i = 1; i < names.length; i++) {
                                    lastName += names[i];
                                    if (i < names.length - 1) {
                                        lastName += " ";
                                    }
                                }
                            } else {
                                lastName = names[1];
                            }
                            member.setLastName(lastName);
                            member.setAddress(rs.getString(3));
                            member.setDob(rs.getDate(4));
                            member.setDor(rs.getDate(5));
                            member.setStatus(rs.getString(6));
                            member.setBalance(rs.getDouble(7));
                            // add member to arraylist
                            data.add(member);
                        }
//                        System.out.println(data);
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // if something went wrong -> wrong column index
                        return null;
                    }
                case USERS_TABLE:
                    try {
                        //while rs has value, populate user
                        while (rs.next()) {
                            //create a user to return
                            User user = new User();
                            user.setUsername(rs.getString(1));
                            user.setPassword(rs.getString(2));
                            user.setStatus(rs.getString(3));
                                                        
                            // add member to arraylist
                            data.add(user);
                        }
//                        System.out.println(data);
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // if something went wrong -> wrong column index
                        break;
                    }
                default:
                    return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //worst case
        return null;
    }

    /*
        Get data from table by filter and return as object
        To use this we need the table name and the object we want to search 
        e.g. db.searchData(USERS_TABLE, USERS_TABLE_ID, 1);
        select the data from user table, column name id where id = 1
        it is similar to getAllData but this one will only return one object
    */
    protected Object searchData(String tableName, String columnName, Object dataSearch) {
        
        // selection query, with filter
        String query = "select * from " + tableName + " where " + columnName + "=?";
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        //connect to the database, connection will auto close after use
        try (Connection con = DriverManager.getConnection(url, user, pass);) {

            if (columnName == CLAIMS_TABLE_DESCRIPTION || columnName == MEMBERS_TABLE_ADDRESS) {
                // query for Long Varchar
                query = "select * from " + tableName + " where " + columnName + " like ? escape '!'";
                String search = (String) dataSearch;
                pstmt = con.prepareStatement(query);
                // no matter what is before or after the data you want to search
                // if there contain the word, return that row of data
                pstmt.setObject(1, "%" + dataSearch + "%");
            } else {
                // if data type is not long varchar, use normal selection query
                pstmt = con.prepareStatement(query);
                pstmt.setObject(1, dataSearch);
            }
            // statement execute
            rs = pstmt.executeQuery();

            switch (tableName) {
                case CLAIMS_TABLE:
                    // create a claim to return
                    ClaimsBean claim = new ClaimsBean();
                    
                    // populate the claim
                    if (rs.next()) {
                        claim.setID(rs.getInt(1));
                        claim.setMemberID(rs.getString(2));
                        claim.setDate(rs.getDate(3));
                        claim.setDescription(rs.getString(4));
                        claim.setState(rs.getString(5));
                        // format amount to get the amount like 10.00
                        DecimalFormat df = new DecimalFormat("#.00");
                        double amount = Double.parseDouble(df.format(rs.getDouble(6)));
                        claim.setAmount(amount);
                    } else {
                        claim = null;
                    }
                    return claim;
                case PAYMENTS_TABLE:
                    // create a payment to return
                    PaymentBean payment = new PaymentBean();
                    
                    // populate the payment
                    if (rs.next()) {
                        payment.setID(rs.getInt(1));
                        payment.setMemberID(rs.getString(2));
                        payment.setType(rs.getString(3));
                        // format amount to get the amount like 10.00
                        DecimalFormat df = new DecimalFormat("#.00");
                        double amount = Double.parseDouble(df.format(rs.getDouble(4)));
                        payment.setAmount(amount);
                        payment.setDate(rs.getDate(5));
                        payment.setTimestamp(rs.getTime(6));
                    } else {
                        payment = null;
                    }
                    return payment;
                case MEMBERS_TABLE:
                    //create user to return
                    User member = new User();
                    // populate the member
                    if (rs.next()) {
                        member.setUsername(rs.getString(1));
                        String names[] = rs.getString(2).split(" ");
                        member.setFirstName(names[0]);
                        //check if there were spaces for the last name
                        String lastName = "";
                        if (names.length > 2) {
                            for (int i = 1; i < names.length; i++) {
                                lastName += names[i];
                                if (i < names.length - 1) {
                                    lastName += " ";
                                }
                            }
                        } else {
                            lastName = names[1];
                        }
                        member.setLastName(lastName);
                        member.setAddress(rs.getString(3));
                        member.setDob(rs.getDate(4));
                        member.setDor(rs.getDate(5));
                        member.setStatus(rs.getString(6));
                        member.setBalance(rs.getDouble(7));
                    } else {
                        member = null;
                    }
                    return member;
                case USERS_TABLE:
                    //create a user to return
                    User user = new User();
                    //populate the user
                    if (rs.next()) {
                        user.setUsername(rs.getString(1));
                        user.setPassword(rs.getString(2));
                        user.setStatus(rs.getString(3));
                    } else {
                        user = null;
                    }
                    return user;
                default:
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally { // close resultset and preparedstatment
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //worst case, return null
        return null;
    }

    /*
        Update data from table and return boolean to check the update status
        To use this we need the table name, column name, new value of data, id
        e.g. db.updateData(USERS_TABLE, "password", USERS_TABLE_PASSWORD, 1);
        update the data from user table to "password" where id = 1
    */
    protected boolean updateData(String tableName, Object id, String columnName, Object newValue) {

        // update query
        String query = "update " + tableName + " set " + columnName + " =? where id =?";

        //connect to the database, connection and preparedstatement will auto close after use
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement pstmt = con.prepareStatement(query);) {

            // set data to update
            pstmt.setObject(1, newValue);
            pstmt.setObject(2, id);

            // if return 1 do the executeUpdate() and return true, else return false
            if (pstmt.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
        insert data into table and return boolean to check the update status
        To use this we need the table name and object details
        e.g. db.insertData(USERS_TABLE, new User("username", "password", "Applied"));
        insert new user with username "username", password "password" and status "applied"
    */
    protected boolean insertData(String tableName, Object type) {

        // insertion query
        String query = "insert into " + tableName + " ";
        PreparedStatement pstmt = null;

        //connect to the database, connection will auto close after use
        try (Connection con = DriverManager.getConnection(url, user, pass);) {
            //switch from four tables, different table have different columns
            switch (tableName) {
                case CLAIMS_TABLE:
                    // set query and insert data
                    query += "(" + CLAIMS_TABLE_MEMBER_ID + ","
                            + CLAIMS_TABLE_DATE + ","
                            + CLAIMS_TABLE_DESCRIPTION + ","
                            + CLAIMS_TABLE_STATUS + ","
                            + CLAIMS_TABLE_AMOUNT + ") ";
                    query += "values(?,?,?,?,?)";
                    pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                    ClaimsBean claim = (ClaimsBean) type;
                    pstmt.setString(1, claim.getMemberID());
                    pstmt.setDate(2, claim.getDate());
                    pstmt.setString(3, claim.getDescription());
                    pstmt.setString(4, claim.getState());
                    pstmt.setDouble(5, claim.getAmount());

                    //if statement execute and update return true else return false
                    if (pstmt.executeUpdate() == 1) {
                        return true;
                    } else {
                        return false;
                    }
                case MEMBERS_TABLE:
                    // set query and insert data
                    query += "values(?,?,?,?,?,?,?)";
                    pstmt = con.prepareStatement(query);
                    User member = (User) type;
                    String name = member.getFirstName() + " " + member.getLastName();
                    pstmt.setString(1, member.getUsername());
                    pstmt.setString(2, name);
                    pstmt.setString(3, member.getAddress());
                    pstmt.setDate(4, member.getDob());
                    pstmt.setDate(5, member.getDor());
                    pstmt.setString(6, member.getStatus());
                    pstmt.setDouble(7, member.getBalance());

                    //if statement execute and update return true else return false
                    if (pstmt.executeUpdate() == 1) {
                        return true;
                    } else {
                        return false;
                    }
                case PAYMENTS_TABLE:
                    // set query and insert data
                    query += "(" + PAYMENTS_TABLE_MEMBER_ID + ","
                            + PAYMENTS_TABLE_TYPE_OF_PAYMENT + ","
                            + PAYMENTS_TABLE_AMOUNT + ","
                            + PAYMENTS_TABLE_DATE + ","
                            + PAYMENTS_TABLE_TIME + ") ";
                    query += " values(?,?,?,?,?)";
//                    pstmt = con.prepareStatement(query, new String[]{"ID"});
                    pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                    PaymentBean payments = (PaymentBean) type;
                    pstmt.setString(1, payments.getMemberID());
                    pstmt.setString(2, payments.getType());
                    pstmt.setDouble(3, payments.getAmount());
                    pstmt.setDate(4, payments.getDate());
                    pstmt.setTime(5, payments.getTimestamp());
                   
                    //if statement execute and update return true else return false
                    if (pstmt.executeUpdate() == 1) {
                        return true;
                    } else {
                        return false;
                    }
                case USERS_TABLE:
                    // set query and insert data
                    query += "values(?,?,?)";
                    pstmt = con.prepareStatement(query);
                    User users = (User) type;
                    pstmt.setString(1, users.getUsername());
                    pstmt.setString(2, users.getPassword());
                    pstmt.setString(3, users.getStatus());

                    //if statement execute and update return true else return false
                    if (pstmt.executeUpdate() == 1) {
                        return true;
                    } else {
                        return false;
                    }
                default:
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
          
        } finally { // close preparedstatment
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // worst case, item not added... return false
        return false;
    }

    // use to test
    public static void main(String[] args) {
//        Database db = new Database();
//        System.out.println(db.getAllData(USERS_TABLE).toString());
//        System.out.println(db.searchData(CLAIMS_TABLE, CLAIMS_TABLE_ID, 1));
//        db.insertData(USERS_TABLE, new User("tom","750407","APPROVED"));
//        System.out.println(db.getAllData(USERS_TABLE).toString());

        /* Testing */
        Database db = new Database();

        //insert user
        User user = new User();
        user.setFirstName("Prithpal");
        user.setLastName("Sooriya");
        user.setUsername("p-sooriya");
        user.setPassword("1234");
        user.setDob(Date.valueOf("1997-05-04"));
        user.setDor(new Date(System.currentTimeMillis()));
        user.setAddress("158 Speedwell Road, BS57SS");
        user.setBalance(0);
        user.setStatus(User.APPLIED);
        db.insertData(Database.USERS_TABLE, user);

        //insert member
        db.insertData(MEMBERS_TABLE, user);

        //insert payment
        PaymentBean p1 = new PaymentBean();
        p1.setAmount(10.1);
        p1.setDate(new Date(System.currentTimeMillis()));
        p1.setMemberID(user.getUsername());
        p1.setType("FEE");
        p1.setTimestamp(new Time(System.currentTimeMillis()));
        db.insertData(Database.PAYMENTS_TABLE, p1);

        //insert payment
        ClaimsBean claim = new ClaimsBean();
        claim.setAmount(10);
        claim.setDate(new Date(System.currentTimeMillis()));
        claim.setDescription("blah");
        claim.setMemberID(user.getUsername());
        claim.setState(ClaimsBean.APPROVED);
        db.insertData(Database.CLAIMS_TABLE, claim);

        //retrieve data
        ArrayList<User> users = new ArrayList<>();
        for (Object object : db.getAllData(Database.USERS_TABLE)) {
            users.add((User) object);
        }
        System.out.println(users.toString());

        System.out.println("==============");

        ArrayList<User> members = new ArrayList<>();
        members.add((User) db.searchData(Database.MEMBERS_TABLE, Database.MEMBERS_TABLE_ID, user.getUsername()));
        System.out.println(members.toString());

        System.out.println("==============");

        ArrayList<PaymentBean> payments = new ArrayList<>();
        for (Object object : db.getAllData(Database.PAYMENTS_TABLE, Database.PAYMENTS_TABLE_MEMBER_ID, p1.getMemberID())) {
            payments.add((PaymentBean) object);
        }
        System.out.println(payments.toString());

        System.out.println("==============");

        ArrayList<ClaimsBean> claims = new ArrayList<>();
        for (Object object : db.getAllData(Database.CLAIMS_TABLE)) {
            claims.add((ClaimsBean) object);
        }
        System.out.println(claims.toString());

        System.out.println("==============");

        //update data
        db.updateData(Database.USERS_TABLE, user.getUsername(), Database.USERS_TABLE_STATUS, User.ADMIN);
        users.clear();
        for (Object object : db.getAllData(Database.USERS_TABLE)) {
            users.add((User) object);
        }
        System.out.println(users.toString());

        System.out.println("==============");

        db.updateData(Database.MEMBERS_TABLE, user.getUsername(), Database.MEMBERS_TABLE_STATUS, User.ADMIN);
        members.clear();
        members.add((User) db.searchData(Database.MEMBERS_TABLE, Database.MEMBERS_TABLE_ID, user.getUsername()));
        System.out.println(members.toString());

        System.out.println("==============");
    }

}
