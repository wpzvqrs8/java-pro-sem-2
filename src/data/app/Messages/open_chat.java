package data.app.Messages;

import data.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class open_chat {
    static String url = "jdbc:postgresql://localhost:5432/java_2_pro";
    static String user = "postgres";
    static String password = "postgres";
    static Connection conn;
    @FXML
    private Label user_name;
    static Message opened_chat;
    @FXML
    ListView<Chat> chats_listview;
    ObservableList<Chat> chat_lists = FXCollections.observableArrayList();

    @FXML
    Label avatar_label,display_name,display_msg;
    void setMessage(Message m) throws Exception {
        if(m!=null){
            System.out.println(m);
            opened_chat = m;
            user_name.setText(m.name);
            chats_listview.setItems(getChats(m));
            chats_listview.scrollTo(chat_lists.size() - 1);
            chats_listview.setCellFactory(list -> new ChatCell());
            avatar_label.setText(""+m.name.charAt(0));
        }
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
        System.out.println(m);
        st.execute("SET client_encoding = 'UTF8'");
        PreparedStatement cs = conn.prepareStatement("SELECT * FROM chat_messages where from_name = ? or to_name =? ");

        cs.setString(1, m.name);
        cs.setString(2, m.name);

        ResultSet rs = cs.executeQuery();

        while (rs.next()) {
            boolean is_my_msg =rs.getString("from_name").equals(m.name);
            Chat messageee=new Chat(rs.getString("from_name"), rs.getString("to_name"),rs.getString("message"), is_my_msg,rs.getTimestamp("sent_at").toLocalDateTime());
            System.out.println(messageee);
            clist.add(messageee);
        }
//        contacts_list = new ListView<>( contacts_from_db);

//        System.out.println(contacts_from_db.getFirst().name);
        rs.close();
        st.close();
        System.out.println(clist);
        FXCollections.reverse(clist);
        return clist;

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
