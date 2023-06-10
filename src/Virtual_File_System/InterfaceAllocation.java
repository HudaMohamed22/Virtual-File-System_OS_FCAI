package Virtual_File_System;

import java.io.Serializable;
import static jdk.nashorn.internal.objects.NativeDebug.getClass;


public class InterfaceAllocation {
    
    
    public  boolean checkPath(Directory d ,String[] splited_Path){
        boolean bool=true;
        if(!d.getName().equals(splited_Path[0]))
            return false;
        int flag=0;
        for (int i=1;i<splited_Path.length-1;i++)
        {
            bool=false;
            for(int j=0;j<d.getSubDirectories().size();j++)
            {
                //System.out.println(splited_Path[i]+"   "+d.getSubDirectories().get(j).getName());
                if(splited_Path[i].equals(d.getSubDirectories().get(j).getName()))
                {
                    flag=1;
                    d=d.getSubDirectories().get(j);
                    bool=true;
                }
            }
            if(flag==0)
                return false;
            flag=0;
        }
        return bool;
    }
    
    
     public void allocateFile(String path, int file_Size, DiskData _data){
         
     }
     public void deleteFile(String path , DiskData _data){
         System.out.println(".deleteFile()");
     }
     
     
    public void Create_Folder(String path,  DiskData _data)
    {
        
	Directory myDirectory= null ;
        //check path exists or not 
        boolean flag1= false ;
        String[] splited_Path = path.split("/");
        flag1=checkPath(_data.getRoot(), splited_Path);
        if(flag1 ==true)  // path exists
        { 
            Directory dTemp=_data.getRoot();
            boolean  flag= false ;
            // to get last directory 
            
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
           //  check if folder already exist 
             for(int i=0 ; i< _data.getRoot().getSubDirectories().size();i++)
            {  
                if(_data.getRoot().getSubDirectories().get(i).getName().equals( splited_Path[splited_Path.length-1]))     
                    flag= true ;

            }
             //if not 
            if(flag == false)
            {
                //add Folder in root tree 
                

                myDirectory=new Directory();
                myDirectory.setDirectoryPath(path);
                myDirectory.setName(splited_Path[splited_Path.length-1]);
                // last folder to put new folder in
                _data.getRoot().getSubDirectories().add(myDirectory);
                _data.setRoot(dTemp);
                //_data.getRoot().printDirectoryStructure(0);

                System.out.println("Created Successful");
            }
            else{
                System.out.println("Folder is already created");
            }
        }
        else{
            System.out.println("Path Not Found");
        }
    } 

    
    public void Delete_Folder(String path,  DiskData _data ,InterfaceAllocation Alloc)
    {
        
	Directory myDirectory= null ;
        //check path exists or not
        boolean flag1= false ;
        Directory dTemp=_data.getRoot();
        String[] splited_Path = path.split("/");
        // path exists
        flag1=checkPath(_data.getRoot(), splited_Path);
        if(flag1 ==true)  

        { 
            boolean  flag= false ;
            String pathTemp=path;
             for (int i=1;i<splited_Path.length-1;i++)
                {
                    //flag=false;
                    for(int j=0;j<_data.getRoot().getSubDirectories().size();j++)
                    {
                            //System.out.println(splited_Path[i]+"   "+_data.getRoot().getSubDirectories().get(j).getName());
                        if(splited_Path[i].equals(_data.getRoot().getSubDirectories().get(j).getName()))
                        {
                             _data.setRoot(_data.getRoot().getSubDirectories().get(j));
                             
                        }
                    }

                }
             
             
         //    System.out.println(_data.getRoot().getDirectoryPath());
            // then check folder name 

            int index=0;

            for(int i=0 ; i< _data.getRoot().getSubDirectories().size();i++)
            {  
                //System.out.println(_data.getRoot().getSubDirectories().get(i).getName()+"+++"+splited_Path[splited_Path.length-1]);
                if(_data.getRoot().getSubDirectories().get(i).getName().equals( splited_Path[splited_Path.length-1])) 
                {
                    ///////////////////////////////////////////////////////////////////
                    Directory folderToBeDeletedTemp=_data.getRoot();
                   for(int j=0;j<_data.getRoot().getSubDirectories().get(i).getFiles().size();j++)
                    {
                        //System.out.println(_data.getRoot().getSubDirectories().get(i).getFiles().get(j).getFilePath()+"  "+Alloc.getClass().getName());
                        
                        String path_File=_data.getRoot().getSubDirectories().get(i).getFiles().get(j).getFilePath();
                        _data.setRoot(dTemp);
                        
                        Alloc.deleteFile(path_File, _data);
                        
                        _data.setRoot(folderToBeDeletedTemp);
                        
                    }
                    while(_data.getRoot().getSubDirectories().get(i).getSubDirectories().size()!=0)
                    {
                        //System.out.println(_data.getRoot().getSubDirectories().get(i).getSubDirectories().size());
                        for(int j=0;j<_data.getRoot().getSubDirectories().get(i).getSubDirectories().get(0).getFiles().size();j++)
                        {
                            String path_file=_data.getRoot().getSubDirectories().get(i).getSubDirectories().get(0).getFiles().get(j).getFilePath();
                            _data.setRoot(dTemp);
                            Alloc.deleteFile(path_file, _data);
                            _data.setRoot(folderToBeDeletedTemp);
                        }
                        _data.getRoot().getSubDirectories().get(i).getSubDirectories().remove(0);
                        index++;


                    }
                    _data.getRoot().getSubDirectories().remove(i);
                    _data.setRoot(dTemp);
                    //_data.getRoot().printDirectoryStructure(0);
                    flag= true ;
                }

            }
            if(flag == false)
            {
                  System.out.println("Folder not Found");
            }
            else{
                System.out.println("Folder is Deleted");
            }
        }
        else{
            System.out.println("Path Not Found");
        }
    } 
    


}

    

