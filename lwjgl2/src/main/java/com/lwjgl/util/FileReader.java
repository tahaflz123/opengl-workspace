package com.lwjgl.util;

import java.io.InputStream;
import java.util.Scanner;

public class FileReader {
	
	public static String loadResource(String fileName) {
        String result = null;
        
       InputStream in = FileReader.class.getResourceAsStream(fileName);
       Scanner scanner = new Scanner(in, java.nio.charset.StandardCharsets.UTF_8.name());
       result = scanner.useDelimiter("\\A").next();
       scanner.close();
       return result;
    }
	
}
