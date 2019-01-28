import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextHandler {    //用来处理字段的工作类
    ArrayList<MyWord> list = new ArrayList<MyWord>();
    String text;  //待处理内容
    int width; //指定宽度


    public TextHandler(String text, int width) {
        this.text = text;
        this.width = width;

    }

    public String handle() {  //集合功能处理
        if (!isInvalidChar(text)) {
            return TextConstant.invalidText;
        } else {
            this.list = splitWord(list, text);
            this.list = countLine(list);
            return outResult() + "\n";
        }
    }


    public ArrayList<MyWord> splitWord(ArrayList<MyWord> list, String text) {   //分割出各个单词单元
        StringTokenizer st = new StringTokenizer(text, " ");
        if (!st.hasMoreTokens()) {   //如果输入字符串均为空格时
            list.add(new MyWord(" "));
        } else {
            while (st.hasMoreTokens()) {
                list.add(new MyWord(st.nextToken()));
                list.add(new MyWord(" "));
            }
            list.remove(list.size() - 1);  //去除最后多余的空格
        }
        return list;

    }

    public ArrayList<MyWord> countLine(ArrayList<MyWord> list) {   //计算各个元素所在行
        int i = 1;
        int count = 0;
        for (int j = 0; j < list.size(); j++) {
            MyWord word = list.get(j);
            if (count + word.mlength < width) {
                count = count + word.mlength;
                word.line.add(i);
            } else if (count + word.mlength == width) {
                word.line.add(i);
                i++;
                count = 0;
            } else {
                int temp = (count + word.mlength) / width;//判断该节占多少行
                count = (count + word.mlength) % width;
                if (count != 0) temp++; //上取整
                while (temp > 0) {
                    word.line.add(i);
                    i++;
                    temp--;
                }
                if (count != 0) i--;
            }

        }
        return list;
    }

    public String outResult() {    //整理输出格式
        StringBuffer sBuffer = new StringBuffer("");
        for (int j = 0; j < list.size(); j++) {
            MyWord word = list.get(j);
            sBuffer.append(word.content).append("(").append(word.lineToString()).append(")").append(";");
        }
        return sBuffer.toString();
    }


    private static boolean isInvalidChar(String text) {  //判断内容格式
        if (text == " " || text == "") return false;
        Pattern pattern = Pattern.compile(TextConstant.regex);
        Matcher m = pattern.matcher(text);
        return m.matches();
    }


}
