package data.app.Messages;

import data.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

import static data.app.Messages.Messages.openChat;

public class open_chat {
    static String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    static String user = "postgres";
    static String password = "postgres";
    static Connection conn;
    @FXML
    private Label user_name;
    @FXML
    private TextField chat_tf;

    static Message opened_chat;
    @FXML
    ListView<Chat> chats_listview;
    ObservableList<Chat> chat_lists = FXCollections.observableArrayList();
    static  Message mu;
    @FXML
    Label avatar_label,display_name,display_msg;
    void setMessage(Message m) throws Exception {
//        if(m!=null){
//            System.out.println(m);
        try {
            opened_chat = m;
            user_name.setText(m.name);
            chat_lists = getChats(m);
            chats_listview.setItems(chat_lists);
            chats_listview.scrollTo(chat_lists.size() - 1);
            chats_listview.setCellFactory(list -> new ChatCell());
            avatar_label.setText(""+m.name.charAt(0));
        } catch (Exception e) {

        }
//        }
    }
    @FXML
    public void initialize() throws Exception {

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
//            System.out.println("Loaded contacts: " + contacts_from_db.size());
        } catch (Exception e) {

            throw new RuntimeException(e);
        }


    }
    private ObservableList<Chat> getChats(Message m) throws Exception {


        ObservableList<Chat> clist =
                FXCollections.observableArrayList();
        Statement st = conn.createStatement();
        mu = m;
//        System.out.println(m);
        st.execute("SET client_encoding = 'UTF8'");
        PreparedStatement cs = conn.prepareStatement("SELECT * FROM chat_messages where from_name = ? OR to_name = ? ORDER BY sent_at desc");

        cs.setString(1, m.name);
        cs.setString(2, m.name);

        ResultSet rs = cs.executeQuery();
//todo my name =  set only my chat for my name
        while (rs.next()) {
            boolean is_my_msg =rs.getString("from_name").equals(Main.my_name) ;
            Chat messageee=new Chat(rs.getString("from_name"), rs.getString("to_name"),rs.getString("message"), is_my_msg,rs.getTimestamp("sent_at").toLocalDateTime());
//            System.out.println(messageee);
            clist.add(messageee);
        }
//        contacts_list = new ListView<>( contacts_from_db);
        System.out.println("before"+clist);
        clist.sort(Comparator.comparing(chat -> chat.sent_at));
        System.out.println("after"+clist);

//        System.out.println(contacts_from_db.getFirst().name);
        rs.close();
        st.close();
//        System.out.println(clist);
//        FXCollections.reverse(clist);
        return clist;

    }
    @FXML
    void send_text() throws Exception {
        if(!chat_tf.getText().isEmpty() || chat_tf.getText()!=null){
            PreparedStatement cs = conn.prepareStatement("insert into  chat_messages (from_name , to_name , message) values(?,?,?)");
            cs.setString(1,"Bhavy Patel");
            cs.setString(2,mu.name );
            cs.setString(3,chat_tf.getText());
            cs.executeUpdate();
            openChat(mu);
        }
        else return;
    }
}


class Chat{
    String from_name , to_name , msg;
    boolean is_sent;

    public Chat(String from_name, String to_name, String msg, boolean is_sent, LocalDateTime sent_at) {
        this.from_name = from_name;
        this.to_name = to_name;
        this.msg = msg;
        this.is_sent = is_sent;
        this.sent_at = sent_at;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "from_name='" + from_name + '\'' +
                ", to_name='" + to_name + '\'' +
                ", msg='" + msg + '\'' +
                ", is_sent=" + is_sent +
                ", sent_at=" + sent_at +
                '}';
    }

    LocalDateTime sent_at;
}
