
package Virtual_File_System;

import java.io.Serializable;
import java.util.ArrayList;


public class Directory implements Serializable{
    private String directoryPath;
    private ArrayList<V_File> files;
    private String name;
    private ArrayList<Directory> subDirectories;
    private boolean deleted = false;
		
    public Directory(){
        files=new ArrayList<V_File>();
        subDirectories=new ArrayList<Directory>();
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public ArrayList<V_File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<V_File> files) {
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Directory> getSubDirectories() {
        return subDirectories;
    }

    public void setSubDirectories(ArrayList<Directory> subDirectories) {
        this.subDirectories = subDirectories;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public void printDirectoryStructure(int level) {
        for(int i=0;i<level;i++)
        {
            System.out.print(" ");
        }
        System.out.println("<"+name+">");
        for(int i=0;i<files.size();i++)
        {
            for(int j=0;j<level+2;j++)
            {
                System.out.print(" ");
            }
            System.out.println(files.get(i).getName());
        }
        for(int i=0;i<subDirectories.size();i++)
        {
           
            subDirectories.get(i).printDirectoryStructure(level+2);
        }
        
        if(subDirectories.size()==0)
            return;
    }
    
   

}



