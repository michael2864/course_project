package sample;

import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Control {
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    int mark;

    public Control()
    {
        questions=new ArrayList<String>();
        answers=new ArrayList<String>();
        mark=0;
    }
    public void read_q_from_file() throws IOException {
        Files.lines(Paths.get("questions.txt")).forEach(line -> {
            questions.add(line);
        });
    }
    public void read_a_from_file() throws IOException {
        Files.lines(Paths.get("answers.txt")).forEach(line -> {
            answers.add(line);
        });
    }
    public void show_question(TextArea text, int num)
    {
        if(num>questions.size()-1||num<0||questions.size()==0)
            System.err.println("Cannot show part");
        else
        {
            //System.out.println("aaaaaaaaaaaaaaaaaaa");
            text.setText("");
            for(int i=0;i<questions.get(num).split("&")[0].length();i++)
            {
                if(questions.get(num).split("&")[0].charAt(i)=='@')
                    text.appendText("\n");
                else
                    text.appendText(questions.get(num).split("&")[0].charAt(i)+"");
            }
        }
    }
    public int getQ_num()
    {
        return questions.size();
    }
    public int getA_num()
    {
        return answers.size();
    }
    public int getAnswer(int num)
    {
        if(num>answers.size()-1||num<0||answers.size()==0) {
            System.err.println("Cannot receive question");
            return -1;
        }
        return Integer.parseInt(answers.get(num).split("&")[0]);
    }
    public void estimate(int num, RadioButton r1,RadioButton r2,RadioButton r3)
    {
        //System.out.println(getAnswer(num)+"");
        if(r1.isSelected()&&(getAnswer(num)==1))
            mark++;
        else if(r2.isSelected()&&(getAnswer(num)==2))
            mark++;
        else if(r3.isSelected()&&(getAnswer(num)==3))
            mark++;
      //  System.out.println(mark+"");
    }

    public int getMark()
    {
        return mark;
    }
    public int next_part(TextArea text,int num)
    {
        if(num==questions.size()-1||questions.size()==0)
            return num;
        else
        {
            show_question(text,num+1);
            return num+1;
        }
    }
    public int prev_part(TextArea text,int num)
    {
        if(num==questions.size()-1||questions.size()==0)
            return num;
        else
        {
            show_question(text,num+1);
            return num+1;
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
    public void changeQ(int num,String answer,String text ,boolean write)
    {
        questions.set(num-1,text+"&");
        answers.set(num-1,answer+"&");
        if(write)
        {
            try (FileWriter writer = new FileWriter("questions.txt", false)) {
                // запись всей строки
                for(int i=0;i<questions.size();i++)
                    writer.append(questions.get(i)+"\n");
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
            try (FileWriter writer = new FileWriter("answers.txt", false)) {
                // запись всей строки
                for(int i=0;i<answers.size();i++)
                    writer.append(answers.get(i)+"\n");
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }
    }
    public void deleteQ(int num,boolean write)
    {
        questions.remove(num);
        answers.remove(num);
        if(write)
        {
            try (FileWriter writer = new FileWriter("questions.txt", false)) {
                // запись всей строки
                for(int i=0;i<questions.size();i++)
                    writer.append(questions.get(i)+"\n");
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
            try (FileWriter writer = new FileWriter("answers.txt", false)) {
                // запись всей строки
                for(int i=0;i<answers.size();i++)
                    writer.append(answers.get(i)+"\n");
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }
    }
}
