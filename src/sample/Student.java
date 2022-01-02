package sample;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Student {
    private String login;
    private String password;
    private long time_in_system;
    private long time_control;
    private int mark;
    private int passed;

    public Student(Student st) throws IOException {
        login=st.getLogin();
        password=st.getPassword();
        time_in_system=st.getTime();
        time_control=st.getTimeTest();
        mark=st.getMark();
        passed=st.getPas();
    }
    public Student() throws IOException {
        login="";
        password="";
        time_in_system=0;
        time_control=0;
        mark=0;
        passed=0;
    }
    public Student(String log,String pas,long t,long tt,int m,int p) throws IOException {
        login=log;
        password=pas;
        time_in_system=t;
        time_control=tt;
        mark=m;
        passed=p;
    }
    public boolean load_data(String log,String pass) throws IOException {
        login="";
        password="";
        boolean b=false;
        Files.lines(Paths.get("logins.txt")).forEach(line -> {
            String[] l=line.split("&");
            //System.out.println(l[0]+" "+l[1]+log+pass);
            if(log.equals(l[0]) && pass.equals(l[1])) {
                login=log;
                password=pass;
                time_in_system=Integer.parseInt(l[2]);
                time_control=Integer.parseInt(l[3]);
                mark=Integer.parseInt(l[4]);
                passed=Integer.parseInt(l[5]);
            }
        });
        if(login.equals(""))
            return false;
        else
            return true;
    }
    public void show(TextArea label)
    {
        label.setText("Login:"+login+"\n"+"Password:"+password+"\nTime:"+time_in_system/1000+"s"+
                "\nTime for test:"+ time_control/1000+"s"+"\nMark:"+mark);
        if(passed==0)
            label.appendText("\nHas not passed exam");
        else
            label.appendText("\nHas passed exam");
    }
    public String getLogin()
    {
        return login;
    }
    public String getPassword()
    {
        return password;
    }
    public long getTime()
    {
        return time_in_system;
    }
    public void changeSt_Mat(String log,String pas, Student st)
    {
        ArrayList<String> students=new ArrayList<String>();
        try {
            AtomicInteger ind= new AtomicInteger();
            Files.lines(Paths.get("logins.txt")).forEach(line -> {
                students.add(line);
                if(line.split("&")[0].equals(log)&&line.split("&")[1].equals(pas))
                students.set(ind.intValue(),st.getLogin()+"&"+st.getPassword()+"&"
                        +st.getTime()+"&"//2
                        +line.split("&")[3]+"&"+line.split("&")[4]+"&"+line.split("&")[5]+"&");//3 4 5
                ind.getAndIncrement();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter("logins.txt", false))
        {
            // запись всей строки
            for(int i=0;i<students.size();i++)
            {
                writer.write(students.get(i)+"\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    public void changeSt_Con(String log,String pas, Student st)
    {
        ArrayList<String> students=new ArrayList<String>();
        try {
            AtomicInteger ind= new AtomicInteger();
            Files.lines(Paths.get("logins.txt")).forEach(line -> {
                students.add(line);
                if(line.split("&")[0].equals(log)&&line.split("&")[1].equals(pas))
                    students.set(ind.intValue(),line.split("&")[0]+"&"+line.split("&")[1]+"&"
                            +line.split("&")[2]+"&"//2
                            +st.getTimeTest()+"&"+st.getMark()+"&"+st.getPas()+"&");//3 4 5
                ind.getAndIncrement();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter("logins.txt", false))
        {
            // запись всей строки
            for(int i=0;i<students.size();i++)
            {
                writer.write(students.get(i)+"\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    public long getTimeTest()
    {
        return time_control;
    }
    public int getMark()
    {
        return mark;
    }
    public int getPas()
    {
        return passed;
    }
    public void cpySt(Student st)
    {
        login=st.getLogin();
        password=st.getPassword();
        time_in_system=st.getTime();
        time_control=st.getTimeTest();
        mark=st.getMark();
        passed=st.getPas();
    }
    public void setPassed(int n)
    {
        passed=n;
    }
    public void setMark(int n)
    {
        mark=n;
    }
    public void setTimeTest(int n)
    {
        time_control=n;
    }
}
