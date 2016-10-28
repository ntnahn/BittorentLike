package com.bittorentlike.classes;

import java.io.Serializable;

public class BTLPackage implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte type;
    private byte[] unuse;
    private byte[] headerLength;
    private byte[] packageLength;
    private byte[] checksum;
    private byte[] sequenceNumber;
    private byte[] acknowledgeNumber;
    private String option;
    private byte[] data;

    public BTLPackage(byte type, byte[] unuse, byte[] headerLength, byte[] packageLength, byte[] checksum, byte[] sequenceNumber, byte[] acknowledgeNumber, String option, byte[] data ) {
        this.type = type;
        this.unuse = unuse;
        this.headerLength = headerLength;
        this.packageLength = packageLength;
        this.checksum = checksum;
        this.sequenceNumber = sequenceNumber;
        this.acknowledgeNumber = acknowledgeNumber;
        this.option = option;
        this.data = data;
        
    }
    
    public BTLPackage(String option, byte[] data){
        this.type = 0;
        this.unuse = new byte[1];
        this.headerLength = new byte[2];
        this.packageLength = new byte[2];
        this.checksum = new byte[2];
        this.sequenceNumber = new byte[4];
        this.acknowledgeNumber = new byte[4];
        this.option = option;
        this.data = data;     
    }

    public byte[] getAcknowledgeNumber() {
        return acknowledgeNumber;
    }

    public void setCcknowledgeNumber(byte[] acknowledgeNumber) {
        this.acknowledgeNumber = acknowledgeNumber;
    }

    public byte[] getChecksum() {
        return checksum;
    }

    public void setChecksum(byte[] checksum) {
        this.checksum = checksum;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getUnuse() {
        return unuse;
    }

    public void setUnuse(byte[] unuse) {
        this.unuse = unuse;
    }

    public byte[] getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(byte[] headerLength) {
        this.headerLength = headerLength;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
    
    public byte[] getpackageLength() {
        return packageLength;
    }

    public void setpackageLength(byte[] packageLength) {
        this.packageLength = packageLength;
    }

    public byte[] getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(byte[] sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
