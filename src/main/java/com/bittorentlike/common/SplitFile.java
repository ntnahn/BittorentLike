package com.bittorentlike.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SplitFile {
	public static final int size = 1024;

	public static ArrayList<byte[]> getByteListFromPath(String path) {
		ArrayList<byte[]> listBytes = new ArrayList<>();
		try {
			File file = new File(path);
			FileInputStream f = new FileInputStream(file);
			int remainLength = (int) file.length();
			int offset = 0;
			while (remainLength >= size) {
				byte[] b = new byte[size];
				f.read(b, offset, size);
				listBytes.add(b);
				remainLength = remainLength - size;
				offset += size;
			}

			if (remainLength > 0) {
				byte[] b = new byte[remainLength];
				f.read(b, offset, remainLength);
				listBytes.add(b);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listBytes;
	}
}
