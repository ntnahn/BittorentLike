package com.bittorentlike.common;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CombineFile {
	ArrayList<byte[]> fileSplit = new ArrayList<>();
	
	public void addPart(byte[] part) {
		fileSplit.add(part);
	}
	
	public void doCreateFile(String filePath) {
        try {
        	FileOutputStream fos = new FileOutputStream(new File("D:\\def.mp3"));
            for ( byte[] part : fileSplit ) {
                fos.write(part, 0, part.length);
            }       
            fos.close();
        } catch(Exception ex) {
        	
        }
	}
}
