package com.bittorentlike.chunks;

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
    
    
}
