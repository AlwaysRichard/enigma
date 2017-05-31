package textfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

public class ReadFileTest {

	@Test
	public void testPath() {
		final String fileName = "/Users/richardcox/Documents/cipher-challenge/plain_test.txt";
		final String firstLine = "The Project Gutenberg EBook of The Complete Works of William Shakespeare, by";
		
		try {
			ReadFile rf = new ReadFile(fileName);
			
			// Ensure path and fileName are correct
			assertEquals(fileName, rf.getName());
			
			// Read file, first 35 lines with the <<THIS ELECTRONIC VERSION OF ...>> block
			// added. Test first line.
			final List<String> text = rf.getLines();
			assertEquals(firstLine, text.get(0).trim());
			
			// Should be 27 lines after removeing the copyright coment
			// <<THIS ELECTRONIC VERSION OF ...>>
			assertEquals(27, text.size());
		}
		catch (IOException ioe) {
			fail();
		}
	}
	

	@Test
	public void readFileWithCopyrightTest() {
		final String fileName = "/Users/richardcox/Documents/cipher-challenge/plain_test.txt";

		final List<String> testLines = new ArrayList<String>();
		testLines.add("                     154");
		testLines.add("  The little Love-god lying once asleep,");
		testLines.add("  Laid by his side his heart-inflaming brand,");
		testLines.add("  Whilst many nymphs that vowed chaste life to keep,");
		testLines.add("  Came tripping by, but in her maiden hand,");
		testLines.add("  The fairest votary took up that fire,");
		testLines.add("  Which many legions of true hearts had warmed,");
		testLines.add("  And so the general of hot desire,");
		testLines.add("  Was sleeping by a virgin hand disarmed.");
		testLines.add("  This brand she quenched in a cool well by,");
		testLines.add("  Which from Love's fire took heat perpetual,");
		testLines.add("  Growing a bath and healthful remedy,");
		testLines.add("  For men discased, but I my mistress' thrall,");
		testLines.add("    Came there for cure and this by that I prove,");
		testLines.add("    Love's fire heats water, water cools not love.");
		testLines.add("");
		testLines.add("");
		testLines.add("THE END");
		testLines.add("");
		testLines.add("");
		testLines.add("");
		testLines.add("<<THIS ELECTRONIC VERSION OF THE COMPLETE WORKS OF WILLIAM");
		testLines.add("SHAKESPEARE IS COPYRIGHT 1990-1993 BY WORLD LIBRARY, INC., AND IS");
		testLines.add("PROVIDED BY PROJECT GUTENBERG ETEXT OF ILLINOIS BENEDICTINE COLLEGE");
		testLines.add("WITH PERMISSION.  ELECTRONIC AND MACHINE READABLE COPIES MAY BE");
		testLines.add("DISTRIBUTED SO LONG AS SUCH COPIES (1) ARE FOR YOUR OR OTHERS");
		testLines.add("PERSONAL USE ONLY, AND (2) ARE NOT DISTRIBUTED OR USED");
		testLines.add("COMMERCIALLY.  PROHIBITED COMMERCIAL DISTRIBUTION INCLUDES BY ANY");
		testLines.add("SERVICE THAT CHARGES FOR DOWNLOAD TIME OR FOR MEMBERSHIP.>>");
		testLines.add("");
		testLines.add("");
		testLines.add("");
		testLines.add("");
		testLines.add("");
		testLines.add("1603");
		testLines.add("");
		testLines.add("ALLS WELL THAT ENDS WELL");
		testLines.add("");
		testLines.add("by William Shakespeare");
		testLines.add("");
		testLines.add("");
		testLines.add("Dramatis Personae");
		
		final List<String> verifyLines = new ArrayList<String>();
		verifyLines.add("                     154");
		verifyLines.add("  The little Love-god lying once asleep,");
		verifyLines.add("  Laid by his side his heart-inflaming brand,");
		verifyLines.add("  Whilst many nymphs that vowed chaste life to keep,");
		verifyLines.add("  Came tripping by, but in her maiden hand,");
		verifyLines.add("  The fairest votary took up that fire,");
		verifyLines.add("  Which many legions of true hearts had warmed,");
		verifyLines.add("  And so the general of hot desire,");
		verifyLines.add("  Was sleeping by a virgin hand disarmed.");
		verifyLines.add("  This brand she quenched in a cool well by,");
		verifyLines.add("  Which from Love's fire took heat perpetual,");
		verifyLines.add("  Growing a bath and healthful remedy,");
		verifyLines.add("  For men discased, but I my mistress' thrall,");
		verifyLines.add("    Came there for cure and this by that I prove,");
		verifyLines.add("    Love's fire heats water, water cools not love.");
		verifyLines.add("");
		verifyLines.add("");
		verifyLines.add("THE END");
		verifyLines.add("");
		verifyLines.add("");
		verifyLines.add("");
		verifyLines.add("");
		verifyLines.add("");
		verifyLines.add("");
		verifyLines.add("");
		verifyLines.add("");
		verifyLines.add("1603");
		verifyLines.add("");
		verifyLines.add("ALLS WELL THAT ENDS WELL");
		verifyLines.add("");
		verifyLines.add("by William Shakespeare");
		verifyLines.add("");
		verifyLines.add("");
		verifyLines.add("Dramatis Personae");
		
		new MockUp<java.nio.file.Files>() {
			@Mock
			List<String> readAllLines(Path path, Charset cs) throws IOException {
				return testLines;
			}
		};
		
		try {
			ReadFile rf = new ReadFile(fileName);
			assertEquals(verifyLines, rf.getLines());
		}
		catch (IOException ioe) {
			fail();
		}	
	}

	
	@Test
	public void getNextLetterTest() {
		final String fileName = "/Users/richardcox/Documents/cipher-challenge/plain_test.txt";
		final String testLine = "TheProjectGutenbergEBookofTheCompleteWorksofWilliamShakespearebyWilliamShakespeare";
		ReadFile rf = null;
		
		try {
			rf = new ReadFile(fileName);
		}
		catch (IOException ioe) {
			fail();
		}
		
		rf.resetStream(0, 0);
		for (int i = 0; i < testLine.length(); i++) {
			char c = rf.getNextLetter();
			System.out.println(c);
			assertEquals(testLine.charAt(i), c);
		}
	}
}
