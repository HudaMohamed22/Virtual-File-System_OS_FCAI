/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Virtual_File_System;

import java.io.Serializable;
import java.util.ArrayList;
import static os_h.OS_H.getRandom;

/**
 *
 * @author Hoda
 */
public class IndexedAllocation extends InterfaceAllocation {
    
     public void allocateFile(String path, int file_Size, DiskData _data){
        // firt check path exists or not 
        V_File myfile= null ;
	//check path exists or not 
        boolean flag10= false ;
        String[] splited_Path = path.split("/");
       flag10=checkPath(_data.getRoot(), splited_Path);
       if(flag10 ==true)  // path exists
       { 
            // then check file name 
    	   String fName=splited_Path[splited_Path.length-1];
            boolean  flag= false ;
            for(int i=0 ; i< _data.getRoot().getFiles().size();i++)
            {  
                if(_data.getRoot().getFiles().get(i).getName().equals( fName))     
                    flag= true ;
            }
            if (flag ==false) // file not exists  before 
            {  // check size of file 
                if(_data.getDiskAvailablySize()>= file_Size) // can add file now 
                { 
                    myfile= new V_File();
                    myfile.setName(fName) ;
                    myfile.setFilePath(path);
                    myfile.setFile_size(file_Size);
                    int ind=0;
                    int counter=0;
                    ArrayList<Integer> tempp=new ArrayList<>();
                    while(counter<file_Size)
                    {
               
                        if(_data.getDiskBloks().get(ind)==0)
                        {
                            tempp.add(ind);
                     
                            counter++;
                            _data.getDiskBloks().set(ind, 1);
                        }
                        ind++;
                    }
                    _data.IndexdTables.add(tempp);
                    myfile.getAllocatedBlocks().add(_data.IndexdTables.size()-1);
               //     System.out.println("Virtual_File_System.IndexedAllocation.allocateFile()"+_data.IndexdTables.size()+"   "+_data.IndexdTables.get(_data.IndexdTables.size()-1));
                     //add file in root tree 
                    Directory dTemp=_data.getRoot();
                    for (int i=1;i<splited_Path.length-1;i++)
                    {
                        for(int j=0;j<_data.getRoot().getSubDirectories().size();j++)
                        {
                                //System.out.println(splited_Path[i]+"   "+_data.getRoot().getSubDirectories().get(j).getName());
                            if(splited_Path[i].equals(_data.getRoot().getSubDirectories().get(j).getName()))
                            {
                                 _data.setRoot(_data.getRoot().getSubDirectories().get(j));
                                
                            }
                        }
                     
                    }
                    myfile.setTypeOfAllocation(1);
                    _data.getRoot().getFiles().add(myfile);
                    _data.setRoot(dTemp);
                    int tempAvailablySize=_data.getDiskAvailablySize();
                    _data.setDiskAvailablySize(tempAvailablySize-file_Size);
                   // _data.getRoot().printDirectoryStructure(0);
                                
                    System.out.println("Created Successful");
                } 
                else 
                    {System.out.println("Sorry, there is no enough space"); }
            }   
           else{System.out.println("File is already exists") ;}      
                         
        }
        else{System.out.println("path isnot  exist") ;} 
       
       }
	
    
    @Override
     public void deleteFile(String path , DiskData _data){
        int file_Size=0;
        Directory dTemp=_data.getRoot();
        String[] splited_Path = path.split("/");
        boolean flag =checkPath(_data.getRoot(), splited_Path);
        if(flag==true)
        {
            for (int i=1;i<splited_Path.length-1;i++)
            {
                for(int j=0;j<_data.getRoot().getSubDirectories().size();j++)
                {
                    if(splited_Path[i].equals(_data.getRoot().getSubDirectories().get(j).getName()))
                    {
                        _data.setRoot(_data.getRoot().getSubDirectories().get(j));           
                    }
                }
                     
            }
            for(int i=0;i<_data.getRoot().getFiles().size();i++)
            {
                if((splited_Path[splited_Path.length-1]).equals(_data.getRoot().getFiles().get(i).getName()))
                {
                    file_Size=_data.getRoot().getFiles().get(i).getFile_size();

                    int index=_data.getRoot().getFiles().get(i).getAllocatedBlocks().get(0);
                    
                    for(int j=0; j<_data.IndexdTables.get(index).size();j++)
                    {
                        _data.getDiskBloks().set(_data.IndexdTables.get(index).get(j), 0);
                    }                   
                    _data.getRoot().getFiles().remove(i);
                }
            }
            
            _data.setRoot(dTemp);
            int tempAvailablySize=_data.getDiskAvailablySize();
            _data.setDiskAvailablySize(tempAvailablySize+file_Size);
           // _data.getRoot().printDirectoryStructure(0);
	
            
        }
	else
	{
	    System.out.println(" File Not Found " );
	}
	 
	   
     }
    

    
}
