import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import static java.lang.Math.min;
import static java.lang.Math.max;
public class Q1 {

    public static void main(String[] args) throws IOException {

        try {

            Scanner sc = (new Scanner(new File("./Assignment1-GeneFeature/vertebrates.txt")));
            String write_fn = "./pos.fasta";
            FileWriter writer = createFile(write_fn);
            
            String seq = "", sub_seq = "", line = "";
            int index = 0, idx = 0, id = -1, new_id = -1;
            Boolean seq_complete = false, found_index = false;
            
            while (sc.hasNextLine()) {

                line = sc.nextLine().trim();

                try {
                    sub_seq = line.split(" ", 2)[0];
                    new_id = Integer.parseInt(sub_seq);
                    if (seq != "") {
                        seq = "N".repeat(max(0, 99-index)) + seq.substring(max(0, index-99), min(index+102, seq.length())) + "N".repeat(max(0, 102-(seq.length()-index)));
                        writer.write(String.format("> %d +1_Index(%d)\n%s\n", id, index, seq));
                        seq = "";
                        index = 0;
                        seq_complete = false;
                        found_index = false;
                    }
                    id = new_id;

                } catch(NumberFormatException e) {
                    if (sub_seq.charAt(0) == '.' || sub_seq.charAt(0) == 'M') {
                        seq_complete = true;
                    }
                    if (!seq_complete) {
                        seq = seq + sub_seq;
                    } else if (!found_index){
                        idx = sub_seq.indexOf("i");
                        if (idx == -1) {
                            index += sub_seq.length();
                        } else {
                            index += idx;
                            found_index = true;
                        }
                        
                    }
                }

            }

            seq = "N".repeat(max(0, 99-index)) + seq.substring(max(0, index-99), min(index+102, seq.length())) + "N".repeat(max(0, 102-(seq.length()-index)));
            writer.write(String.format("> %d +1_Index(%d)\n%s\n", id, index, seq));
                        
            writer.close();

        } catch(FileNotFoundException e) {
            System.out.println(e);
        }

    }

    public static FileWriter createFile(String fn) {

        File write_file = new File(fn);

        try {
            if (write_file.createNewFile()) {
                System.out.println("File created: " + write_file.getName());
            } else {
                System.out.println("File already exists.");
            }
            return new FileWriter(fn);

        } catch (IOException e) {
            e.printStackTrace();
            FileWriter fw = null; 
            return fw;
        }

    }

}