package com.example.api.web.util;

import java.io.BufferedReader;
import java.io.IOException;

public class Util {
	
	    public static String converterJsonToString(BufferedReader buffereReader) throws IOException {
	        String resposta, jsonEmString = "";
	        while ((resposta = buffereReader.readLine()) != null) {
	            jsonEmString += resposta;
	        }
	        return jsonEmString;
	    }
	}


