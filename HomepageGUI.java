import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class HomepageGUI extends JFrame implements ActionListener {
    private JPanel contentPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel page1;
    private JPanel page2;
    private JPanel page3;
    private JPanel page4;
    private JButton page1Button;
    private JButton page2Button;
    private JButton page3Button;
    private JButton page4Button;
    private JButton backButton;
    private Color backgroundColor = new Color(65,81,128);
    private Color backgroundColor2 = new Color(65,90,180);
    private ArrayList<Icon> imgs = new ArrayList<Icon>();
    private int id;

    

    public HomepageGUI() {

      getJSON();
      
      Icon icon1 = new ImageIcon("imgs/acfinal.png");
      Icon icon2 = new ImageIcon("imgs/apexlegendsfinal.png");
      Icon icon3 = new ImageIcon("imgs/callofdutyfinal.png");
      Icon icon4 = new ImageIcon("imgs/cs2final.png");
      Icon icon5 = new ImageIcon("imgs/destiny2final.png");
      Icon icon6 = new ImageIcon("imgs/doomfinal.png");
      Icon icon7 = new ImageIcon("imgs/eldenfinal.png");
      Icon icon8 = new ImageIcon("imgs/fifa23final.png");
      Icon icon9 = new ImageIcon("imgs/forzahorizon5final.png");
      Icon icon10 = new ImageIcon("imgs/ghostoftsushimafinal.png");
      Icon icon11 = new ImageIcon("imgs/godofwarfinal.png");
      Icon icon12 = new ImageIcon("imgs/gta5final.png");
      Icon icon13 = new ImageIcon("imgs/rocketleaguefinal.png");
      Icon icon14 = new ImageIcon("imgs/seaofthievesfinal.png");
      Icon icon15 = new ImageIcon("imgs/thelegendofzeldafinal.png");
      Icon icon16 = new ImageIcon("imgs/thewitcher3final.png");
      imgs.add(icon1);
      imgs.add(icon2);
      imgs.add(icon3);
      imgs.add(icon4);
      imgs.add(icon5);
      imgs.add(icon6);
      imgs.add(icon7);
      imgs.add(icon8);
      imgs.add(icon9);
      imgs.add(icon10);
      imgs.add(icon11);
      imgs.add(icon12);
      imgs.add(icon13);
      imgs.add(icon14);
      imgs.add(icon15);
      imgs.add(icon16);
      
        setTitle("GameWave Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        contentPanel = new JPanel(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Welcome to GameWave!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        headerPanel.setBackground(backgroundColor2);
        contentPanel.setBackground(backgroundColor);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            dispose();
            LoginGUI login = new LoginGUI();
          }
        });
        headerPanel.add(backButton);

        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Card Layout Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Page 1 Panel
        page1 = new JPanel(new BorderLayout());
        JPanel tablePanel1 = new JPanel(new GridLayout(4, 4));
        tablePanel1.setBackground(backgroundColor);
        for (int i = 0; i < 16; i++) {
            JButton button = new JButton(imgs.get(i));
            button.setBackground(backgroundColor);
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    //logic when button is pressed
                }
            });
            button.setHorizontalAlignment(JLabel.CENTER);
            tablePanel1.add(button);
        }
        page1.add(tablePanel1, BorderLayout.CENTER);
        cardPanel.add(page1, "Page 1");

        // Page 2 Panel
        page2 = new JPanel(new BorderLayout());
        JPanel tablePanel2 = new JPanel(new GridLayout(4, 4));
        tablePanel2.setBackground(backgroundColor);
        for (int i = 0; i < 16; i++) {
            JButton button = new JButton("Game " + (i + 17));
            button.setBackground(backgroundColor);
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    //logic when button is pressed
                }
            });
            tablePanel2.add(button);
        }
        page2.add(tablePanel2, BorderLayout.CENTER);
        cardPanel.add(page2, "Page 2");

        // Page 3 Panel
        page3 = new JPanel(new BorderLayout());
        JPanel tablePanel3 = new JPanel(new GridLayout(4, 4));
        tablePanel3.setBackground(backgroundColor);
        for (int i = 0; i < 16; i++) {
            JButton button = new JButton("Game " + (i + 33));
            button.setBackground(backgroundColor);
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    //logic when button is pressed
                }
            });
            tablePanel3.add(button);
        }
        page3.add(tablePanel3, BorderLayout.CENTER);
        cardPanel.add(page3, "Page 3");

        // Page 4 Panel
        page4 = new JPanel(new BorderLayout());
        JPanel tablePanel4 = new JPanel(new GridLayout(4, 4));
        tablePanel4.setBackground(backgroundColor);
        for (int i = 0; i < 16; i++) {
            JButton button = new JButton("Game " + (i + 49));
            button.setBackground(backgroundColor);
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    //logic when button is pressed
                }
            });
            tablePanel4.add(button);
        }
        page4.add(tablePanel4, BorderLayout.CENTER);
        cardPanel.add(page4, "Page 4");

        contentPanel.add(cardPanel, BorderLayout.CENTER);

        // Navigation Panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        page1Button = new JButton("Featured Games");
        page1Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Page 1");
            }
        });
        navPanel.add(page1Button);

        page2Button = new JButton("Top Games");
        page2Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Page 2");
            }
        });
        navPanel.add(page2Button);

        page3Button = new JButton("Sale");
        page3Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Page 3");
            }
        });
        navPanel.add(page3Button);

        page4Button = new JButton("Recommended");
        page4Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Page 4");
            }
        });
        navPanel.add(page4Button);

        

        contentPanel.add(navPanel, BorderLayout.SOUTH);

        // Show the GUI
        setContentPane(contentPanel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
      
    }

    public void getJSON(){
      try {
            URL url = new URL("http://0.0.0.0:80/init");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
          new InputStreamReader(con.getInputStream()));
        
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        System.out.println(inputLine);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

            
    }

  public void openGamePage(int id){
    
  }
}