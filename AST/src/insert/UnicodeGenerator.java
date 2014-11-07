package insert;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;

/**
 * 
 * @author xiyao
 * 
 */

public class UnicodeGenerator {

	public static void main(String[] args) throws Exception {
		// generateUTF8_ChineseString();
		// generateUTF8_Arabic();
		// generateUTF8_Latin1();
		// generateUTF8_Super();
		test();
	}

	private static boolean isAppended = false;

	public static void generateUTF8_ChineseString()
			throws UnsupportedEncodingException {
		// Chinese
		int start = Integer.valueOf("4E00", 16);
		int end = Integer.valueOf("9FA5", 16);

		Writer writer = null;

		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(
							"conf/unicode.properties", isAppended), "utf-8"));
			writer.write("UTF8_Chinese=");

			// Chinese
			for (int i = start; i <= end; i++) {
				writer.write("\\u" + Integer.toHexString(i));
			}
			writer.write("\n");
			isAppended = true;
		} catch (IOException ex) {
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}

	}

	public static void generateUTF8_Latin1()
			throws UnsupportedEncodingException {

		int start = Integer.valueOf("00A1", 16);
		int end = Integer.valueOf("00FF", 16);

		Writer writer = null;

		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(
							"conf/unicode.properties", isAppended), "utf-8"));
			writer.write("UTF8_Latin1=");

			for (int i = start; i <= end; i++) {
				writer.write("\\u00" + Integer.toHexString(i));
			}
			writer.write("\n");
			isAppended = true;
		} catch (IOException ex) {
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}

	}

	public static void generateUTF8_Arabic()
			throws UnsupportedEncodingException {

		// Arabic
		int start_Arabic = Integer.valueOf("0600", 16);
		int end_Arabic = Integer.valueOf("06FF", 16);

		Writer writer = null;

		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(
							"conf/unicode.properties", isAppended), "utf-8"));
			writer.write("UTF8_Arabic=");

			// Arabic
			for (int i = start_Arabic; i <= end_Arabic; i++) {
				writer.write("\\u0" + Integer.toHexString(i));
			}
			writer.write("\n");
			isAppended = true;
		} catch (IOException ex) {
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}

	}

	public static void generateUTF8_Super() throws UnsupportedEncodingException {

		Properties p = new Properties();
		Writer writer = null;
		try {
			p.load(new FileInputStream("conf/characterSetRange.properties"));
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("conf/unicode.properties", false),
					"utf-8"));
			for (Entry<Object, Object> entry : p.entrySet()) {
				// read {0} from {0}={1}-{2}-{3} in characterSetRange.properties
				writer.write((String) entry.getKey() + "=");

				String[] ranges = ((String) entry.getValue()).split("-");

				// read start={1} & end={2} from {0}={1}-{2}-{3} in
				// characterSetRange.properties
				int start = Integer.valueOf(ranges[0], 16);
				int end = Integer.valueOf(ranges[1], 16);

				// if has {3} then read balance={3} from {0}={1}-{2}-{3} in
				// characterSetRange.properties
				String balance = ranges.length > 2 ? ranges[2] : "";

				for (int i = start; i <= end; i++) {
					writer.write("\\u" + balance + Integer.toHexString(i));
				}
				writer.write("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}

	}

	public static String test() throws Exception {
		Properties p = new Properties();

		p.load(new FileInputStream("conf/unicode.properties"));

		char[] UTF8_Syriac = p.getProperty("UTF8_Syriac").toCharArray();
		// char[] values_Latin1 = p.getProperty("UTF8_Latin1").toCharArray();
		// char[] values_Arabic = p.getProperty("UTF8_Arabic").toCharArray();

		int maxLength = 50;

		Random r = new Random();

		int randomLength = r.nextInt(maxLength);

//		for (int i = 0; i < 100; i++) {
//
//			randomLength = randomLength == 0 ? 1 : randomLength;
//
//			System.out.println(getRandomString(randomLength, UTF8_Syriac));
//			// System.out.println(getRandomString(randomLength, values_Latin1));
//			// System.out.println(getRandomString(randomLength, values_Arabic));
//			randomLength = r.nextInt(maxLength);
//		}
		randomLength = randomLength == 0 ? 1 : randomLength;
//		System.out.println("------------" + getRandomString(randomLength, UTF8_Syriac));
        return getRandomString(randomLength, UTF8_Syriac);
	}

	private static Random random = new Random();

	private static String getRandomString(int count, char[] characters) {
		StringBuilder sb = new StringBuilder(count);

		for (int i = 0; i < count; i++)
			sb.append(characters[random.nextInt(characters.length)]);
		return sb.toString();
	}
}
