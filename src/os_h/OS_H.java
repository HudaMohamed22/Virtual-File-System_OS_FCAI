
package os_h;

import Virtual_File_System.ContiguousAllocation;
import Virtual_File_System.Directory;
import Virtual_File_System.DiskData;
import Virtual_File_System.IndexedAllocation;
import Virtual_File_System.InterfaceAllocation;
import Virtual_File_System.LinkedAllocation;
import Virtual_File_System.V_File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;





public class OS_H {

   public static int getRandom(int max){ return (int) (Math.random()*max); }
    
    private static void intializer(){
                
        DiskData _data=DiskData.getDiskData();
        _data.IntialDiskData(100);
        InterfaceAllocation IAllocate = new ContiguousAllocation();
        IAllocate.Create_Folder("root/folder1", _data);
        IAllocate.Create_Folder("root/folder1/folder1_A", _data);
        IAllocate.Create_Folder("root/folder1/folder1_B", _data);
        IAllocate.Create_Folder("root/folder2", _data);
        IAllocate.allocateFile("root/folder1/file1.txt", 10, _data);
        IAllocate.allocateFile("root/folder2/file1.txt", 10, _data);
        IAllocate.allocateFile("root/folder2/file2.txt", 10, _data);
        _data.DisplayDiskStatus();
        //IAllocate.Delete_Folder("root/folder2", _data);
        IAllocate.deleteFile("root/folder2/file1.txt", _data);
      _data.DisplayDiskStatus();
    }


    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        
       
        String structurePath = "DiskStructure.txt";
        File diskStructure = new File(structurePath);
        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
    
        if (diskStructure.createNewFile() || diskStructure.length() == 0) {
            System.out.println("Please Enter disk size.");
            int diskSize = scanner.nextInt();
            DiskData.getDiskData().IntialDiskData(diskSize);
        } else {
            DiskData.initialize(structurePath);
        }
        
        InterfaceAllocation allocation = null;
        while (true) {
            System.out.println("Choose Allocation Method:\n1- Contiguous Allocation\n2- Linked Allocation\n3- Indexed Allocation");
            int choiceInput = scanner.nextInt();
            int flag =0;
            switch (choiceInput){
                case 1:
                    allocation=new ContiguousAllocation();
                    flag=1;
                    break;
                case 2:
                    allocation=new LinkedAllocation();
                    flag=1;
                    break;
                case 3:
                    allocation=new IndexedAllocation();
                    flag=1;
                    break;
                default:
                    System.out.println("Enter valid choice ): ");
            }
            if(flag==1)
                break;
            
            
        }
        
       
        
        String command;
        while (true) {
            System.out.print(">>");
            command = scanner2.nextLine();
            String[] arguments = command.split(" ");
            if(arguments[0].equals("CreateFile") && arguments.length == 3){
                allocation.allocateFile(arguments[1], Integer.parseInt(arguments[2]), DiskData.getDiskData());
            }
            else if (arguments[0].equals("CreateFolder") && arguments.length == 2){
                allocation.Create_Folder(arguments[1], DiskData.getDiskData());
            }
            else if (arguments[0].equals("DeleteFile") && arguments.length == 2){
               allocation.deleteFile(arguments[1], DiskData.getDiskData()); ///////
            }
            else if (arguments[0].equals("DeleteFolder") && arguments.length == 2){
                allocation.Delete_Folder(arguments[1], DiskData.getDiskData(),allocation);
            }
            else if (arguments[0].equals("DisplayDiskStatus")){
                DiskData.getDiskData().DisplayDiskStatus();
            }
            else if (arguments[0].equals("DisplayDiskStructure")){
                DiskData.getDiskData().getRoot().printDirectoryStructure(0);
            }
            else if (arguments[0].equals("systemblocks")){
                DiskData.getDiskData().displayDiskBlocks();
            }
            else if(arguments[0].equals("exit")){
                break;
            }
            else{
                System.out.println("Invalid Command");
            }            
            
        }
        
        



      intializer();
        //DiskData.getDiskData().DisplayDiskStatus();
//        FileOutputStream fileOut = new FileOutputStream(diskStructure);
//        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//        objectOut.writeObject(DiskData.getDiskData());
//        objectOut.close(); 
        
    }
    
}
