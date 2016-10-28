package com.bittorentlike.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.bittorentlike.classes.BTLPackage;

public class BTLCommon {
	public static BTLPackage deserializeBytes(byte[] bytes) {
		try {
			ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
	        ObjectInputStream ois = new ObjectInputStream(bytesIn);
	        Object obj = ois.readObject();
	        ois.close();
	        return (BTLPackage) obj;
		} catch(Exception ex) {
			return null;
		}
    }

    public static byte[] serializeObject(Serializable obj) {
    	try {
    		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bytesOut);
            oos.writeObject(obj);
            oos.flush();
            byte[] bytes = bytesOut.toByteArray();
            bytesOut.close();
            oos.close();
            return bytes;
    	} catch(Exception ex) {
    		return new byte[0];
    	}
    }
}
