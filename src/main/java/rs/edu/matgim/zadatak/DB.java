package rs.edu.matgim.zadatak;

import java.sql.*;

public class DB {

    String connectionString = "jdbc:sqlite:src\\main\\java\\KompanijaZaPrevoz.db";

    public void printNajzastupljenijaRelacija() {
        try (Connection conn = DriverManager.getConnection(connectionString); Statement s = conn.createStatement()) {

            ResultSet rs = s.executeQuery("SELECT DISTINCT P.MestoOd+'r'+P.MestoDo AS putanja FROM Putovanje P");
            int max=0;
            String OdK="",DoK="";
            while (rs.next()) {
                String putanja=rs.getString("putanja");
                String[] mesta= putanja.split("r");
                String upit="SELECT COUNT(*) AS broj FROM Putovanje P WHERE P.MestoOd="+mesta[0]+" AND P.MestoDo="+mesta[1];
                 ResultSet rs1=s.executeQuery(upit);
                int tr=rs1.getInt("broj");
                if(tr>max) {max=tr;OdK=mesta[0];DoK=mesta[1];}
            }
            System.out.println(OdK+" "+DoK);
        } catch (SQLException ex) {
            System.out.println("Greska prilikom povezivanja na bazu");
            System.out.println(ex);
        }
    }
    public int zadatak(String imeIPrezime,String Kategorija){
        int o=1;
        String upit="INSERT INTO Vozac(ImePrezime,Kategorija) VALUES (?,?)";
        String upit1="SELECT IDzap from Vozac";
        String upit2="UPDATE Putovanje P SET IDZap=? WHERE Status='N' AND P.IDZap=NULL";
        String upit3="SELECT IdPut FROM Putovanje";
        String upit4="UPDATE Putovanje P SET IDZap=? WHERE Status='N' AND IdPut=?";
        try (Connection conn = DriverManager.getConnection(connectionString); PreparedStatement ps = conn.prepareStatement(upit);Statement st=conn.createStatement()) {
                  conn.setAutoCommit(false);
                   ps.setString(1, imeIPrezime);
                   ps.setString(2, Kategorija);
                   ps.execute();
                   
                   conn.prepareStatement(upit1);
                   ps.execute();
                   ResultSet rs=ps.getResultSet();
                   int id=0;
                   while(rs.next()){
                       id++;
                   }
                   conn.prepareStatement(upit2);
                   ps.setInt(1, id);
                   ps.execute();
                   if(ps.getResultSet()==null){
                       st.executeQuery(upit3);
                       int min=10000;
                       while(rs.next()){
                           int p=rs.getInt("IdPut");
                           if (p<min) min=p;
                       }
                       conn.prepareStatement(upit4);
                       ps.setInt(1, min);
                       ps.execute();
                       if(ps.getResultSet()==null){
                           System.out.println("Nije ga potrebno angazovati za sada.");
                           o=-1;
                       }
                   }
                   conn.commit();
                   conn.setAutoCommit(true);
                   return o;
        } catch (SQLException ex) {
            System.out.println("Greska prilikom povezivanja na bazu");
            System.out.println(ex);
            return -1;
        }
    }
    

}
