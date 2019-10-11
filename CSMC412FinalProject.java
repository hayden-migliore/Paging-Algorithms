// Author: Hayden Migliore
// Date: October 7th, 2019
// Class: CMSC 412 6380

package csmc412finalproject;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;


public class CSMC412FinalProject {

    //Create Reference String to pass
    static int[] referenceString = new int[40];
    static int rsl = 0;
    static int n = 5;
    static Scanner input = new Scanner(System.in);
    static boolean stringGenerated = false;
    
    public static void main(String[] args) {
        //Begin loop
        while(true){
            System.out.println("\nPlease select an option below.");
            System.out.println("0 - Exit");
            System.out.println("1 - Read reference string");
            System.out.println("2 - Generate reference string");
            System.out.println("3 - Display current reference string");
            System.out.println("4 - Simulate FIFO");
            System.out.println("5 - Simulate OPT");
            System.out.println("6 - Simulate LRU");
            System.out.println("7 - Simulate LFU\n");
            int option = input.nextInt();
            Options(option);
        }
    }
    
    //Switch cases based on user input
    static void Options(int choice){
        switch(choice){
            case 0:             
                Exit();
                break;
            case 1:
                ReadReferenceString();
                break;
            case 2:
                GenerateReferenceString();
                break;
            case 3:
                DisplayReferenceString();
                break;
            case 4:
                FIFO();
                break;
            case 5:
                OPT();
                break;
            case 6: 
                LRU();
                break;
            case 7:
                LFU();
                break;
            case 8:
                System.out.println("ERROR: 8 is not a valid input.");
                break;
            case 9:
                System.out.println("ERROR: 9 is not a valid input.");
                break;
        }
    }
    
    static void Exit(){
        //End application
        System.out.println("Goodbye");
        System.exit(0);
    }
    
    static void ReadReferenceString(){
        //Get length of reference string
        System.out.println("Please enter how long the read reference string will be.");
        rsl = input.nextInt();
        
        //Get reference string from console
        System.out.println("Please enter in " + rsl + " numbers for the reference string.");
        for (int i = 0; i < rsl; i++){
            referenceString[i] = input.nextInt();
        }
        System.out.println("Reference string entered.");

        //Mark that a reference string has been generated
        stringGenerated = true;
    }
    
    static void GenerateReferenceString(){
        //Get length of reference string
        System.out.println("Please enter how long the generated reference string will be.");
        rsl = input.nextInt();
        
        //Generate random string
        System.out.println("Generating reference string.");
        for (int i = 0; i < rsl; i++){
            referenceString[i] = new Random().nextInt(10); 
        }
        System.out.println("Reference string generated.");

        
        //Mark that a reference string has been generated
        stringGenerated = true;
    }
    
    static void DisplayReferenceString(){
        //Check if a reference string has been generated
        if(stringGenerated){
            //Display Reference String
            System.out.println("The current reference string is: ");
            for (int i = 0; i < rsl; i++){
                System.out.print(referenceString[i]);
            }
            System.out.print("\n");
        }
        else{
            System.out.println("ERROR: A reference string has not been generated yet.");
        }
    }
    
    static void FIFO(){
        //Intalize variables
        System.out.println("Simulate FIFO:");
        String[][] printArray = new String[n+2][rsl];
        int[] fifoArray = new int[n];
        for (int i = 0; i < fifoArray.length; i++){
            fifoArray[i] = -1;
        }
        boolean pageFault = true;
        
        //Check that a reference string has been set
        if(!stringGenerated){
            System.out.println("ERROR: A reference string has not been generated yet.");
            return;
        }
        
        //For loop through reference string
        for(int i = 0; i < rsl; i++){
            //Print start
            System.out.print("\nReference String: ");
            for (int a = 0; a < rsl; a++){
                System.out.print(referenceString[a]);
            }
            System.out.println("\nCurrent page: " + referenceString[i]);
            System.out.println("Current FIFO Array: " + Arrays.toString(fifoArray));
            
            //Check for page fault
            for(int j = 0; j < fifoArray.length; j++){
                if (referenceString[i] == fifoArray[j]){
                    pageFault = false;
                }
            }
            
            //If there is a page fault insert number into fifoArray
            if (pageFault){
                System.out.println("Page Fault");
                printArray[n+1][i] = "F";
                
                //Push each page in array back one, remove oldest page
                for (int k = (n - 1); k > 0; k--){
                    fifoArray[k] = fifoArray[k - 1];
                }
                
                //Add page to clear page fault
                fifoArray[0] = referenceString[i];
                
                //Print results
                System.out.println("New FIFO Array: " + Arrays.toString(fifoArray));
            }
            else{
                System.out.println("No Page Fault");
                printArray[n+1][i] = " ";
            }
            
            //Put results in printArray
            for (int p = 0; p < fifoArray.length; p++){
                printArray[p+1][i] = String.valueOf(fifoArray[p]);
            }
            printArray[0][i] = String.valueOf(referenceString[i]);
            
            //Reset for next integer in reference string
            pageFault = true;
        }
        //Print final results
        Print(printArray);
    }
    
