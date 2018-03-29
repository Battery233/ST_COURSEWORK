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
        
    // return INT, not string
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    
    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }
    
    @Test
    public void Test1() {
    	map.store("year", "abc");
    	assertEquals("Hello abc", engine.evaluate("Hello ${year}", map, TemplateEngine.DEFAULT));
    }
    
    @Test
    public void Test2() {
    	map.store("year", "-1");
    	assertEquals("Hello -1", engine.evaluate("Hello ${year}", map, TemplateEngine.DEFAULT));
    }
    
    @Test
    public void Test3() {
    	map.store("year", "0");
    	assertEquals("Hello " + year, engine.evaluate("Hello ${year}", map, TemplateEngine.DEFAULT));
    }
    
    @Test
    public void Test4() {
    	map.store("base_year", "2000");
    	map.store("year", "0");
    	assertEquals("Hello " + 2000, engine.evaluate("Hello ${year}", map, TemplateEngine.DEFAULT));
    }
    
    @Test
    public void Test5() {
    	map.store("year", "in 5 years");
    	assertEquals("Hello " + (year + 5), engine.evaluate("Hello ${year}", map, TemplateEngine.DEFAULT));
    }
    
    @Test
    public void Test6() {
    	map.store("year", "5 years ago");
    	assertEquals("Hello " + (year - 5), engine.evaluate("Hello ${year}", map, TemplateEngine.DEFAULT));
    }
    
    @Test
    public void Test7() {
    	map.store("year", "in 5 years");
    	map.store("base_year", "2000");
    	assertEquals("Hello " + (2000 + 5), engine.evaluate("Hello ${year}", map, TemplateEngine.DEFAULT));
    }
    
    @Test
    public void Test8() {
    	map.store("year", "5 years ago");
    	map.store("base_year", "2000");
    	assertEquals("Hello " + (2000 - 5), engine.evaluate("Hello ${year}", map, TemplateEngine.DEFAULT));
    }
    
    @Test
    public void Test9() {
    	map.store("year", "5 years ago");
    	map.store("baSe_year", "200");
    	map.store("base_year", "2000");
    	assertEquals("Hello " + (2000 - 5), engine.evaluate("Hello ${year}", map, TemplateEngine.CASE_SENSITIVE));
    }
    
    @Test
    public void Test10() {
    	map.store("year", "5 years ago");
    	map.store("baSe_year", "2000");
    	assertEquals("Hello " + (2000 - 5), engine.evaluate("Hello ${year}", map, TemplateEngine.CASE_INSENSITIVE));
    }
    
    @Test
    public void Test11() {
    	map.store("year", "5 years ago");
    	map.store("base_year", "2000");
    	assertEquals("Hello " + (2000 - 5), engine.evaluate("Hello ${year}", map, TemplateEngine.ACCURATE_SEARCH));
    }
    
    @Test
    public void Test12() {
    	map.store("year", "5 years ago");
    	map.store("base_ year", "2000");
    	assertEquals("Hello " + (2000 - 5), engine.evaluate("Hello ${year}", map, TemplateEngine.BLUR_SEARCH));
    }
    
    @Test
    public void Test13() {
    	map.store("year", "in 5 years");
    	map.store("base_year", "2000");
    	assertEquals("Hello " + (2000 + 5), engine.evaluate("Hello ${year${unmatch}}", map, TemplateEngine.DELETE_UNMATCHED));
    }
    
    @Test
    public void Test14() {
    	map.store("year", "in 5 years");
    	map.store("base_year", "2000");
    	assertEquals("Hello ${year${unmatch}}", engine.evaluate("Hello ${year${unmatch}}", map, TemplateEngine.KEEP_UNMATCHED));
    }
    
    @Test
    public void Test15() {
    	map.store("a", "r");
    	map.store("year", "in 5 years");
    	map.store("base_year", "2000");
    	assertEquals("Hello " + (2000 + 5), engine.evaluate("Hello ${yea${a}}", map, TemplateEngine.DEFAULT));
    }
    
    @Test
    public void Test16() {
    	map.store("year", "in 5 years");
    	map.store("base _year", "2000");
    	map.store("basE_year", "1995");
    	assertEquals("Hello " + (2000 + 5), engine.evaluate("Hello ${year}", map, TemplateEngine.BLUR_SEARCH|TemplateEngine.CASE_SENSITIVE));
    }
}