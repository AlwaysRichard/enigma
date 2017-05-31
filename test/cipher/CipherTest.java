package cipher;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import textfiles.ReadFile;

public class CipherTest {

	@Test
	public void testEncrypted() {
		final String clearnFileName = "/Users/richardcox/Documents/cipher-challenge/plain.txt";
		final String referenceFileName = "/Users/richardcox/Documents/cipher-challenge/encrypted.txt";
		final String translatedFileName = "/Users/richardcox/Documents/cipher-challenge/encrypted_translated.txt";
		
		try {
			ReadFile cleanFileObj = new ReadFile(clearnFileName);
			ReadFile referenceFileObj = new ReadFile(referenceFileName);
			
			Cipher cipher = new Cipher(cleanFileObj, referenceFileObj, translatedFileName, 4); 
		}
		catch (IOException ioe) {
			fail();
		}
	}
	
	@Test
	@Ignore
	public void testEncryptedHard() {
		final String clearnFileName = "/Users/richardcox/Documents/cipher-challenge/plain.txt";
		final String referenceFileName = "/Users/richardcox/Documents/cipher-challenge/encrypted_hard.txt";
		final String translatedFileName = "/Users/richardcox/Documents/cipher-challenge/encrypted_hard_translated.txt";
		
		try {
			ReadFile cleanFileObj = new ReadFile(clearnFileName);
			ReadFile referenceFileObj = new ReadFile(referenceFileName);
			
			Cipher cipher = new Cipher(cleanFileObj, referenceFileObj, translatedFileName, 4); 
		}
		catch (IOException ioe) {
			fail();
		}
	}

}
