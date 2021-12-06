import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class Project {
    static int cont;// the variable that never changes, to make the loop never end without the "break"

    static float taxMethod(float inp) {
        return (float) (inp * 1.13);
    }

    static JFrame jFrame;

    static JTable jTable;


    public static void main(String[] args) throws IOException {
        byte arrayIndex = 0;
        int textFileLength = 5;
        Store[] storeStorage;
        File myFile = new File(("FileName.txt"));
        if (myFile.createNewFile()) {
            System.out.println("File Created");
        }
        Scanner myReader = new Scanner(myFile);
        Scanner lineCounter = new Scanner(myFile);
        Scanner input = new Scanner(System.in);
        boolean fileEmpty = !myReader.hasNextLine();

        for (int x = 0; lineCounter.hasNextLine(); x++) {
            lineCounter.nextLine();
            textFileLength = x;

        }
        if (textFileLength > 0) {
            arrayIndex = 5;
        }
        storeStorage = new Store[textFileLength + 1];// for some reason the +1 needs to be here, the random +1s in different places fix several errors somehow

        try {
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
            if (!(fileEmpty)) {
                System.out.println("A. Add New Items\nB. Display Items\nC. Exit");
                String selection = input.nextLine();
                if (selection.equalsIgnoreCase("a")) {

                } else if (selection.equalsIgnoreCase("b")) {
                    JTable(textFileLength, storeStorage);
                    break;
                } else {
                    break;
                }
            } else {

                System.out.println("Welcome to the Program! Enter your five items, item #" + (arrayIndex + 1));

            }


            System.out.println("Enter Name of Item: ");
            itemName = input.nextLine();
            System.out.println("Enter Price (tax will be automatically added): ");
            itemPrice = taxMethod(Float.parseFloat(input.nextLine()));
            System.out.println("Enter quantity of item: ");
            itemQuantity = Integer.parseInt(input.nextLine());

            try {
                storeStorage[arrayIndex] = new Store(itemPrice, itemName, itemQuantity, arrayIndex);
            } catch (Exception e) {
                Store[] copiedStoreStorage;
                copiedStoreStorage = new Store[arrayIndex + 1];
                System.arraycopy(storeStorage, 0, copiedStoreStorage, 0, arrayIndex - 1);

                storeStorage = new Store[textFileLength + 1];
                System.arraycopy(copiedStoreStorage, 0, storeStorage, 0, storeStorage.length);
            }
            arrayIndex++;


        }
        while (cont == 0);//the conditions of the loop ensure that it will never end unless the break condition(s) is met

        FileWriter myWriter = new FileWriter("FileName.txt");
        System.out.println(arrayIndex);
        for (byte i = 0; i <= arrayIndex; i++) {
                System.out.println(i);
                myWriter.write(storeStorage[i].Price + "NUM" + storeStorage[i].Name + "NUM" + storeStorage[i].Quantity + "NUM" + storeStorage[i].Index + "\n");
                System.out.println(i);

        }

        myWriter.close();
        myReader.close();
    }

    static void JTable(int numOfRows, Store[] storeStorage) {

        jFrame = new JFrame();


        jFrame.setTitle("Store");
        System.out.println(numOfRows);
        String[][] data = new String[numOfRows + 1][3];
        for (int count1 = 0; count1 <= numOfRows; count1++) {

            data[count1][0] = storeStorage[count1].Name;
            data[count1][1] = String.valueOf(storeStorage[count1].Price);
            data[count1][2] = String.valueOf(storeStorage[count1].Quantity);

        }

        //String[][] data = {{ "Kinda Kumar Jha", "4031", "ooo" }, { "Anand Jha", "6014", "IT" }, {"one","two","Three"}};


        String[] columnNames = {"Name", "Price", "Quantity"};


        jTable = new JTable(data, columnNames);
        jTable.setBounds(30, 40, 200, 300);


        JScrollPane sp = new JScrollPane(jTable);
        jFrame.add(sp);

        jFrame.setSize(500, 200);

        jFrame.setVisible(true);
    }


}


class Store {
    public int Quantity;
    public float Price;
    public String Name;
    public byte Index;


    Store(float price, String name, int quantity, byte index) {
        this.Price = price;
        this.Name = name;
        this.Quantity = quantity;
        this.Index = index;
    }


}
