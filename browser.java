/*Swing Imports*/
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;



public class PhoenixWeb extends JFrame implements HyperlinkListener {
  //Create instances for...
  private JButton buttonBack = new JButton("<-"), buttonForward = new JButton("->");//...the Back button

  private JTextField locationTextField = new JTextField(55);//...the text field (35 chars)

  private JEditorPane displayEditorPane = new JEditorPane();//...the display panel

  private ArrayList pageList = new ArrayList(); //...and an ArrayList for pageList

  

  public PhoenixWeb() {
    setSize(1920, 1080);//window size to 1920 px by 1080px
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//what to do on close (default)
    JPanel bttnPanel = new JPanel();//Create a JPanel instance with refVar bttnPanel

    buttonBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        backActn();
      }
    });

    buttonBack.setEnabled(false);
    bttnPanel.add(buttonBack);
    buttonForward.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        forwardActn();
      }
    });

    buttonForward.setEnabled(false);
    bttnPanel.add(buttonForward);
    locationTextField.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          actionGo();
        }
      }
    });

    //bttnGo
    bttnPanel.add(locationTextField);
    JButton bttnGo = new JButton("GO");
    bttnGo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionGo();
      }
    });

    //Set Display Panel
    bttnPanel.add(bttnGo);
    displayEditorPane.setContentType("text/html/css");
    displayEditorPane.setEditable(false);
    displayEditorPane.addHyperlinkListener(this);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(bttnPanel, BorderLayout.NORTH);
    getContentPane().add(new JScrollPane(displayEditorPane), BorderLayout.CENTER);
  }//close PhoenixWeb()



  //Navigate back action
  private void backActn() {
    URL currentUrl = displayEditorPane.getPage();
    int pageIndex = pageList.indexOf(currentUrl.toString());
    try {
      showPage(new URL((String) pageList.get(pageIndex - 1)), false);
    } catch (Exception e) {
    }
  }

  //Navigate forward action
  private void forwardActn() {
    URL currentUrl = displayEditorPane.getPage();
    int pageIndex = pageList.indexOf(currentUrl.toString());
    try {
      showPage(new URL((String) pageList.get(pageIndex + 1)), false);
    } catch (Exception e) {
    }
  }

  //Go action
  private void actionGo() {
    URL verifiedUrl = verifyUrl(locationTextField.getText());
    if (verifiedUrl != null) {
      showPage(verifiedUrl, true);
    } else {
      System.out.println("Invalid URL");
    }
  }
