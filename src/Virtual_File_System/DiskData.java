
package Virtual_File_System;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.FileSystem;
import java.util.ArrayList;

public class DiskData implements Serializable{
    
    private static DiskData _diskData;
    private ArrayList<Integer> diskBloks;
    private int diskSize;
    private int diskAvailablySize;
    private Directory root;
    public ArrayList<ArrayList<Integer> > IndexdTables ;


    

    public void IntialDiskData(int size)
    {
        
        IndexdTables =  new ArrayList<ArrayList<Integer> >();
        diskSize=size;
        diskAvailablySize=size;
        diskBloks=new ArrayList<>();
        for(int i=0;i<diskSize;i++)
        {
            diskBloks.add(0);
        }
        Directory d=new Directory();
        d.setName("root");
        d.setDirectoryPath("root");
        root=d;
        
      
    }

    private DiskData(){
        
    }
    public static DiskData getDiskData(){
        if(_diskData == null){
            _diskData = new DiskData();
        }
        return _diskData;
    }

    public int getDiskAvailablySize() {
        return diskAvailablySize;
    }

    public void setDiskAvailablySize(int diskAvailablySize) {
        this.diskAvailablySize = diskAvailablySize;
    }
    

    public ArrayList<Integer> getDiskBloks() {
        return diskBloks;
    }

    public void setDiskBloks(ArrayList<Integer> diskBloks) {
        this.diskBloks = diskBloks;
    }

    public int getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(int diskSize) {
        this.diskSize = diskSize;
    }

    public Directory getRoot() {
        return root;
    }

    public void setRoot(Directory root) {
        this.root = root;
    }
    
    
    public void displayDiskBlocks(){
        System.out.println("---------------------------------Disk Blocks---------------------------------");
        for(int i=0;i<diskBloks.size();i++)
            System.out.print(diskBloks.get(i));
        System.out.println();
    }
    public void DisplayDiskStatus(){
        System.out.println("Disk Space: "+diskSize);
        System.out.println("Empty Space: "+diskAvailablySize);
        System.out.println("Allocated Space: "+(diskSize-diskAvailablySize));
        displayDiskBlocks();
    }
    
    public static void initialize(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        _diskData = (DiskData) objectIn.readObject();
        objectIn.close();
        fileIn.close();
    }

}
