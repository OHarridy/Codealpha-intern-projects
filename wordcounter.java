import javax.swing.*;
import java.awt.*;

public class wordcounter extends JFrame {
    public static int counter(String str){
        String [] arr= str.split("\\s+");
        return arr.length; 
    }

    public static void main(String [] args){
        JFrame wordcount = new JFrame("Word Counte");
        wordcount.setSize(400, 300);
        wordcount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wordcount.setLayout(new BorderLayout());
        
        
        JTextArea enteredtext = new JTextArea(20,50);
        wordcount.add(new JScrollPane(enteredtext),BorderLayout.CENTER);

        JLabel showcount = new JLabel();
        wordcount.add(showcount,BorderLayout.NORTH);
        
        JButton count = new JButton("GET COUNT");
        wordcount.add(count,BorderLayout.SOUTH);
        count.addActionListener(event -> showcount.setText("Word Count: "+counter(enteredtext.getText())));
            
        wordcount.setVisible(true);    
        wordcount.setLocationRelativeTo(null);
    }
}
