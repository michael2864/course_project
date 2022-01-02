package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Add {

    private int themes;
    private ArrayList<String> material;
    private ArrayList<String> headers;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    int mark;

    public Add()
    {
        questions=new ArrayList<String>();
        answers=new ArrayList<String>();
        mark=0;
        material = new ArrayList<String>();
        headers = new ArrayList<String>();
        themes=0;
    }

    public void addPart(String theme,int num,boolean write)
    {
        boolean exists=false;
        int i=0;
        for(;i<material.size();i++)
        {
            if(theme.equals(material.get(i).split("&")[0]))
            {
                exists=true;
                break;
            }
        }
        if(exists)
        {
            ArrayList<String> copy=new ArrayList<String>();
            for(int j=0;j<material.size();j++)
                copy.add(material.get(j));
            material.add("");
            material.set(i+num,theme+"&"+"Empty"+"&");
            i+=num+1;
            for(;i-1<copy.size();i++)
                material.set(i,copy.get(i-1));
        }
        else {
            material.add(theme + "&" + "Empty" + "&");
            headers.add(theme);
            themes++;
        }
        if(write)
            writeFile();
    }
    public void writeFile()
    {
        try(FileWriter writer = new FileWriter("themes.txt", false))
        {
            // запись всей строки
            for(int j=0;j<material.size();j++)
            {
                writer.write(material.get(j)+"\n");
            }
            if(material.size()==0)
                writer.write("");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    public void addQ(String text,String answer,boolean write)
    {
        questions.add(text);
        answers.add(answer);
        if(write) {
            try (FileWriter writer = new FileWriter("questions.txt", true)) {
                // запись всей строки
                writer.write(questions.get(questions.size()-1) + "\n");
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
            try (FileWriter writer = new FileWriter("answers.txt", true)) {
                // запись всей строки
                writer.write(answers.get(answers.size()-1) + "\n");
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }
    }
}
