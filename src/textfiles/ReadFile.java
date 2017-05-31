package textfiles;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Read file reads by line a given filename, it also can get in sequence one-by-one 
 * through each character alpha [A-Aa-x]. 
 * @author richardcox
 *
 */
public class ReadFile {
	
	/** File Encoding. */
	private static final Charset UTF8 = StandardCharsets.UTF_8;

	/** The path and name of ascii file to read. */ 
	private String name;
	
	/** An array holding each line from the file read. */
	private List<String> lines;
	
	/** Current line position when pulling each alpha character. */
	private int currentLine;
	
	/** Current char position when pulling each alpah character. */
	private int currentChar;
	
	/**
	 * Instanciation and loading the file into memory.
	 * @param fileName path and filename.
	 * @exception IOException thron on error.
	 */
	public ReadFile (final String fileName) throws IOException {
		this.name = fileName;
		readFile(UTF8);
	}
	
	/**
	 * Returns the full file path and name.
	 * @return
	 */
	public final String getName() {
		return this.name;
	}
	
	/**
	 * Returns the text as a string.
	 * @return
	 */
	public List<String> getLines() {
		return this.lines;
	}
	
	/**
	 * Resets line and character position to traverse the file again.
	 * @param linePos
	 * @param charPos
	 */
	public void resetStream(final int linePos, final int charPos) {
		this.currentLine = linePos;
		this.currentChar = charPos;
	}
	
	/**
	 * Returns the current line when getting lines one-at-a-time.
	 * @return the line at currentLine.
	 */
	public String getLine() {
		return this.lines.get(this.currentLine++);
	}
	
	/**
	 * Returns a specific line.
	 * @param lineNo
	 * @return The line specified.
	 */
	public String getLine(final int lineNo) {
		return this.lines.get(lineNo);
	}
	
	/**
	 * Scans the file and remembers where it left off in order to return the next
	 * alpha character that is [A-Za-z].
	 * @return char the next alpha character in the file.
	 */
	public char getNextLetter() {
		while (this.currentLine < this.lines.size()) {
			final String l = this.lines.get(this.currentLine);
			while (this.currentChar < l.length()) {
				final char c = l.charAt(this.currentChar++);
				if (Character.isLetter(c)) {
					return c;
				}
			}
			this.currentLine++;
			this.currentChar = 0;
		}
		return '.';
	}
	
	/**
	 * Reads the given fie into memory, but will remove the Gutenberg EBook
	 * copyright notces that are scattered throught the Clear text file.
	 * @param encoding file encoding UTF-8.
	 * @return the number of lines in the file in memory (copyright removed).
	 * @throws IOException
	 */
	private int readFile(Charset encoding) throws IOException {
		List<String> currentLines = Files.readAllLines(Paths.get(this.name), encoding);
		this.lines = new ArrayList<String>();
		int currentLinesLen = currentLines.size();
		boolean inComment = false;
		
		for (int i = 0; i < currentLinesLen; i++) {
			final String lineStr = currentLines.get(i);
			
			if (lineStr.startsWith("<<")) {
				inComment = true;
			}
			
			if (!inComment) {
				this.lines.add(lineStr);
			}
			
			if (lineStr.endsWith(">>")) {
				inComment = false;
			}
			
		}
		return this.lines.size();
	}
	
	
}