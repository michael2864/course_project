package sample;

import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Material {
    private int themes;
    private ArrayList<String> material;
    private ArrayList<String> headers;

    Material() throws IOException {material = new ArrayList<String>();
        headers = new ArrayList<String>();
    themes=0;}
    public void copy_theme(Material mat, String head)
    {
        boolean b=false;
        if(mat.getLinesNum()==0)
        {
            ;
        }
        else {
            for (int i = 0; i < mat.getLinesNum(); i++) {
                if (head.equals(mat.getLine(i).split("&")[0])) {
                    b = true;
                    material.add(mat.getLine(i));
                }
            }
            if (b) {
                themes = 1;
                headers.add(head);
            } else {
                themes = 0;
            }
        }
    }

    public void read_from_file() throws IOException {
        headers = new ArrayList<String>();
        themes = 0;
        Files.lines(Paths.get("themes.txt")).forEach(line -> {
            material.add(line);
        });
        String prev;
        if(material.isEmpty())
        {
            themes=0;
            return;
        }
        else
        {
            themes=1;
            prev=material.get(0).split("&")[0];
            headers.add(prev);
            for(int i=0;i<material.size();i++)
            {
                if(!prev.equals(material.get(i).split("&")[0]))
                {
                    prev=material.get(i).split("&")[0];
                    headers.add(prev);
                    themes++;
                }
            }
        }
    }
    public String getLine(int num)
    {
        return material.get(num);
    }
    public int getLinesNum()
    {
        return material.size();
    }
    public int getThemes()
    {return themes;}
    public String getHeader(int n)
    {
        return headers.get(n);
    }
    public void show_part(TextArea text,int num)
    {
        if(num>material.size()-1||num<0)
            System.err.println("Cannot show part");
        else
        {
            text.setText("");
            for(int i=0;i<material.get(num).split("&")[1].length();i++)
            {
                if(material.get(num).split("&")[1].charAt(i)=='@')
                    text.appendText("\n");
                else
                    text.appendText(material.get(num).split("&")[1].charAt(i)+"");
            }
        }
            //text.setText(material.get(num).split("&")[1]);
    }
    public void change_mat(String theme,int num,String text,boolean write)
    {
        for(int i=0;i<material.size();i++)
        {
            if(material.get(i).split("&")[0].equals(theme))
            {
                material.set(i+num,theme+"&"+text+"&");
                if(write)
                    writeFile();
                break;
            }
        }
    }
    public void deletePart(int num,boolean write)
    {
        material.remove(num);
        if(material.isEmpty())
        {
            themes=0;
            headers.clear();
            return;
        }
        else
        {
            headers.clear();
            String prev;
            themes=1;
            prev=material.get(0).split("&")[0];
            headers.add(prev);
            for(int i=0;i<material.size();i++)
            {
                if(!prev.equals(material.get(i).split("&")[0]))
                {
                    prev=material.get(i).split("&")[0];
                    headers.add(prev);
                    themes++;
                }
            }
        }
        if(write)
        {
            writeFile();
        }
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
    public int next_part(TextArea text,int num)
    {
        if(num==material.size()-1||material.size()==0)
            return num;
        else
        {
            show_part(text,num+1);
            return num+1;
        }
    }
    public int prev_part(TextArea text,int num)
    {
        if(num==0||material.size()==0)
            return num;
        else
        {
            show_part(text,num-1);
            return num-1;
        }
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
    public void deleteTheme(String theme,boolean write)
    {
        for(int i=0;i<material.size();i++)
        {
            //System.out.println(material.size()+"");
            if(material.get(i).split("&")[0].equals(theme))
            {
                //System.out.println(material.size()+"");
                    while (material.get(i).split("&")[0].equals(theme)) {
                        material.remove(i);
                        if (material.isEmpty())
                            break;
                    }
            }
        }
        for(int i=0;i<headers.size();i++)
        {
            if(headers.get(i).equals(theme))
                headers.remove(i);
        }
        themes--;
        if(write)
             writeFile();
    }

}
