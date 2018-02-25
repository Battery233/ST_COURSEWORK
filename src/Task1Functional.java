import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;
import st.SimpleTemplateEngine;

public class Task1Functional {

	private EntryMap map;

	private TemplateEngine engine;

	private SimpleTemplateEngine simpleEngine;

	@Before
	public void setUp() throws Exception {
		map = new EntryMap();
		engine = new TemplateEngine();
		simpleEngine = new SimpleTemplateEngine();
	}

	// Template cannot be NULL or empty
	@Test
	public void testEntryMap01() {
		boolean fail = true;
		try {
			map.store(null, "name");
		} catch (RuntimeException e) {
			fail = false;
		}
		assertEquals(fail, false);
	}

	@Test
	public void testEntryMap02() {
		boolean fail = true;
		try {
			map.store("", "name");
		} catch (RuntimeException e) {
			fail = false;
		}
		assertEquals(fail, false);
	}

	// Replace value string cannot be NULL
	@Test
	public void testEntryMap03() {
		boolean fail = true;
		try {
			map.store("name", null);
		} catch (RuntimeException e) {
			fail = false;
		}
		assertEquals(fail, false);
	}

	// The entries are ordered and follow the order
	@Test
	public void testEntryMap04() {
		map.store("name", "adam");
		map.store("Name", "ADAM");
		assertEquals("Hello adam", engine.evaluate("Hello ${Name}", map, 0));
	}

	// Entries that already exist cannot be stored again. Test the store function
	// executing normally
	@Test
	public void testEntryMap05() {
		map.store("name", "Adam");
		assertEquals(map.getEntries().size(), 1);
		map.store("name", "Adam");
		assertEquals(map.getEntries().size(), 1);
		map.store("Name", "Adam");
		assertEquals(map.getEntries().size(), 2);
	}

	// Template cannot be NULL or empty. A runtime exception is thrown otherwise.

	@Test
	public void testEntryMap06() {
		boolean fail = true;
		try {
			map.delete(null);
		} catch (RuntimeException e) {
			fail = false;
		}
		assertEquals(fail, false);
	}

	@Test
	public void testEntryMap07() {
		boolean fail = true;
		try {
			map.delete("");
		} catch (RuntimeException e) {
			fail = false;
		}
		assertEquals(fail, false);
	}

	// After deleting a value pair, other remaining entries are still ordered.
	@Test
	public void testEntryMap08() {
		map.store("name", "adam");
		map.store("NaMe", "AdAm");
		map.store("NAME", "ADAM");
		map.delete("NaMe");
		assertEquals("Hello adam", engine.evaluate("Hello ${NAME}", map, TemplateEngine.DEFAULT));
	}

	// Only existing value pair can be deleted. Otherwise nothing would happen
	@Test
	public void testEntryMap09() {
		map.store("name", "Adam");
		map.delete("NAME");
		assertEquals(map.getEntries().size(), 1);
		map.delete("name");
		assertEquals(map.getEntries().size(), 0);
	}

	// Template cannot be NULL or empty. A runtime exception is thrown otherwise
	@Test
	public void testEntryMap10() {
		map.store("name", "Adam");
		boolean fail = true;
		try {
			map.update(null, "Adam");
		} catch (RuntimeException e) {
			fail = false;
		}
		assertEquals(fail, false);
	}

	@Test
	public void testEntryMap11() {
		boolean fail = true;
		map.store("name", "Adam");
		try {
			map.update("", "Adam");
		} catch (RuntimeException e) {
			fail = false;
		}
		assertEquals(fail, false);
	}

	// New replace value string parameters cannot be NULL.
	@Test
	public void testEntryMap12() {
		map.store("name", "Adam");
		boolean fail = true;
		try {
			map.update("name", null);
		} catch (RuntimeException e) {
			fail = false;
		}
		assertEquals(fail, false);
	}

	// This operation does not change existing order.
	@Test
	public void testEntryMap13() {
		map.store("name", "adam");
		map.store("Name", "Adam");
		map.update("Name", "Jack");
		assertEquals("Hello adam", engine.evaluate("Hello ${Name}", map, 0));
	}

	// Only existing value pair can be updated.
	@Test
	public void testEntryMap14() {
		map.store("name", "Adam");
		map.update("name", "Jack");
		map.update("height", "180");
		assertEquals("Hello Jack${height}", engine.evaluate("Hello ${Name}${height}", map, 0));
	}

	// *
	// *
	// TemplateEngine Class Specification
	// *
	// *
	// The template string can be NULL or empty. If template string NULL or empty,
	// then the unchanged template string is returned.
	@Test
	public void TestTemplateEngineStringEmpty() {
		map.store("name", "Adam");
		Integer matchingMode = TemplateEngine.DEFAULT;
		String result = engine.evaluate("", map, matchingMode);
		assertEquals("", result);
	}

	@Test
	public void TestTemplateEngineStringNull() {
		map.store("name", "Adam");
		Integer matchingMode = TemplateEngine.DEFAULT;
		String result = engine.evaluate(null, map, matchingMode);
		assertEquals(null, result);
	}

