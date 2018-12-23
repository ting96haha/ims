

import mmls.MMLSclient;
import java.io.Console;
import java.util.Scanner;

public class ClassTester{
  public static Console testconsole = System.console();
  public static Scanner scan = new Scanner(System.in);

  public static void main(String [] args){
     
	System.out.println("Please enter student ID:");
	String sid = scan.nextLine();
    String pw = new String(testconsole.readPassword("Please enter password for %s: ",sid));
    MMLSclient mmls0 = new MMLSclient(sid,pw);
    //String sid = mmls0.getSid();
    mmls0.testFunc();
	}
};
