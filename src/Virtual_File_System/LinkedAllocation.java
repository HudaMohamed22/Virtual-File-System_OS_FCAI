package Virtual_File_System;

import java.io.Serializable;

public class LinkedAllocation extends InterfaceAllocation {
    
    @Override
    public void allocateFile(String path, int file_Size, DiskData _data){
      
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
                    myfile.setName( fName) ;
                    myfile.setFilePath(path);
                    myfile.setFile_size(file_Size);
                    int ind=0;
                    int counter=0;
                    while(counter<file_Size)
                    {
                                	
                        if(_data.getDiskBloks().get(ind)==0)
                        {
                               
                            myfile.getAllocatedBlocks().add(ind);
                                		
                            counter++;
                            _data.getDiskBloks().set(ind, 1);
                        }
                        ind++;
                    }
                     
                    Directory dTemp=_data.getRoot();
                 //   to get folder of file
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
                    
                    for(int j=0;j<_data.getRoot().getFiles().get(i).getFile_size();j++)
                    {
                        int blockIndex=_data.getRoot().getFiles().get(i).getAllocatedBlocks().get(j);
                        _data.getDiskBloks().set(blockIndex, 0);
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
