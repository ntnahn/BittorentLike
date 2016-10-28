package com.bittorentlike.chunks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.bittorentlike.controller.Share;

public class InfoChunk {
    private int m_index;
    private String m_FileName;
    private String m_HashValue;
    private String path;
    public String getM_HashValue() {
        return m_HashValue;
    }

    public void setM_HashValue(String m_HashValue) {
        this.m_HashValue = m_HashValue;
    }

    public int getM_index() {
        return m_index;
    }

    public void setM_index(int m_index) {
        this.m_index = m_index;
    }
    
    public InfoChunk(){
        
    }
    
    public InfoChunk(int index,String fileName, String hashValue){
        this.m_index = index;
        this.m_FileName = fileName;
        this.m_HashValue = hashValue;
    }
    
    public InfoChunk(String chunkInfo){
        if(chunkInfo.contains(".")){
           String[] arrayChunkInfo = chunkInfo.split(".");
           if(arrayChunkInfo[0].contains("_")){
               String[] infoChunkArray = arrayChunkInfo[0].split("_");
               this.m_index = Integer.parseInt(infoChunkArray[0]);
               this.m_FileName = infoChunkArray[1];
               this.m_HashValue = infoChunkArray[2];               
           }
        }
    }
    
    /**
     * @return the m_FileName
     */
    public String getM_FileName() {
        return m_FileName;
    }

    /**
     * @param m_FileName the m_FileName to set
     */
    public void setM_FileName(String m_FileName) {
        this.m_FileName = m_FileName;
    }
    
    public String getFileNameChunk(){
        return this.m_FileName+String.valueOf(this.m_index)+".chunk";
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
    public static InfoChunk getInfoChunkByPath(String path){
    	BufferedReader br = null;
    	InfoChunk chunk = null;
    	try {
    		FileReader fileChunk = new FileReader(path);
        	br = new BufferedReader(fileChunk);
    	    String line = br.readLine();

    	    if (line != null) {
    	    	//get chunk
    	    	chunk = new InfoChunk();
    	    	String[] contents = line.split(Share.specitor);
    	    	chunk.setM_index(Integer.parseInt(contents[0].trim()));
    	    	chunk.setM_FileName(contents[1]);
    	    	chunk.setM_HashValue(contents[2]);
    	    	chunk.setPath(contents[3]);
    	    }
    	    br.close();
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return chunk;
    }
    
    public static ArrayList<InfoChunk> getInfoChunkListByPath(String path){
    	BufferedReader br = null;
    	ArrayList<InfoChunk> infoChunks = new ArrayList<>();
    	try {
    		FileReader fileChunk = new FileReader(path);
        	br = new BufferedReader(fileChunk);
    	    String line = br.readLine();

    	    while (line != null) {
    	    	InfoChunk chunk = new InfoChunk();
    	    	//get chunk
    	    	chunk = new InfoChunk();
    	    	String[] contents = line.split(Share.specitor);
    	    	chunk.setM_index(Integer.parseInt(contents[0].trim()));
    	    	chunk.setM_FileName(contents[1]);
    	    	chunk.setM_HashValue(contents[2]);
    	    	chunk.setPath(contents[3]);
    	    	infoChunks.add(chunk);
    	        line = br.readLine();
    	    }
    	    br.close();
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return infoChunks;
    }
}
