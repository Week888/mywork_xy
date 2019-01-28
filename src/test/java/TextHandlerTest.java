import org.junit.Test;

import static org.junit.Assert.*;


public class TextHandlerTest {

    TextHandler handler ;


    @Test
    public void testHandle(){   //测试分解字符串正确性
        String text = "The main theme of education in engineering school is learning to teach yourself";
        int width = 30;
        String result  = "The(1); (1);main(1); (1);theme(1); (1);of(1); (1);education(1); (1);in(1); (2);engineering(2); (2);school(2); (2);is(2); (2);learning(2,3); (3);to(3); (3);teach(3); (3);yourself(3);";
        handler= new TextHandler(text,width);
        assertEquals(result,handler.handle());
    }



    @Test
    public void testInput(){   //测试字符串里面含有非法字符情况
        String text = "The main z5661";
        int width = 30;
        String tips  = "error text format";
        handler= new TextHandler(text,width);
        assertEquals(tips,handler.handle());
    }

}