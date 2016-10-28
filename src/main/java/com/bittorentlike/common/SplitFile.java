package com.bittorentlike.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SplitFile {
	public static ArrayList<byte[]> getByteListFromPath(String path) {
		ArrayList<byte[]> listBytes = new ArrayList<>();
		try {
			File file = new File(path);
			FileInputStream f = new FileInputStream(file);
			int remainLength = (int) file.length();
			int offset = 0;
			while (remainLength >= BTLConstant.BUFFER_SIZE) {
				byte[] b = new byte[BTLConstant.BUFFER_SIZE];
				f.read(b, 0, BTLConstant.BUFFER_SIZE);
				listBytes.add(b);
				remainLength = remainLength - BTLConstant.BUFFER_SIZE;
				offset += BTLConstant.BUFFER_SIZE;
			}

			if (remainLength > 0) {
				byte[] b = new byte[remainLength];
				f.read(b, 0, remainLength);
				listBytes.add(b);
			}
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listBytes;
	}
}
