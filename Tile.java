import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends StackPane{
    int col;
    int row;
    int adjBombs = 0;
    boolean isBomb = false;
    boolean isCleared = false;
    boolean isFlagged = false;

    Rectangle disp;

    Minefield field;

    EventHandler<MouseEvent> revealTileEvent = clickBehavior();
    
    public Tile(int size, int col, int row, Minefield field){
        this.field = field;
        disp = new Rectangle(size,size);
        disp.setOnMouseClicked(revealTileEvent);

        this.getChildren().add(disp);
        
        this.col = col;
        this.row = row;
    }

    void pretty(){
        disp.setFill(Color.LIGHTGRAY);
        disp.setStroke(Color.BLACK);
        disp.setStrokeWidth(1);
    }

    public void prime(){
        isBomb = true;
    }

    EventHandler<MouseEvent> clickBehavior(){
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    if(event != null && event.getButton() == MouseButton.SECONDARY){
                        if(!isFlagged){
                            isFlagged = true;
                            disp.setFill(Color.RED);
                        } else if(isFlagged){
                            isFlagged = false;
                            disp.setFill(Color.LIGHTGRAY);
                        }
                    } else {
                        if(isBomb && event != null){
                            disp.setFill(Color.BLACK);
                            field.gameover();
                        } else if(!isBomb && !isCleared){
                            disp.setFill(Color.WHITE);
                            isCleared = true;
                            field.clearedTiles++;
                            field.winCheck();
                            if(adjBombs > 0)
                                Tile.this.getChildren().add(new Text(""+adjBombs));
                            else   
                                clearAdj();
                        }
                    }

            }
        };
    }

    void clearAdj(){
        Stack<Tile> adj = field.getAdj(this);
        while(!adj.empty()){
            adj.pop().revealTileEvent.handle(null);
        }
    }
}
