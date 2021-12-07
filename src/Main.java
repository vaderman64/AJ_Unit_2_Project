import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

//^imports for everything
class Project {
    static int cont = 0;// the variable that never changes, to make the loop never end without the "break"

    static float taxMethod(float inp) {
        return (float) (inp * 1.13);
    }//tax method

    static JFrame jFrame;//declaring frame (and table) so that they can be used throughout the class

    static JTable jTable;


    public static void main(String[] args) throws IOException {
        byte arrayIndex = 0;//keeps track of what item you are editing (eg: product #3 has an index of 2, because it starts at 0)
        int textFileLength = 1;// the length of the file, starts at one, not zero
        Store[] storeStorage;//main array

        File myFile = new File(("FileName.txt"));//selecting the file
        if (myFile.createNewFile()) {//creates a file if one doesn't exist
            System.out.println("File Created");
        }
        Scanner myReader = new Scanner(myFile);//scanners for file reading, line counting, and input
        Scanner lineCounter = new Scanner(myFile);
        Scanner input = new Scanner(System.in);
        boolean fileEmpty = !myReader.hasNextLine();//is the file empty?

        for (int x = 1; lineCounter.hasNextLine(); x++) {//determines file length
            lineCounter.nextLine();
            textFileLength = x;

        }

        if (textFileLength > 0) {//if it's more than 0, then the arrayIndex starts at the file length
            arrayIndex = (byte) (textFileLength - 1);//-1 to account for textFileLength starting at 1
        }

        storeStorage = new Store[textFileLength];//creates a new store array of textFileLength
        try {//fills the array with the split textFile
            for (int i = 0; myReader.hasNextLine(); i++) {
                String fileContents = myReader.nextLine();
                String[] fileContentsArray = fileContents.split("NUM", 4);
                storeStorage[i] = new Store(Float.parseFloat(fileContentsArray[0]), fileContentsArray[1], Integer.parseInt(fileContentsArray[2]), Byte.parseByte(fileContentsArray[3]));

            }

        } catch (Exception e) {
            System.out.println("File Reading Finished!");
        }

        do {
            String itemName;
            float itemPrice;
            int itemQuantity;

            if (fileEmpty && arrayIndex == 5) {//if the user enters something invalid, exit, also exits once five items have been entered for the first time
                break;
            }
            if (!(fileEmpty)) {//if the file is not empty, act as if the user has already entered items
                System.out.println("A. Add New Items\nB. Display Items\nC. Exit");
                String selection = input.nextLine();
                if (selection.equalsIgnoreCase("a")) {

                } else if (selection.equalsIgnoreCase("b")) {
                    JTable((arrayIndex), storeStorage);//calls the table method and gives it how long it has to be and the content it needs to use
                    continue;
                } else {
                    break;
                }
            } else {

                System.out.println("Welcome to the Program! Enter your five items, item #" + (arrayIndex + 1));//this activates if the file is empty

            }


            System.out.println("Enter Name of Item: ");//getting info
            itemName = input.nextLine();
            System.out.println("Enter Price (tax will be automatically added): ");
            itemPrice = taxMethod(Float.parseFloat(input.nextLine()));
            System.out.println("Enter quantity of item: ");
            itemQuantity = Integer.parseInt(input.nextLine());

            try {
                storeStorage[arrayIndex] = new Store(itemPrice, itemName, itemQuantity, arrayIndex);
            } catch (Exception e) {//try to fill the array, if it errors than that means the array is full and a new one is needed.

                Store[] copiedStoreStorage;
                copiedStoreStorage = new Store[arrayIndex + 1];
                for (int i = 0; i <= (storeStorage.length) - 1; i++) {
                    copiedStoreStorage[i] = storeStorage[i];
                }
                copiedStoreStorage[arrayIndex] = new Store(itemPrice, itemName, itemQuantity, arrayIndex);
                storeStorage = copiedStoreStorage;
                storeStorage[arrayIndex] = copiedStoreStorage[arrayIndex];
            }
            arrayIndex++;


        }
        while (cont == 0);//the conditions of the loop ensure that it will never end unless the break condition(s) is met

        FileWriter myWriter = new FileWriter("FileName.txt");

        for (byte i = 0; i < (arrayIndex); i++) {

            myWriter.write(storeStorage[i].Price + "NUM" + storeStorage[i].Name + "NUM" + storeStorage[i].Quantity + "NUM" + storeStorage[i].Index + "\n");
//writes to the file

        }

        myWriter.close();
        myReader.close();//closes both the reader and writer
    }

    static void JTable(int numOfRows, Store[] storeStorage) {//method for the GUI

        jFrame = new JFrame();


        jFrame.setTitle("Store");
        String[][] data = new String[numOfRows][3];
        for (int count1 = 0; count1 < numOfRows; count1++) {//Filling the data array

            data[count1][0] = storeStorage[count1].Name;
            data[count1][1] = String.valueOf(storeStorage[count1].Price);
            data[count1][2] = String.valueOf(storeStorage[count1].Quantity);

        }


        String[] columnNames = {"Name", "Price", "Quantity"};


        jTable = new JTable(data, columnNames);
        jTable.setBounds(30, 40, 200, 300);


        JScrollPane sp = new JScrollPane(jTable);
        jFrame.add(sp);

        jFrame.setSize(500, 200);

        jFrame.setVisible(true);
    }


}


class Store {//class for the store object
    public int Quantity;
    public float Price;
    public String Name;
    public byte Index;


    Store(float price, String name, int quantity, byte index) {//"filling" the variables
        this.Price = price;
        this.Name = name;
        this.Quantity = quantity;
        this.Index = index;
    }


}