	// The EntryMap object can be NULL. If EntryMap object NULL, then the unchanged
	// template string is returned.
	@Test
	public void TestTemplateEngineMapNull() {
		Integer matchingMode = TemplateEngine.DEFAULT;
		String result = engine.evaluate("String example ${name}", null, matchingMode);
		assertEquals("String example ${name}", result);
	}

	// specification 3
	// *****
	// Three pairs of matching mode can be provided together using the operator |.
	@Test
	public void TestFlagIsZeroNullDefaultUnsupported() {
		map.store("Name", "Adam");
		map.store("n  ame", "adam");
		Integer matchingMode = TemplateEngine.DEFAULT;
		assertEquals("Hello Adam ${SURNAME}", engine.evaluate("Hello ${name} ${SURNAME}", map, matchingMode));
		assertEquals("Hello Adam ${SURNAME}", engine.evaluate("Hello ${name} ${SURNAME}", map, 0));
		assertEquals("Hello Adam ${SURNAME}", engine.evaluate("Hello ${name} ${SURNAME}", map, null));
		assertEquals("Hello Adam ${SURNAME}", engine.evaluate("Hello ${name} ${SURNAME}", map, -54645));
	}

	@Test
	public void TestDeleteUnmatched() {
		map.store("Name", "Adam");
		map.store("n  ame", "adam");
		Integer matchingMode = TemplateEngine.DELETE_UNMATCHED;
		assertEquals("Hello Adam ", engine.evaluate("Hello ${name} ${SURNAME}", map, matchingMode));
	}

	@Test
	public void TestCASE_SENSITIVEandBLUR_SEARCH() {
		map.store("Name", "Adam");
		map.store("n  ame", "adam");
		Integer matchingMode = TemplateEngine.CASE_SENSITIVE | TemplateEngine.BLUR_SEARCH;
		assertEquals("Hello adam ${SURNAME}", engine.evaluate("Hello ${name} ${SURNAME}", map, matchingMode));
	}

	@Test
	public void TestContradictory() {
		map.store("Name", "Adam");
		Integer matchingMode = TemplateEngine.DELETE_UNMATCHED | TemplateEngine.KEEP_UNMATCHED;
		assertEquals("Hello Adam ", engine.evaluate("Hello ${name} ${SURNAME}", map, matchingMode));
	}

	// Templates in a template string occur between "${" and "}". In a template,
	// everything between its boundaries ("${" and "}") is treated as normal text
	// when matched against an entry.
	@Test
	public void Testspec4() {
		map.store("name", "Adam");
		map.store("${name}", "name");
		assertEquals("Hello Adam", engine.evaluate("Hello ${name}", map, 0));
	}

	// When a template is matched against an entry key and BLUR_SEARCH is enabled,
	// any non-visible character does not affect the result.
	@Test
	public void TestspecBlur() {
		map.store("n  ame", "A   dam");
		map.store("name", "Adam");
		Integer matchingMode = TemplateEngine.BLUR_SEARCH;
		assertEquals("Hello A   dam", engine.evaluate("Hello ${name}", map, matchingMode));
		assertEquals("Hello Adam", engine.evaluate("Hello ${name}", map, 0));
	}

	// When CASE_INSENSITIVE is enabled, letter case is not taken in consideration
	// when matching against entries
	@Test
	public void TestspecCase() {
		map.store("nAme", "aDam");
		map.store("Name", "Adam");
		Integer matchingMode = TemplateEngine.CASE_SENSITIVE;
		assertEquals("Hello Adam", engine.evaluate("Hello ${Name}", map, matchingMode));
		assertEquals("Hello aDam", engine.evaluate("Hello ${name}", map, 0));
	}

	// In a template string every "${" and "}" occurrence acts as a boundary of at
	// MOST one template.
	@Test
	public void TestBoundary() {
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

	// In a template string, different templates are ordered according to their
	// length. The shorter templates precede.
	@Test
	public void TestLength() {
		map.store("s", "S");
		map.store("de", "DE");
		map.store("lm", "LM");
		map.store("fgijkLMnopqr", "123");
		assertEquals("abc}DE123Suvw${xyz", engine.evaluate("abc}${de}${fgijk${lm}nopqr}${s}uvw${xyz", map, 0));
	}

	// The engine processes one template at a time and attempts to match it against
	// the keys of the EntryMap entries until there is a match or the entry list is
	// exhausted
	@Test
	public void TestExhaustedResults() {
		map.store("s", "S");
		map.store("lm", "LM");
		map.store("fgijkLMnopqr", "123");
		assertEquals("abc}${de}123Suvw${xyz", engine.evaluate("abc}${de}${fgijk${lm}nopqr}${s}uvw${xyz", map, TemplateEngine.KEEP_UNMATCHED));
	}
	
	@Test
	public void TestExhaustedResults2() {
		map.store("s", "S");
		map.store("lm", "LM");
		map.store("fgijkLMnopqr", "123");
		assertEquals("abc}123Suvw${xyz", engine.evaluate("abc}${de}${fgijk${lm}nopqr}${s}uvw${xyz", map, TemplateEngine.DELETE_UNMATCHED));
	}

}
