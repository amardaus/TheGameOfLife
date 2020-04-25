package sample;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    int width = 500;
    int height = 500;
    int n = 18;         //square count
    int w = width/n;    //width of a single square
    int h = height/n;   //height of a single square
    boolean[][] gameBoard = new boolean[n][n];
    Rectangle[][] rect = new Rectangle[n][n];

    @Override
    public void start(Stage stage){

        VBox leftNav = new VBox();     //left navigation
        Pane rectPane = new Pane();
        Button btnEvolve = new Button("Evolve");
        Button btnReset = new Button("Reset");
        leftNav.getChildren().addAll(btnEvolve, btnReset);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(leftNav);
        borderPane.setCenter(rectPane);
        Scene scene = new Scene(borderPane, width + 100, height);

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                rect[i][j] = new Rectangle(w, h);
                rect[i][j].setX(i*w);
                rect[i][j].setY(j*h);
                rect[i][j].setFill(Color.WHITE);
                rect[i][j].setStroke(Color.BLACK);
                rectPane.getChildren().add(rect[i][j]);
            }
        }
        rectPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double xPos = mouseEvent.getX();
                double yPos = mouseEvent.getY();
                int col = (int)(xPos/w);
                int row = (int)(yPos/h);
                Color color = gameBoard[col][row] ? Color.WHITE : Color.BLACK;
                rect[col][row].setFill(color);
                gameBoard[col][row] = !gameBoard[col][row];
            }
        });

        stage.setTitle("The Game of Life");
        stage.setScene(scene);
        stage.show();

        btnEvolve.setOnAction(e -> {
            evolve();
        });

        btnReset.setOnAction(e -> {
            reset();
        });
    }

    void updateNeighbors(){
        boolean[][] tmp = new boolean[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                int neighbors = 0;
                for(int a = -1; a < 2; a++){
                    for(int b = -1; b < 2; b++){
                        if(i+a >= 0 && i+a < n && j+b >= 0 && j+b < n){
                            if((a != 0 || b != 0) && (gameBoard[i+a][j+b])) neighbors++;
                        }
                    }
                }
                if((!gameBoard[i][j]) && neighbors == 3) tmp[i][j] = true;
                else if(gameBoard[i][j] && (neighbors == 2 || neighbors == 3)) tmp[i][j] = true;
                else tmp[i][j] = false;
            }
        }

        /*for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(tmp[i][j]) System.out.print("1 ");
                else System.out.print("0 ");
            }
            System.out.println();
        }*/

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                gameBoard[i][j] = tmp[i][j];
            }
        }
    }

    void evolve() {
        updateNeighbors();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                rect[i][j].setFill(gameBoard[i][j] ? Color.BLACK : Color.WHITE);
            }
        }
    }

    void reset(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                gameBoard[i][j] = false;
                rect[i][j].setFill(Color.WHITE);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
