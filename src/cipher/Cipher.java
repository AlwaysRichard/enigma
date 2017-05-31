package cipher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import textfiles.FindPattern;
import textfiles.ReadFile;

/**
 * Takes a text file read in using ReadFile performs the following
 *   1. Identifies the line to use to build the word/space/punchuation patterns 
 *   using a line from the encrypted reference file.
 *   
 *   2. Finds the word/space/punchuation pattern in the clear-text file.
 *   
 *   3. Builds alphabet key xross-reference.
 *   
 *   4. Decryptes the encrypted file usig the alphabet cross-reference.
 *   
 *   4. Outputs decrypted text to a new file.
 * 
 * @author richardcox
 *
 */
public class Cipher {

	/** The alphabet offset because ascii(a) = 65. */
	private static int ALPHABET_OFFSET = 65;
	
	/** The alphabet key used to translate the encrypted file. */
	private char[] alphabet = new char[26];
	
	/** The clearFile object used by this class. */
	final private ReadFile clearFile;
	
	/** The encrypted/reference file used by this class. */ 
	final private ReadFile referenceFile;
	
	/**
	 * Takes a clear-file, an encrypted reference file and builds a encryption 
	 * alphabet cross-reference key to decrypt the encrypted filee. Finaly
	 * ouputs decrypted file as specified.
	 * @param clearFile
	 * @param referenceFile
	 * @param translated file output filename.
	 * @param pattern complexity used to ensure a good word/space/punchuation mix.
	 */
	public Cipher(final ReadFile clearFileObj, final ReadFile referenceFileObj, final String translatedOutFileName, final int patternComplexity) {
		
		this.clearFile = clearFileObj;
		this.referenceFile = referenceFileObj;

		blankKey();
		FindPattern fp = new FindPattern();
		
		// Find a line with sutable word/space/punchuation complexity 
		// for building the alphabet key.
		int currentComplexity = 0;
		int currentPatternLine = 0;
		while (currentComplexity < patternComplexity) {
			currentComplexity = fp.buildPattern(referenceFile.getLine(currentPatternLine++));
		}
		
		// Find word/space/punchuation matching in clear text file.
		int lineMatch = fp.findMatchingLine(currentPatternLine - 1, this.clearFile.getLines());
		this.clearFile.resetStream(lineMatch, 0);
		
		// Build alphabet cross-reference key.
		buildKey();
		
		// Decrypt file and save to file.
		List<String> translated = decrypt(this.referenceFile);
		writeFile(translated, translatedOutFileName);
		
	}
	
	/**
	 * 
	 * @return
	 */
	private void buildKey() {
		boolean buildingKey = true;
		while (buildingKey) {
			final char clearChar = Character.toUpperCase(this.clearFile.getNextLetter());
			final char refChar = Character.toUpperCase(this.referenceFile.getNextLetter());
			
			// If getNextLetter = '.' then reached end of the file.
			// If isAlphabetBuild() is true then key is complete.
			if (clearChar == '.' || refChar == '.' || isAlphabetBuild()) {
				return;
			}
			
			// Take encrypted char and matching clear-text character and
			// add to the key. Fail if current clear-text char does not match key,
			final int alphabetPos = (int) refChar - ALPHABET_OFFSET;
			if (alphabet[alphabetPos] == clearChar || alphabet[alphabetPos] == '.') {
				alphabet[alphabetPos] = clearChar;
			}
			else {
				buildingKey = false;
			}
		}
	}
	
	/**
	 * Walk throu encrypted file using alphabet cross-reference key to 
	 * build a clear-text version of the file.
	 * @param decryptFile
	 * @return decrypted array of file lines.
	 */
	private List<String> decrypt(ReadFile decryptFile) {
		final List<String> refLines = decryptFile.getLines();
		List<String> translatedLines = new ArrayList<String>();
		
		for (int i = 0; i < refLines.size(); i++) {
			final String refLine = refLines.get(i);
			StringBuffer translatedLine = new StringBuffer();
			
			for (int j = 0; j < refLine.length(); j++) {
				char refChar = Character.toUpperCase(refLine.charAt(j));
				if (Character.isLetter(refChar)) {
					final int pos = (int) refChar - ALPHABET_OFFSET;
					translatedLine.append(alphabet[pos]);
				}
				else {
					translatedLine.append(refChar);
				}
			}
			translatedLines.add(translatedLine.toString());
		}
		
		return translatedLines;

	}
	
	/**
	 * Output file as UTF-8.
	 * @param outputLines
	 * @param outputFileName
	 */
	private void writeFile(final List<String> outputLines, final String outputFileName) {
		try{
		    PrintWriter writer = new PrintWriter(outputFileName, "UTF-8");
		    for (int i = 0; i < outputLines.size(); i++) {
		    	writer.println(outputLines.get(i));
		    }
		    writer.close();
		} 
		catch (IOException e) {
			/* do nothing */
		}
	}
	
	/**
	 * Determines if alphabet cross-reference key is complete.
	 * @return
	 */
	private boolean isAlphabetBuild() {
		for (int i = 1; i < alphabet.length; i++) {
			if (alphabet[i] == '.') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Blanks key for initial and additional tries.
	 */
	private void blankKey() {
		final int alphabetLen = alphabet.length;
		for (int i = 0; i < alphabetLen; i++) {
			alphabet[i] = '.';
		}
	}
}
