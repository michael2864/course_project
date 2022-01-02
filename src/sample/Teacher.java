package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Teacher {
    private String login;
    private String password;

    public Teacher ()
    {
        login="";
        password="";
    }
    public Teacher (Teacher teach)
    {
        login=teach.login;
        password=teach.password;
    }
    public boolean load_data(String log,String pas) throws IOException {
        login="";
        password="";
        boolean b=false;
        Files.lines(Paths.get("teachers.txt")).forEach(line -> {
            String[] l=line.split("&");
            //System.out.println(l[0]+" "+l[1]+log+pass);
            if(log.equals(l[0]) && pas.equals(l[1])) {
                login=log;
                password=pas;
            }
        });
        if(login.equals(""))
            return false;
        else
            return true;
    }
    public String getLogin()
    {
        return login;
    }
    public String getPassword()
    {
        return password;
    }
    public Student load_student(int n) throws IOException {
        AtomicInteger ind= new AtomicInteger();
        ArrayList<Student>st=new ArrayList<Student>();
        Files.lines(Paths.get("logins.txt")).forEach(line -> {
            String[] l=line.split("&");
            Student s= null;
            try {
                s = new Student(l[0],l[1],Integer.parseInt(l[2]),Integer.parseInt(l[3]),
                        Integer.parseInt(l[4]),Integer.parseInt(l[5]));
                st.add(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ind.getAndIncrement();
        });
        if(n<0||n>=st.size())
        {
            Student s=new Student("-","-",0,0,0,0);
            return s;
        }
        return st.get(n);
    }
    public void addSt(String log,String pas) throws IOException {
        ArrayList <String> st = new ArrayList<String>();
        Files.lines(Paths.get("logins.txt")).forEach(line -> {
           st.add(line);
        });
        try(FileWriter writer = new FileWriter("logins.txt", false))
        {
            // запись всей строки
            for(int i=0;i<st.size();i++)
            {
                writer.write(st.get(i)+"\n");
            }
            writer.write(log+"&"+pas+"&"+"0"+"&"+"0"+"&"+"0"+"&"+"0"+"&"+"\n");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
