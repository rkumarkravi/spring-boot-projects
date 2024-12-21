package testprop;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProps {
	public static void main(String[] args) throws IOException {
		System.out.println(getProperties());
	}

	static Properties getProperties() throws IOException {
		FileInputStream fileInputStream = new FileInputStream("some.properties");
		Properties properties = new Properties();
		properties.load(fileInputStream);

		return properties;
	}
}
