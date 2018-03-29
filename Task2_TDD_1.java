import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;
import st.SimpleTemplateEngine;
import java.util.Calendar;

public class Task2_TDD_1 {

    private EntryMap map;

    private TemplateEngine engine;
    
    private SimpleTemplateEngine simpleEngine;
    
    // return INT, not string
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    
    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
        simpleEngine = new SimpleTemplateEngine();
    }
    
    @Test
    public void Test1() {
    	
    }
    
    @Test
    public void Test2() {

       
    }
    
    @Test
    public void Test3() {

    }
    
    @Test
    public void Test4() {
       
    }
    
}