    static void OPT(){
        System.out.println("Simulate OPT:");
        //Intalize variables
        String[][] printArray = new String[n+2][rsl];
        int[] optArray = new int[n];
        for (int i = 0; i < optArray.length; i++){
            optArray[i] = -1;
        }
        int[] holder = new int[n];
        int start = 0;
        int count = 0;
        int optimal = -1;
        int max = 0;
        boolean pageFault = true;
        boolean inArray = false;
        boolean found = false;
        
        //Check that a reference string has been set
        if(!stringGenerated){
            System.out.println("ERROR: A reference string has not been generated yet.");
            return;
        }
        
        //For loop through reference string
        for(int i = 0; i < rsl; i++){
            //Print start
            System.out.print("\nReference String: ");
            for (int a = 0; a < rsl; a++){
                System.out.print(referenceString[a]);
            }
            System.out.println("\nCurrent page: " + referenceString[i]);
            System.out.println("Current OPT Array: " + Arrays.toString(optArray));
            
            //Check for page fault
            for(int j = 0; j < optArray.length; j++){
                if (referenceString[i] == optArray[j]){
                    pageFault = false;
                }
            }
            
            //If there is a page fault insert number into fifoArray
            if (pageFault){
                System.out.println("Page Fault");
                printArray[n+1][i] = "F";
                
                //For first n pages to fill optArray
                if (start < n){
                    int index = Arrays.binarySearch(optArray, referenceString[i]); //Check if optArray already contains value
                    if (!(index >= 0)){
                        optArray[start] = referenceString[i];
                        start++;
                    }
                }
                
                //After first n pages
                else{
                    //Look for an optimal that is not in the remaining reference string
                    for (int a = 0; a < optArray.length; a++){
                        for (int b = i; b < rsl; b++){
                            if (referenceString[b] == optArray[a])
                                inArray = true;
                        }
                        if (!inArray){
                            optimal = a;
                            found = true;
                            break;
                        }
                        inArray = false;
                    }
                    //If no optimal was found bc all pages in optArray are still in reference string
                    if (!found){
                        for (int c = 0; c < optArray.length; c++){ //Cycle through optArray
                            count = 0;
                            for (int d = i; d < rsl; d++){ //Compare optArray to String
                                if (optArray[c] == referenceString[d]){ //if match, record place
                                    holder[c] = count; //Fill holder with places
                                    d = rsl; //Found match, go to next in optArray
                                }
                                count++;
                            }
                        }
                        for (int e = 0; e < holder.length; e++){
                            if (holder[e] > max){
                                max = holder[e];
                                optimal = e;
                            }
                        }
                    }  
                    
                    //Replace optimal in optArray with reference string to clear page fault
                    optArray[optimal] = referenceString[i];
                    
                    //Reset for next page
                    found = false;
                    max = 0;
                    for (int f = 0; f < holder.length; f++){
                        holder[f] = 0;
                    }
                }
                
                //Print results
                System.out.println("New OPT Array: " + Arrays.toString(optArray));
            }
            else{
                System.out.println("No Page Fault");
                printArray[n+1][i] = " ";
            }
            
            //Put results in printArray
            for (int p = 0; p < optArray.length; p++){
                printArray[p+1][i] = String.valueOf(optArray[p]);
            }
            printArray[0][i] = String.valueOf(referenceString[i]);
            
            //Reset for next integer in reference string
            pageFault = true;
            count = 0;
        }
        //Print final results
        Print(printArray);
    }
    
