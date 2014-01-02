package atlas.kingj.roi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Security {
	
	private static String DATA_LOC_231 = "bpi5sd2j";
	private static int SALT_LENGTH = 512;
	private static int HASH_LENGTH = 32;
	private static int TIME_LENGTH = 16;
	private static int TOT_LENGTH = 584;
	private static String DATA_LOC_431 = "gpa9wmsf";
	private static String one = "vÄ²@ñÎåámùŒk³‹oÂºþH#Š";
	private static String two = "ìi;Gá5è=Î/ƒ¬”ž¾}";
	
	private int mode; // 1 == CHECK   2 == WRITE
	private long starttime;
	private long endtime;
	private File location;
	public long getStartTimestamp(){ return starttime; }
	public long getEndTimestamp(){ return endtime; }
	
	public Security(){
		mode = 1;
	}
	public Security(int mode){
		this.mode = mode;
	}
	public Security(int mode, String loc){
		this.mode = mode;
		location = new File(loc);
	}
	public Security(int mode, File loc){
		this.mode = mode;
		location = loc;
	}
	public Security(int mode, String loc, long time1, long time2){
		this.mode = mode;
		location = new File(loc);
		starttime = time1;
		endtime = time2;
	}
	
	public void setMode(int mode){
		this.mode = mode;
	}
	
	public void set(String filename){
		// Licence file location
		location = new File(filename);
	}
	
	public void set(String filename, long time1, long time2){
		// Licence file location
		location = new File(filename);
		// Start & end times for licence
		this.starttime = time1;
		this.endtime = time2;
	}
	
	public boolean CheckLicence(){
		if(mode == 2)
			return false;
		else{
			
			try{
				// Allocate bytes to read from licence file
				byte[] readin = new byte[TOT_LENGTH];
				
				// Perform read into byte array
				if(!ReadFromFile(readin, location))
					return false;
				
				// Decrypt input
				byte[] input = decrypt(readin, DATA_LOC_231);
				
				// Divide byte array into the 4 separate data items: salt, hash, start_time, end_time
				byte[] readsalt = new byte[SALT_LENGTH];
				byte[] readdata = new byte[HASH_LENGTH];
				byte[] readtime1 = new byte[TIME_LENGTH];
				byte[] readtime2 = new byte[TIME_LENGTH];
				System.arraycopy(input, 0, readsalt, 0, SALT_LENGTH);
				System.arraycopy(input, SALT_LENGTH, readdata, 0, HASH_LENGTH);
				System.arraycopy(input, SALT_LENGTH + HASH_LENGTH, readtime1, 0, TIME_LENGTH);
				System.arraycopy(input, SALT_LENGTH + HASH_LENGTH + TIME_LENGTH, readtime2, 0, TIME_LENGTH);
				
				// Perform hash using the salt we just read in
				byte[] resultcheck = getHash(one + getSerialNumber() + two, readsalt);
				String A = new String(readdata);
				String B = new String(resultcheck);
				
				// Check against the hash we read in
				if(A.equals(B)){
					// hash ok
					// Decrypt time data
					byte[] timedec1 = decrypt(readtime1, DATA_LOC_431);
					byte[] timedec2 = decrypt(readtime2, DATA_LOC_431);
					
					starttime = toLong(timedec1);
					endtime = toLong(timedec2);
					
					return true;
				}else{
					// bad hash
					return false;
				}
			
			} catch (NoSuchAlgorithmException e) {
				return false;
			} catch (UnsupportedEncodingException e) {
				return false;
			} catch (InvalidKeyException e) {
				return false;
			} catch (NoSuchPaddingException e) {
				return false;
			} catch (IllegalBlockSizeException e) {
				return false;
			} catch (BadPaddingException e) {
				return false;
			} catch (Exception e){
				return false;
			}
		}
	}

	public boolean SaveLicence(){
		if(mode == 1)
			return false;
		else{
		
			// Generate the salt from a secure pseudorandom generator
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[512];
			random.nextBytes(salt);
			
			// Allocate byte arrays
			byte[] timebyte1 = toByteArray(starttime);
			byte[] timebyte2 = toByteArray(endtime);
			byte[] enctime1 = null;
			byte[] enctime2 = null;
			
			try {
				// Encrypt time data
				enctime1 = encrypt(timebyte1, DATA_LOC_431);
				enctime2 = encrypt(timebyte2, DATA_LOC_431);
			
				// Get system serial no.
				String serial = one + getSerialNumber() + two;
				
				byte[] result = null;
			
				// Perform hash
				result = getHash(serial, salt);
			
				// Combine data to save into 1 byte array: [salt  hash  time_start  time_end]
				byte[] output = new byte[salt.length + result.length + enctime1.length + enctime2.length];
				System.arraycopy(salt, 0, output, 0, salt.length);
				System.arraycopy(result, 0, output, salt.length, result.length);
				System.arraycopy(enctime1, 0, output, salt.length + result.length, enctime1.length);
				System.arraycopy(enctime2, 0, output, salt.length + result.length + enctime1.length, enctime2.length);
				
				// Encrypt everything
				byte[] final_out = encrypt(output, DATA_LOC_231);

				// Save file
				return WriteToFile(final_out, location);
				
			} catch (InvalidKeyException e1) {
				return false;
			} catch (NoSuchAlgorithmException e1) {
				return false;
			} catch (NoSuchPaddingException e1) {
				return false;
			} catch (IllegalBlockSizeException e1) {
				return false;
			} catch (BadPaddingException e1) {
				return false;
			} catch (UnsupportedEncodingException e) {
				return false;
			}
		}
	}
	
	// Convert a long to a byte array
	private static byte[] toByteArray(long value){  
		ByteBuffer bb = ByteBuffer.allocate(8);  
		return bb.putLong(value).array();
	}  
	// Convert a byte array to a long
	private static long toLong(byte[] value){  
		ByteBuffer bb = ByteBuffer.wrap(value);
		return bb.getLong();
	}  
	
	// Encrypt a byte array using DES, with a fixed key (insecure)
	private static byte[] encrypt(byte[] in, String pw) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		byte[] encryptedData;
		SecretKey key = new SecretKeySpec(pw.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		encryptedData = cipher.doFinal(in);
		return encryptedData;
	}
	
	// Decrypt with DES using fixed key
	private static byte[] decrypt(byte[] in, String pw) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		byte[] decryptedData;
		SecretKey key = new SecretKeySpec(pw.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		decryptedData = cipher.doFinal(in);
		return decryptedData;
	}
	
	// Read from a file into a byte array
	private static boolean ReadFromFile(byte[] out, File location){
		try{
			FileInputStream fin = new FileInputStream(location);
			fin.read(out);
			fin.close();
			return true;
		}catch(FileNotFoundException ex){
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
	// Write from a byte array into a file
	private static boolean WriteToFile(byte[] out, File location){

		 try{
			 if(!location.getParentFile().exists())
		        	location.getParentFile().mkdirs();
			 FileOutputStream fos = new FileOutputStream(location);
		     fos.write(out);
		     fos.close(); 
		     return true;
		  }
		  catch(FileNotFoundException ex)
		  {
			  return false;
		  }
		  catch(IOException ioe)
		  {
			  return false;
		  }
	}
	
	// Perform a SHA-256 hash, with previously generated salt (secure)
	private static byte[] getHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
		return digest.digest(password.getBytes("UTF-8"));
	}

	// Get the serial number of the machine's mother board. Supports 3 major OS's
	public static final String getSerialNumber(){
		
		String compname = System.getenv("COMPUTERNAME");
		if(compname == null)
			try {
				compname = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				compname = "NONAME";
			}
		String username = System.getenv("USERNAME");
		if(username == null)
			username = System.getProperty("user.name");
		
		String details = compname+username;
		
		OsCheck.OSType ostype=OsCheck.getOperatingSystemType();
		switch (ostype) {
		    case Windows: return details + getSerialNumberWin(); 
		    case MacOS: return details + getSerialNumberMac(); 
		    case Linux: return details + getSerialNumberNix(); 
		    case Other: return details + getSerialNumberWin();
		}
		return null;
	}
	
	// Get MB serial on Windows
	private static final String getSerialNumberWin() {

		String sn = null;

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(new String[] { "wmic", "bios", "get", "serialnumber" });
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scanner sc = new Scanner(is);
		try {
			while (sc.hasNext()) {
				String next = sc.next();
				if ("SerialNumber".equals(next)) {
					sn = sc.next().trim();
					break;
				}
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}

		return sn;
	}
	
	// Get MB serial no. on Mac
	private static final String getSerialNumberMac() {

		String sn = null;

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(new String[] { "/usr/sbin/system_profiler", "SPHardwareDataType" });
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String marker = "Serial Number:";
		try {
			while ((line = br.readLine()) != null) {
				if (line.indexOf(marker) != -1) {
					sn = line.split(marker)[1].trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}

		return sn;
	}

	// Get MB serial no. in Linux (and Unix?)
	private static final String getSerialNumberNix() {

		String sn = null;

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(new String[] { "dmidecode", "-t", "system" });
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String marker = "Serial Number:";
		try {
			while ((line = br.readLine()) != null) {
				if (line.indexOf(marker) != -1) {
					sn = line.split(marker)[1].trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}

		return sn;
	}

	// Determine OS type
	private static final class OsCheck {
	  /**
	   * types of Operating Systems
	   */
	  public enum OSType {
	    Windows, MacOS, Linux, Other
	  };

	  protected static OSType detectedOS;

	  /**
	   * detected the operating system from the os.name System property and cache
	   * the result
	   * 
	   * @returns - the operating system detected
	   */
	  public static OSType getOperatingSystemType() {
	    if (detectedOS == null) {
	      String OS = System.getProperty("os.name", "generic").toLowerCase();
	      if (OS.indexOf("win") >= 0) {
	        detectedOS = OSType.Windows;
	      } else if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
	        detectedOS = OSType.MacOS;
	      } else if (OS.indexOf("nux") >= 0) {
	        detectedOS = OSType.Linux;
	      } else {
	        detectedOS = OSType.Other;
	      }
	    }

	    return detectedOS;
	  }
	}

}
