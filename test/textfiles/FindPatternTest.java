package textfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

public class FindPatternTest {

	@Test
	public void testBuildPatternTest() {

		final String line = "JXN JLZYNMB QE LQSNQ ZRM VGTWNJ";
		final String pattern = "\\w\\w\\w \\w\\w\\w\\w\\w\\w\\w \\w\\w \\w\\w\\w\\w\\w \\w\\w\\w \\w\\w\\w\\w\\w\\w";
		FindPattern fp = new FindPattern();
		fp.buildPattern(line);
		assertEquals(pattern, fp.getPattern());
	}

	@Test
	public void testBuildPatternTestWithNumbers() {

		final String line = "JXN 1.ZYNMB QE 2.SNQ ZRM VGTWNJ";
		final String pattern = "\\w\\w\\w 1\\.\\w\\w\\w\\w\\w \\w\\w 2\\.\\w\\w\\w \\w\\w\\w \\w\\w\\w\\w\\w\\w";
		FindPattern fp = new FindPattern();
		fp.buildPattern(line);
		assertEquals(pattern, fp.getPattern());
	}
	
	@Test
	public void findMatchingLineTest() {
		final String line = "JXN JLZYNMB QE LQSNQ ZRM VGTWNJ";
		final String fileName = "/Users/richardcox/Documents/cipher-challenge/plain.txt";
		
		try {
			ReadFile rf = new ReadFile(fileName);
			FindPattern fp = new FindPattern();
			fp.buildPattern(line);
			int foundLine = fp.findMatchingLine(0, rf.getLines());
			assertEquals(94457, foundLine+1);
		}
		catch (IOException ioe) {
			fail();
		}
	}

}