    static void LRU(){
        System.out.println("Simulate LRU:");
        //Intalize variables
        String[][] printArray = new String[n+2][rsl];
        int[] lruArray = new int[n];
        for (int i = 0; i < lruArray.length; i++){
            lruArray[i] = -1;
        }
        int[] holder = new int[n];
        int count = 0;
        int start = 0;
        int lru = -1;
        int max = 0;
        boolean pageFault = true;
        
        //Check that a reference string has been set
        if(!stringGenerated){
            System.out.println("ERROR: A reference string has not been generated yet.");
            return;
        }
        
        //For loop through reference string
        for(int i = 0; i < rsl; i++){
            //Print start
            System.out.print("\nReference String: ");
            for (int a = 0; a < rsl; a++){
                System.out.print(referenceString[a]);
            }
            System.out.println("\nCurrent page: " + referenceString[i]);
            System.out.println("Current LRU Array: " + Arrays.toString(lruArray));
            
            //Check for page fault
            for(int j = 0; j < lruArray.length; j++){
                if (referenceString[i] == lruArray[j]){
                    pageFault = false;
                }
            }
            
            //If there is a page fault insert number into lruArray
            if (pageFault){
                System.out.println("Page Fault");
                printArray[n+1][i] = "F";
                
                //For first n pages to fill lruArray
                if (start < n){
                    int index = Arrays.binarySearch(lruArray, referenceString[i]); //Check if lruArray already contains value
                    if (!(index >= 0)){
                        lruArray[start] = referenceString[i];
                        start++;
                    }
                }
                //After first n sections
                else{
                    for (int c = 0; c < lruArray.length; c++){ //Cycle through optArray
                        count = 0;
                        for (int d = i; d > -1; d--){ //Compare lruArray to String
                            if (lruArray[c] == referenceString[d]){ //if match, record place
                                holder[c] = count; //Fill holder with places
                                d = -1; //Found match, go to next in optArray
                            }
                            count++;
                        }
                    }
                    for (int e = 0; e < holder.length; e++){
                        if (holder[e] > max){
                            max = holder[e];
                            lru = e;
                        }
                    }
                    //Replace lru in lruArray with reference string to clear page fault
                    lruArray[lru] = referenceString[i];
                }

                //Print results
                System.out.println("New LRU Array: " + Arrays.toString(lruArray));
                
                //Reset for next page
                max = 0;
                for (int f = 0; f < holder.length; f++){
                    holder[f] = 0;
                }
            }
            else{
                System.out.println("No Page Fault");
                printArray[n+1][i] = " ";
            }
            
            //Put results in printArray
            for (int p = 0; p < lruArray.length; p++){
                printArray[p+1][i] = String.valueOf(lruArray[p]);
            }
            printArray[0][i] = String.valueOf(referenceString[i]);
            
            //Reset for next integer in reference string
            pageFault = true;
        }
        //Print final results
        Print(printArray);
    }
    
    static void LFU(){
        System.out.println("Simulate LFU:");
        //Intalize variables
        String[][] printArray = new String[n+2][rsl];
        int[] lfuArray = new int[n];
        for (int i = 0; i < lfuArray.length; i++){
            lfuArray[i] = -1;
        }
        int[] holder = new int[n];
        int lfu = -1;
        int min = 100;
        int start = 0;
        boolean pageFault = true;
        
        //Check that a reference string has been set
        if(!stringGenerated){
            System.out.println("ERROR: A reference string has not been generated yet.");
            return;
        }
        
        //For loop through reference string
        for(int i = 0; i < rsl; i++){
            //Print start
            System.out.print("\nReference String: ");
            for (int a = 0; a < rsl; a++){
                System.out.print(referenceString[a]);
            }
            System.out.println("\nCurrent page: " + referenceString[i]);
            System.out.println("Current LFU Array: " + Arrays.toString(lfuArray));
            
            //Check for page fault
            for(int j = 0; j < lfuArray.length; j++){
                if (referenceString[i] == lfuArray[j]){
                    pageFault = false;
                    holder[j] = holder[j] + 1; //Mark how many times page has been used
                }
            }
            
            //If there is a page fault insert number into fifoArray
            if (pageFault){
                System.out.println("Page Fault");
                printArray[n+1][i] = "F";
                
                //For first n pages to fill lfuArray
                if (start < n){
                    int index = Arrays.binarySearch(lfuArray, referenceString[i]); //Check if lfuArray already contains value
                    if (!(index >= 0)){
                        lfuArray[start] = referenceString[i];
                        start++;
                    }
                }
                //After first n sections
                else{
                    
                    //Holder is already set, find lfu
                    for (int e = 0; e < holder.length; e++){
                        if (holder[e] < min){
                            min = holder[e];
                            lfu = e;
                        }
                    }
                    //Replace lfu in lruArray with reference string to clear page fault
                    lfuArray[lfu] = referenceString[i];
                    holder[lfu] = 0;
                }
                
                //Reset for next page
                min = 100;
                for (int f = 0; f < holder.length; f++){
                    holder[f] = 0;
                }
                
                //Print results
                System.out.println("New LFU Array: " + Arrays.toString(lfuArray));
            }
            else{
                System.out.println("No Page Fault");
                printArray[n+1][i] = " ";
            }
            
            //Put results in printArray
            for (int p = 0; p < lfuArray.length; p++){
                printArray[p+1][i] = String.valueOf(lfuArray[p]);
            }
            printArray[0][i] = String.valueOf(referenceString[i]);
            
            //Reset for next integer in reference string
            pageFault = true;
        }
        //Print final results
        Print(printArray);
                
    }
    
    static void Print(String[][] printArray){
        System.out.print("\n");
        System.out.println("ref");
        for (int i = 0; i < rsl; i++){
            for (int j = 0; j < (n + 2); j++){
                if (j == 0 || j == 1)
                    System.out.print(" ");
                System.out.print(String.valueOf(printArray[j][i]) + "  ");
            }
            System.out.print("\n");
        }
    }
    
}
