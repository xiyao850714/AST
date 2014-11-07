package insert;

import java.util.Random;

/**
 * 
 * @author xiyao
 * 
 */
public class Client {

	private char[] characters;
	private Random random;
	private int sourceLength;

	public void RandomChinese(CharacterSetType type, int seed) {
		switch (type) {
		case AllSet:
			this.characters = CharacterSet.Unicode_22000_22FFF.toCharArray();
			break;
		case Euro:
			this.characters = CharacterSet.Euro.toCharArray();
			break;
		case GB2312_Euro:
			// this.characters = "";
			break;
		default:
			this.characters = CharacterSet.Unicode_22000_22FFF.toCharArray();
		}
		sourceLength = this.characters.length;
		random = seed != 0 ? new Random(seed) : new Random();
	}

	public String GetString(int count) {
		StringBuilder sb = new StringBuilder(count);
		for (int i = 0; i < count; i++)
			sb.append(characters[random.nextInt(sourceLength)]);
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {

//		myTest();

		Client client = new Client();

		client.RandomChinese(CharacterSetType.Euro, 0);

		int maxLength = 50;

		Random r = new Random();

		int randomLength = r.nextInt(maxLength);

		for (int i = 0; i < 100; i++) {

			randomLength = randomLength == 0 ? 1 : randomLength;

			System.out.println(client.GetString(randomLength));
			randomLength = r.nextInt(maxLength);
		}
	}

//	public static void myTest() throws FileNotFoundException, IOException {
//		Properties p = new Properties();
//		System.out.println(System.getProperty("user.dir"));
//		p.load(new FileInputStream("src/com/oracle/dbsharp/my.properties"));
//
//		p.getProperty("a");
//		p.getProperty("test1");
//	}

}
