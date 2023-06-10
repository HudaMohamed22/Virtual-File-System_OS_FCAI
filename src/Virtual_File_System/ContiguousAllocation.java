
package Virtual_File_System;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ContiguousAllocation extends InterfaceAllocation {
      
     public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
    {
        List<Map.Entry<Integer, Integer> > list =
               new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet());
 
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
         
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    
     @Override
    public void allocateFile(String path, int file_Size, DiskData _data){
        
        V_File myfile= null ;
	//check path exists or not 
        boolean flag1= false ;
        String[] splited_Path = path.split("/");
        
       flag1=checkPath(_data.getRoot(), splited_Path);
       if(flag1 ==true)  // path exists
       { 
            // then check file exist before or not  
            boolean  flag= false ;
            for(int i=0 ; i< _data.getRoot().getFiles().size();i++)
            {  
                if(_data.getRoot().getFiles().get(i).getName().equals( splited_Path[splited_Path.length-1]))     
                    flag= true ;

            }
            if (flag ==false) // file not exists  before 
            {                	
                HashMap<Integer,Integer> avail_Blocks=new HashMap<>();   //<Start Index , Num of Available Blocks>
                int temp1,temp2;
                for( int i=0; i<_data.getDiskBloks().size() ; i++)
                {
                    if(_data.getDiskBloks().get(i)==0)
                    {
                        temp1=i;   //start 
                    	int New_i= i;
                    	int counter=0;
                    	while(_data.getDiskBloks().get(New_i)==0)
                    	{
                    	    //System.out.println("While"+NewL);
                            counter++;
                            New_i++;
                            if(New_i>=_data.getDiskBloks().size())
                            {
                        	break;
                            }
                    			
                    	}
                        avail_Blocks.put(temp1,counter);
                    	i+=counter;
                    		
                    }	
                    else
                        continue;
                    
                    }
                	// Get min seuence of Blocks 
                   HashMap<Integer,Integer> sorted_avil_Blocks=sortByValue(avail_Blocks);
                   //System.out.println(sorted_avil_Blocks);
            
                   for (HashMap.Entry<Integer, Integer> blocks : sorted_avil_Blocks.entrySet()) {
                   
                        //add myfile data
                        if(blocks.getValue()>= file_Size)
                        {
                            myfile= new V_File();
                            myfile.setFilePath(path);
                            myfile.getAllocatedBlocks().add(blocks.getKey());
                            myfile.setName(splited_Path[splited_Path.length-1]);
                            myfile.setFile_size(file_Size);
                            // change status of blocks to 1 
                            for(int i=0;i<file_Size;i++)
                            {
                                _data.getDiskBloks().set(blocks.getKey()+i, 1);
                            }
                            break;
                        }
                        
                    }
                   if(myfile==null)
                    {System.out.println("No Enough Space!") ;} 
                   
                   else
                   {
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
                       myfile.setTypeOfAllocation(0);
                       _data.getRoot().getFiles().add(myfile);
                       _data.setRoot(dTemp);
                       int tempAvailablySize=_data.getDiskAvailablySize();
                       _data.setDiskAvailablySize(tempAvailablySize-file_Size);
                       //_data.getRoot().printDirectoryStructure(0);
                  }
            }
                else{System.out.println("File is already exists") ;}      
                         
       }  
       else{System.out.println("path isnot  exist") ;} 
        
    }
    
    @Override
    public void deleteFile(String path , DiskData _data){
        //System.out.println("-----------------.ContiguousAllocation."+path);
        _data.getRoot().getName();
        Directory dTemp=_data.getRoot();
        int file_Size = 0;
        String[] splited_Path = path.split("/");
        boolean flag =checkPath(_data.getRoot(), splited_Path);
        if(flag==true)
        {
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
            //System.out.println(_data.getRoot().getName()+"+++++++++++");
            for(int i=0;i<_data.getRoot().getFiles().size();i++)
            {
                if((splited_Path[splited_Path.length-1]).equals(_data.getRoot().getFiles().get(i).getName()))
                {
                    file_Size=_data.getRoot().getFiles().get(i).getFile_size();
                    for(int j=0;j<_data.getRoot().getFiles().get(i).getFile_size();j++)
                    {
                        //System.out.println("-------------------------------"+_data.getRoot().getFiles().get(i).getAllocatedBlocks());
                        int startBolck=_data.getRoot().getFiles().get(i).getAllocatedBlocks().get(0);
                        _data.getDiskBloks().set(j+startBolck, 0);
                    }
                    
                    _data.getRoot().getFiles().remove(i);
                }
            }
            
            _data.setRoot(dTemp);
            int tempAvailablySize=_data.getDiskAvailablySize();
            _data.setDiskAvailablySize(tempAvailablySize+file_Size);
            //_data.getRoot().printDirectoryStructure(0);
            
        }
        else
	{
	    System.out.println(" File Not Found " );
	}
        
    }
    
}
