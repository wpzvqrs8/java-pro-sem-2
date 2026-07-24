package data.app.Tic_Tac_Toe;

import data.Main;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;

public class Tic_Tac_Toe extends Application {
    static char[][] board = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
    static char player = 'X';
    static int r;
    static int c;
    static int a[] = {-1, -1};
    static int uid;
    static boolean move_done = false;
    static boolean game_over = false;
    static int move_no = 0;
    static boolean is_computer = false;
    static int[] bot_move = {-1,-1};
    static boolean is_bot_move = false;
    @FXML
    private Label statusLabel;
    @FXML
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
    Parent root;

    @Override
    public void start(Stage stage) throws Exception {

        this.root = FXMLLoader.load(
                getClass().getResource("loading_screen.fxml"));
//        Main.prev_screen_stack.push(root);

        Scene scene = new Scene(this.root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void initialize(){
        System.out.println(btn0);
        if(btn0!=null){
            Button b[] = {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8};

            for (int i = 0; i < b.length; i++) {
                b[i].setStyle("-fx-border-color: transparent; -fx-background-color: #f0e3c0;");
            }
        }
    }

    static void main() {
        launch();
    }
    @FXML
    void play_with_computer(ActionEvent event) {
        try {
            is_computer = true;
            reset_game();
//            data.Main.prev = (AnchorPane) data.Main.MAIN_SCENE.getChildren().get(0);
            Parent root = FXMLLoader.load(
                    getClass().getResource("main_frame_player_computer.fxml"));
//            Stage stage = (Stage) ((Node) event.getSource())
//                    .getScene()
//                    .getWindow();
//
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
            root.setLayoutY(25);
            data.Main.MAIN_SCENE.getChildren().setAll(root);
            Main.prev_screen_stack.push(root);
            Main.recent_apps_stack.pop();
            Main.recent_apps_stack.push(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void play_with_friend(ActionEvent event) {
        try {
            is_computer = false;
            reset_game();
            Parent root = FXMLLoader.load(
                    getClass().getResource("main_frame_2player.fxml"));
            Main.prev_screen_stack.push(root);
            root.setLayoutY(25);
            data.Main.MAIN_SCENE.getChildren().setAll(root);
            Main.recent_apps_stack.pop();
            Main.recent_apps_stack.push(root);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    static boolean is_button_avalable(int id) {
        if (id == 0) return board[0][0] == 0;
        if (id == 1) return board[0][1] == 0;
        return false;
    }

    boolean check_win(char board[][] ,int player , boolean while_checking) {
        if(while_checking){
            for (int i = 0; i < 3; i++) {
                if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
                else if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
                else if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
                else if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
            }
            return false;
        }
        String btn_css = "-fx-text-fill: #db0707;" +
                "-fx-font-size: 40px;" +
                "-fx-background-color: green;" +
                "-fx-border-color: transparent;";
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                if(i==0){
                    btn0.setStyle(btn_css);
                    btn3.setStyle(btn_css);
                    btn6.setStyle(btn_css);
                }
                else if(i==1){
                    btn1.setStyle(btn_css);
                    btn4.setStyle(btn_css);
                    btn7.setStyle(btn_css);
                }
                else if(i==2){
                    btn2.setStyle(btn_css);
                    btn5.setStyle(btn_css);
                    btn8.setStyle(btn_css);
                }

                return true;
            }
            else if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                if(i==0){
                    btn0.setStyle(btn_css);
                    btn1.setStyle(btn_css);
                    btn2.setStyle(btn_css);
                }
                else if(i==1){
                    btn3.setStyle(btn_css);
                    btn4.setStyle(btn_css);
                    btn5.setStyle(btn_css);
                }
                else if(i==2){
                    btn6.setStyle(btn_css);
                    btn7.setStyle(btn_css);
                    btn8.setStyle(btn_css);
                }
                return true;
            }
            else if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
                btn0.setStyle(btn_css);
                btn4.setStyle(btn_css);
                btn8.setStyle(btn_css);
                return true;
            }
            else if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
                btn2.setStyle(btn_css);
                btn4.setStyle(btn_css);
                btn6.setStyle(btn_css);
                return true;
            }
        }
        return false;

    }

    static void display_cli() {
        System.out.println();
        for (int ii = 0; ii < 3; ii++) {
            for (int jj = 0; jj < 3; jj++) {
                System.out.print(board[ii][jj] + " ");
            }
            if (ii != 2) System.out.println();
        }
        System.out.println();
    }

    static boolean board_full(char board[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') return false;
            }

        }
        return true;
    }

    @FXML void change_cell(ActionEvent event) throws InterruptedException {
        Button b = (Button) event.getSource();
        int index = Integer.parseInt(""+(b.getId().charAt(b.getId().length()-1)));
        int r = index / 3;
        int c = index % 3;
        if (board[r][c] != ' ') {
            return;
        }
        if (is_computer) {
            player = 'X';
            is_bot_move = false;
            b.setText("X");
            b.setStyle("-fx-text-fill: #0324dd; -fx-font-size: 40px; -fx-background-color: #f0e3c0; -fx-border-color: transparent;");
            board[r][c] = 'X';

            move_no++;
//            System.out.println("move no -"+move_no+" (X)");
            if (check_win(board, 'X', false)) {
                statusLabel.setVisible(true);
                statusLabel.setText("X Wins!");
                disableAllButtons();
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> { statusLabel.setVisible(false); reset_game(); });
                pause.play();
                return;
            }
            if (move_no == 9) {  // changed from 8 to 9 for clarity
                statusLabel.setVisible(true);
                statusLabel.setText("Tie !!");
                disableAllButtons();
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> { statusLabel.setVisible(false); reset_game(); });
                pause.play();
                return;
            }

            is_bot_move = true;
            if (best_move()) {
                int i = bot_move[0] * 3 + bot_move[1];
                Button botBtn = switch (i) {
                    case 0 -> btn0; case 1 -> btn1; case 2 -> btn2;
                    case 3 -> btn3; case 4 -> btn4; case 5 -> btn5;
                    case 6 -> btn6; case 7 -> btn7; case 8 -> btn8;
                    default -> null;
                };

                if (botBtn != null) {
                    botBtn.setText("O");
                    botBtn.setStyle("-fx-text-fill: #db0707; -fx-font-size: 40px; -fx-background-color: #f0e3c0; -fx-border-color: transparent;");
                    board[bot_move[0]][bot_move[1]] = 'O';

                    move_no++;
                    System.out.println("move no -"+move_no+" (O)");

                    if (check_win(board, 'O', false)) {
                        statusLabel.setVisible(true);
                        statusLabel.setText("O Wins!");
                        disableAllButtons();
                        PauseTransition pause = new PauseTransition(Duration.seconds(1));
                        pause.setOnFinished(e -> { statusLabel.setVisible(false); reset_game(); });
                        pause.play();
                    }
                }
            }
            is_bot_move = false;
            player = 'X';
        }
        else {
            // 2-Player mode
            b.setText("" + player);
            if (player == 'X') {
                b.setStyle("-fx-text-fill: #0324dd;" + "-fx-font-size: 40px;" + "-fx-background-color: #f0e3c0;" + "-fx-border-color: transparent;");
            } else {
                b.setStyle("-fx-text-fill: #db0707;" + "-fx-font-size: 40px;" + "-fx-background-color: #f0e3c0;" + "-fx-border-color: transparent;");
            }

            switch (b.getId()) {
                case "btn0" -> board[0][0] = player;
                case "btn1" -> board[0][1] = player;
                case "btn2" -> board[0][2] = player;
                case "btn3" -> board[1][0] = player;
                case "btn4" -> board[1][1] = player;
                case "btn5" -> board[1][2] = player;
                case "btn6" -> board[2][0] = player;
                case "btn7" -> board[2][1] = player;
                case "btn8" -> board[2][2] = player;
            }

            if (check_win(board, player, false)) {
                statusLabel.setVisible(true);
                statusLabel.setText(player + " Wins!");
                disableAllButtons();
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> { statusLabel.setVisible(false); reset_game(); });
                pause.play();
            } else if (move_no == 8) {
                statusLabel.setVisible(true);
                statusLabel.setText("Tie !!");
                disableAllButtons();
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> { statusLabel.setVisible(false); reset_game(); });
                pause.play();
            }

            move_no++;
            player = player == 'X' ? 'O' : 'X';
        }

        display_cli();
    }
    void disableAllButtons() {
        btn0.setDisable(true); btn1.setDisable(true); btn2.setDisable(true);
        btn3.setDisable(true); btn4.setDisable(true); btn5.setDisable(true);
        btn6.setDisable(true); btn7.setDisable(true); btn8.setDisable(true);
    }
    void reset_game(){
        try {
            btn1.setDisable(false);
            btn2.setDisable(false);
            btn0.setDisable(false);
            btn3.setDisable(false);
            btn4.setDisable(false);
            btn5.setDisable(false);
            btn6.setDisable(false);
            btn7.setDisable(false);
            btn8.setDisable(false);
            btn0.setText("");
            btn1.setText("");
            btn2.setText("");
            btn3.setText("");
            btn4.setText("");
            btn5.setText("");
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn0.setStyle("-fx-background-color: #f0e3c0;" +"-fx-border-color: transparent;" + "-fx-font-size: 40px;" + "-fx-text-fill: black;");
            btn1.setStyle("-fx-background-color: #f0e3c0;" +"-fx-border-color: transparent;" + "-fx-font-size: 40px;" + "-fx-text-fill: black;");
            btn2.setStyle("-fx-background-color: #f0e3c0;" +"-fx-border-color: transparent;" + "-fx-font-size: 40px;" + "-fx-text-fill: black;");
            btn3.setStyle("-fx-background-color: #f0e3c0;" +"-fx-border-color: transparent;" + "-fx-font-size: 40px;" + "-fx-text-fill: black;");
            btn4.setStyle("-fx-background-color: #f0e3c0;" +"-fx-border-color: transparent;" + "-fx-font-size: 40px;" + "-fx-text-fill: black;");
            btn5.setStyle("-fx-background-color: #f0e3c0;" +"-fx-border-color: transparent;" + "-fx-font-size: 40px;" + "-fx-text-fill: black;");
            btn6.setStyle("-fx-background-color: #f0e3c0;" +"-fx-border-color: transparent;" + "-fx-font-size: 40px;" + "-fx-text-fill: black;");
            btn7.setStyle("-fx-background-color: #f0e3c0;" +"-fx-border-color: transparent;" + "-fx-font-size: 40px;" + "-fx-text-fill: black;");
            btn8.setStyle("-fx-background-color: #f0e3c0;" +"-fx-border-color: transparent;" + "-fx-font-size: 40px;" + "-fx-text-fill: black;");
        } catch (Exception e) {}
        move_no = 0;
        player = 'X';
        is_bot_move = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    static boolean available_square(int row, int col) {
        // TODO : write code
        return false;
    }


    int minimax(char[][] mm_board, int depth, boolean is_max) {
        if (check_win(mm_board,'O',true)) {
            return 1;
        } else if (check_win(mm_board,'X',true)) {
            return -1;
        } else if (board_full(mm_board)) {
            return 0;
        }
        if (is_max) {
            int best_score = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (mm_board[i][j] == ' ') {
                        mm_board[i][j] = 'O';
                        int score = minimax(mm_board, depth + 1, false);
                        mm_board[i][j] = ' ';
                        best_score = Math.max(score, best_score);
                    }
                }
            }
            return best_score;
        } else {
            int best_score = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (mm_board[i][j] == ' ') {
                        mm_board[i][j] = 'X';
                        int score = minimax(mm_board, depth + 1, true);
                        mm_board[i][j] = ' ';
                        best_score = Math.min(score, best_score);
                    }
                }
            }
            return best_score;
        }
    }

    boolean best_move() throws InterruptedException {
        is_bot_move = true;
        float best_score = -1000;
        a[0] =-1;
        a[1]=-1;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (board[k][l] == ' ') {
                    board[k][l] = 'O';
                    float score = minimax(board, 0, false);
                    board[k][l] = ' ';
                    if (score > best_score) {
                        best_score = score;
                        a[0] = k;
                        a[1] = l;
                    }

                }
            }
        }
        if (a[0] != -1 && a[1] != -1) {
//    todo
            board[a[0]][a[1]] = 'O';

            bot_move = a;
            if(move_no==9)return false;

            //           -------------------
            //            TODO :play bot move


            return true;
        }
        return false;

    }



}