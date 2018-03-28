import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;
import st.SimpleTemplateEngine;

public class Task2_Mutation {
	private EntryMap map;

    private TemplateEngine engine;
    
    private SimpleTemplateEngine simpleEngine;
    
    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
        simpleEngine = new SimpleTemplateEngine();
    }
    
    @Test
    public void Test1() {
    	map.store("n  ame", "A   dam");
		map.store("name", "Adam");
		Integer matchingMode = TemplateEngine.BLUR_SEARCH;
		assertEquals("Hello A   dam", engine.evaluate("Hello ${name}", map, matchingMode));
		assertEquals("Hello Adam", engine.evaluate("Hello ${name}", map, 0));
    }
    
    @Test
    public void Test2() {
    }
    
    @Test
    public void Test3() {
    }
    
    @Test
    public void Test4() {
    	map.store("name", "Adam");
		map.store("competition", "COMPETITION");
		map.store("name} said: ${we should try or best for winning the ${competition", "value1");
		map.store("{name} said: ${we should try or best for winning the ${competition} cup.", "value2");
		map.store("competition} cup.", "value3");
		map.store("we should try or best for winning the ${competition} cup.", "value4");
		assertEquals("I heard that }: Adam said: ${we should try or best for winning the COMPETITION cup.} ",
				engine.evaluate(
						"I heard that }: ${name} said: ${we should try or best for winning the ${competition} cup.} ",
						map, 0));
		map.store("we should try or best for winning the COMPETITION cup.", "nothing");
		assertEquals("I heard that }: Adam said: nothing ", engine.evaluate(
				"I heard that }: ${name} said: ${we should try or best for winning the ${competition} cup.} ", map, 0));
    }
    
    @Test
    public void Test5() {
		map.store("nAme", "aDam");
		map.store("Name", "Adam");
		Integer matchingMode = TemplateEngine.CASE_SENSITIVE;
		assertEquals("Hello Adam", engine.evaluate("Hello ${Name}", map, matchingMode));
		assertEquals("Hello aDam", engine.evaluate("Hello ${name}", map, 0));
	}
    
    @Test
    public void Test6() {
    }
    
    @Test
    public void Test7() {
		map.store("name", "Adam");
		map.delete("NAME");
		assertEquals(map.getEntries().size(), 1);
		map.delete("name");
		assertEquals(map.getEntries().size(), 0);
	}
    
    @Test
    public void Test8() {
		assertEquals("Hi, my name is Peter. david is my forename.", simpleEngine.evaluate(
				"Hi, my name is David. david is my forename.", "David", "Peter", SimpleTemplateEngine.CASE_SENSITIVE));
	}
    
    @Test
    public void Test9() {
    }
    
    @Test
    public void Test10()  {
		assertEquals("defabcabc", simpleEngine.evaluate("defabc", "abc", "abcabc", SimpleTemplateEngine.DEFAULT_MATCH));
	}

}
