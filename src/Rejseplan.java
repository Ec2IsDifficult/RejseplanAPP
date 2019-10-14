import java.sql.*;
import java.util.Scanner;
public class Rejseplan {


    public Connection connect (String url) throws SQLException {

        return DriverManager.getConnection(url);

    }

    public void PresentInformation(ResultSet res)
        throws SQLException{
        if(res == null)
            System.out.print("No records for you");
        while (res!= null & res.next()) {
            String departuretime = res.getString("Departuretime");
            System.out.println("Du skal tage fra København "+ departuretime);
            //String station = res.getString("StationName");
            //System.out.print(station);
        }
    }

    public PreparedStatement selectAvailableStations(Connection conn)
        throws SQLException {
            String query = "select StationName from Stations";
            PreparedStatement selectStationName = null;
            selectStationName = conn.prepareStatement(query);
            return selectStationName;

        }

    /*public PreparedStatement selectAvailableDepartures(String StationName, Connection conn)
        throws SQLException {
        String query = "select Departuretime from Departures where StationName = '" + StationName + "'";
        PreparedStatement departures =null;
        departures = conn.prepareStatement(query);
        return departures;
    }*/

    public PreparedStatement selectAvailableDepartures(String StartStationName, Connection conn)
        throws SQLException {
            String query = "select Departuretime from Departures where StationName = '" + StartStationName + "'";
            PreparedStatement departureFromStart = null;
            departureFromStart = conn.prepareStatement(query);
            return departureFromStart;
    }

    //public PreparedStatement figureOutRoute()


    public static void main(String[] args) {

        Rejseplan RP = new Rejseplan();
        Connection conn = null;
        try{
            float endTime;
            String url = "jdbc:sqlite:C:\\Users\\rasse\\Desktop\\5 Semester\\sqlite/rejseplanenCasperRasmus.db";
            conn =  RP.connect(url);
            System.out.println("Type your the name of the station you are departing from");
            Scanner scanner = new Scanner(System.in);
            String StationName = scanner.nextLine();
            System.out.println("Type the name of you end destination");
            String DestinationStation = scanner.nextLine();
            System.out.println("Type the time you wish to arrive at");
            endTime = scanner.nextFloat();


            //PreparedStatement retrieveStations = RP.selectAvailableStations(conn);
            //retrieveStations.setString(1, anyKey);
            PreparedStatement retrieveDepartures = RP.selectAvailableDepartures(StationName, conn);
            ResultSet departures = retrieveDepartures.executeQuery();
            //ResultSet stations = retrieveStations.executeQuery();
            //RP.PresentInformation(stations);
            RP.PresentInformation(departures);
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
