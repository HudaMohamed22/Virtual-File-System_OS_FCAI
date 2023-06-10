
package Virtual_File_System;

import java.io.Serializable;
import java.util.ArrayList;


public class V_File implements Serializable{
    private String filePath;
    private ArrayList<Integer> allocatedBlocks;
    private int file_size;
    private String name;
    private boolean deleted;
    private int typeOfAllocation;   //(0 to ContiguousAllocation - 1 to LinkedAllocation - 3 to IndexAllocation )

    public V_File() {
        allocatedBlocks=new ArrayList<>();
        deleted=false;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }

    public int getTypeOfAllocation() {
        return typeOfAllocation;
    }

    public void setTypeOfAllocation(int typeOfAllocation) {
        this.typeOfAllocation = typeOfAllocation;
    }
    
    

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Integer> getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public void setAllocatedBlocks(ArrayList<Integer> allocatedBlocks) {
        this.allocatedBlocks = allocatedBlocks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    

}
