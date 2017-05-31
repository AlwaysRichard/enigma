package textfiles;

import java.util.List;

/**
 * This class builds REGEX patterns based on word/space/punchuaction. Then searches
 * lines of text looking for the matching patterns in simular files. 
 * @author richardcox
 *
 */
public class FindPattern {
	
	/** REGEX pattern shared between methods. */
	private String pattern;

	/**
	 * Constructor.
	 */
	public FindPattern() {
		/* empty constructor */
	}
	
	/**
	 * Builds a regex expression based on the word/space/punchuation patterns.
	 * @param patternLine the line from the reference crypted file.
	 * @return int pattern complexity.
	 */
	public int buildPattern(final String patternLine) {
		final StringBuffer pattern = new StringBuffer(); 
		final int length = patternLine.length();
		int paternComplexity = 0;
		
		for (int i = 0; i < length; i++) {
			
			char ascii = patternLine.charAt(i);
			
			// Matches [A-Za-z] using \w.
			if (String.valueOf(ascii).matches(".")) {
				if (Character.isLetter(ascii)) {
					pattern.append("\\w");
				}
				
				// Matches digit [0-9].
				else if (Character.isDigit(ascii)) {
					pattern.append(ascii);
					paternComplexity++;
				}
				
				// Matches spaces.
				else if (ascii == ' ') {
					pattern.append(ascii);
					paternComplexity++;
				}
				
				// Matches special characters `~!@#%&-_=]},<> not requireing
				// a REGEX escape character.
				else if (String.valueOf(ascii).matches("(`|~|!|@|#|%|&|-|_|=|]|}|,|<|>)")) {
					pattern.append(ascii);
					paternComplexity++;
				}
				
				// Matches special characters $^*()+{||;:'".? that require
				// a REGEX escape character.
				else if (String.valueOf(ascii).matches("(\\$|\\^|\\*|\\(|\\)|\\+|\\[|\\\\|\\||\\;|\\:|\\'|\\\"|\\.|\\/|\\?)")) {
					pattern.append("\\").append(ascii);
					paternComplexity++;
				}
			}
		}
		
		this.pattern = pattern.toString();
		
		return paternComplexity;
	}
	
	/**
	 * Reads each line in the clear file to find a matching word/space/punctuation pattern
	 * @param line offset into file 
	 * @param searchFile lines of text to be searched
	 * @return line number wher pattern is fond or -1 for no matches.
	 */
	public int findMatchingLine(final int offset, final List<String> searchFile) {
		
		final int numLines = searchFile.size();
		int line;
		for (line = offset; line < numLines; line++) {
			if (searchFile.get(line).matches(this.pattern)) {
				break;
			}
		}
		
		return line < numLines ? line : -1;
	}
	
	/**
	 * Returns REGEX pattern.
	 * @return
	 */
	public String getPattern() {
		return this.pattern;
	}
	
}
