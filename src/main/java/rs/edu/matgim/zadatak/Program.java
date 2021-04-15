package rs.edu.matgim.zadatak;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);
        DB _db = new DB();
        _db.printNajzastupljenijaRelacija();
        
        String ime=sc.next();
        String prezime=sc.next();
        String kategorija=sc.next();
        _db.zadatak(ime+prezime, kategorija);
    }
}
