import java.util.ArrayList;

public class MyWord {    //分割出来的单词item类
    public String content;
    public int mlength;
    public ArrayList<Integer> line = new ArrayList<Integer>() ;

    public MyWord(String content) {
        this.content = content;
        mlength = content.length();

    }

    public String lineToString(){
        String temp =line.toString();
        return temp.substring(1,temp.length()-1).replaceAll(" ","");
    }


}
