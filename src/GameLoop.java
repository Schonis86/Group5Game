import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal;

public class GameLoop {
    Screen screen = TerminalFacade.createScreen();
    Player player = new Player();
    boolean gameOver;
    int playerX, playerY;
    int playerSpeed = player.getPlayerSpeed();
    int minX = 30;
    int maxX = 70;
    int minY = 5;
    int maxY = 25;

    public GameLoop() {
        gameOver = false;
        getPlayerPosition();
    }

    public void run() {
        while(!gameOver) {
            screen.clear();
            gameArea();
            renderPlayer();
            handleKeyPress();
            screen.refresh();
        }
        handleKeyPress();
    }

    private void frameCollisionDetection() {
        getPlayerPosition();

        if (playerX == minX || playerX == maxX || playerY == minY || playerY == maxY) {
            gameOver = true;
            screen.clear();
            gameArea();
            screen.putString(46, 15, "GAME OVER", Terminal.Color.RED, Terminal.Color.BLACK);
            screen.refresh();
            run();
        }
    }

    private void handleKeyPress() {
        Key key = screen.readInput();
        while (key == null) {
            key = screen.readInput();
        }
        switch (key.getKind()) {
            case ArrowUp:
                handlePlayerMovement("up");
                frameCollisionDetection();
                run();
                break;
            case ArrowDown:
                handlePlayerMovement("down");
                frameCollisionDetection();
                run();
                break;
            case ArrowLeft:
                handlePlayerMovement("left");
                frameCollisionDetection();
                run();
                break;
            case ArrowRight:
                handlePlayerMovement("right");
                frameCollisionDetection();
                run();
                break;
            case NormalKey:
                if (key.getCharacter() == 'n'){
                    System.out.println("Reset knappen tryckt");
                    player.reset();
                    gameOver = false;
                    run();
                } else if (key.getCharacter() == 'q') {
                    screen.stopScreen();
                    
                }
                break;
        }
    }

    private void handlePlayerMovement(String direction) {
        switch (direction) {
            case "up":
                player.moveUp();
                break;
            case "down":
                player.moveDown();
                break;
            case "left":
                player.moveLeft();
                break;
            case "right":
                player.moveRight();
                break;
        }
        getPlayerPosition();
        System.out.println("playerX: " + playerX);
        System.out.println("playerY: " + playerY);
    }

    private void getPlayerPosition() {
        playerX = player.getPlayerX();
        playerY = player.getPlayerY();
    }

    private void renderPlayer() {
        getPlayerPosition();
        screen.putString(playerX,playerY,"X", Terminal.Color.MAGENTA, Terminal.Color.BLACK);
        screen.refresh();
    }

    public void gameArea(){

        screen.startScreen();
        screen.setCursorPosition(null);
        screen.putString(45,2,"THE GAME-NAME", Terminal.Color.YELLOW, Terminal.Color.BLACK, ScreenCharacterStyle.Underline);

//      GameArea Left and right

        for (int i =30; i <=70; i+=2){
            screen.putString(i,5,"*", Terminal.Color.GREEN, Terminal.Color.BLACK);
            screen.putString(i,25,"*", Terminal.Color.GREEN, Terminal.Color.BLACK);
        }

//      GameArea over and under

        for (int j = 5; j<=25; j++){
            screen.putString(30,j,"*", Terminal.Color.GREEN, Terminal.Color.BLACK);
            screen.putString(70,j,"*", Terminal.Color.GREEN, Terminal.Color.BLACK);
        }

        screen.putString(33,27," [N] NEW GAME             [Q] QUIT", Terminal.Color.GREEN, Terminal.Color.BLACK);
//Player

//        screen.putString(xPlayer,yPlayer,"X", Terminal.Color.MAGENTA, Terminal.Color.BLACK);
        screen.refresh();

    }
}
