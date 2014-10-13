import tester.*;

import java.awt.Color;
import java.util.Random;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

// Assignment 3
// Bosma, Justin
// jbosma0
// Abou Nassif Mourad, Hussein
// hassuni9


// abstract class of moving Elephants, the Player, and the Bullet
abstract class Moving {
    Posn location;
    
    // constructor of the Moving abstract class
    Moving(Posn loc) {
        this.location = loc;
    }
    
    /* TEMPLATE
     * Fields:
     *  ... this.location ...     -- Posn
     *  
     *  Methods:
     *  ... this.outOfBounds(int, int) ...    -- boolean
     *  ... this.nearTarget(int, int) ...     -- boolean
     *  
     *  Methods for Fields:
     *  ... this.location.x ...      -- int
     *  ... this.location.y ...      -- int
     */
    
    // check if the player or random Elephants are outside the canvas boundaries
    boolean outOfBounds(int width, int height) {
        return (this.location.x < 0
        || this.location.x > width
        || this.location.y < 0 
        || this.location.y > height);
    }
    
    // check if the player or random Elephants are near the target location
    boolean nearTarget(int x, int y) {
        return (this.location.x > x - 15
                && this.location.x < x + 15
                && this.location.y > y - 15
                && this.location.y < y + 15);
    }
}

// to represent the net class
class Net extends Moving {
    
    // constructor of the Net
    Net(Posn location) {
        super(location);
    }
    
    /* TEMPLATE
     * Fields:
     *  ... this.location ...     -- Posn
     *  
     *  Methods:
     *  ... this.getImage() ...         -- WorldImage
     *  ... this.moveIt(String) ...     -- Bullet
     *  
     *  Methods for Fields:
     *  ... this.location.x ...     -- int
     *  ... this.location.y ...     -- int
     */
    
    // Produce the image of the player at the current location
    WorldImage getImage() {
        return new LineImage(new Posn(this.location.x,
                this.location.y), new Posn(this.location.x, 
                        this.location.y - 10), new Red());
    }
    
    // moving class for Bullet. Ignore the string parameter
    Net moveIt(String ke) {
        return new Net(new Posn(this.location.x, (this.location.y - 20)));
    }
}

// class that represents the player that moves around the canvas
class Player extends Moving {
    
    // constructor of the player
    Player(Posn loc) {
        super(loc);
    }
    
    /* TEMPLATE
     * Fields:
     *  ... this.location ...     -- Posn
     *  
     *  Methods:
     *  ... this.getImage() ...               -- WorldImage
     *  ... this.moveIt(String) ...           -- Player
     *  
     *  Methods for Fields:
     *  ... this.location.x ...      -- int
     *  ... this.location.y ...      -- int
     */
    

    // Produce the image of the player at the current location
    WorldImage getImage() {
        return new OvalImage(this.location, 20, 20, new Black());
    }
    
    // move player left, right, up, or down according to key
    Player moveIt(String ke){
        if (ke.equals("right")){
            return new Player(new Posn(this.location.x + 5, this.location.y));
       }
       else if (ke.equals("left")){
           return new Player(new Posn(this.location.x - 5, this.location.y));
       }
       else if (ke.equals("up")){
           return new Player(new Posn(this.location.x, this.location.y - 5));
       }
       else if (ke.equals("down")){
           return new Player(new Posn(this.location.x, this.location.y + 5));
       }    
       else
           return this;
   }
 }

interface ILoElephants {
    
    // Move all the Elephants in this list
    ILoElephants moveElephants(); 
    
    // Produce the image of this Elephants at its current location
    WorldImage ElephantsImage();
    
    // Did any of the Elephants hit the player?
    boolean bumpElephants(Moving that);
    
    // Return the list without the elephant that got hit. Return full list
    // if no elephant was hit
    ILoElephants removeElephants(Moving shot);
    
}

// to represent the empty Elephants class
class MtLoElephants implements ILoElephants {
    
    // constructor for empty Elephant class
    MtLoElephants() {
        // empty constructor since empty class
    }
    
    /* TEMPLATE
     * Fields:
     *  
     *  Methods:
     *  ... this.moveElephants() ...          -- ILoElephants
     *  ... this.ElephantsImage() ...         -- WorldImage
     *  ... this.bumpElephants(Moving) ...    -- boolean
     *  ... this.removeElephants(Moving) ...  -- ILoElephants
     *  
     *  Methods for Fields:
     *  
     */
    
    // Move all the Elephants in this list
    public ILoElephants moveElephants() {
        return this;    
    }
    
    // return circle since it's empty. Will never get called
    public WorldImage ElephantsImage() {
        return new CircleImage(new Posn(0, 0), 0, new White());
    }
    
    // Are any of these Elephants touching the player?
    public boolean bumpElephants(Moving that) {
        return false;
    }
    
    // return empty list
    public ILoElephants removeElephants(Moving shot) {
        return this;
    }
}

// to represent the Elephants class
class Elephants extends Moving {
    int speed;
    
    // constructor for Elephants class
    Elephants(Posn loc, int speed) {
        super(loc);
        this.speed = speed;
    }
    
    /* TEMPLATE
     * Fields:
     *  ... this.location ...     -- Posn
     *  ... this.speed ...        -- int
     *  
     *  Methods:
     *  ... this.getImage() ...               -- WorldImage
     *  ... this.moveIt(String) ...           -- Moving
     *  
     *  Methods for Fields:
     *  
     */
    
    // return image of Elephants
    WorldImage getImage() {
        return new RectangleImage(this.location, 25, 25, new Blue());
    }
    
    // moving function for Player class and Net class
    Moving moveIt(String ke) {
        return this;
    }
}

// to represent Cons class of Elephants
class ConsLoElephants implements ILoElephants {
    Elephants first;
    ILoElephants rest;
    
    // constructor for the Cons class of Elephants
    ConsLoElephants(Elephants first, ILoElephants rest) {
        this.first = first;
        this.rest = rest;
    }
    
    /* TEMPLATE
     * Fields:
     *  ... this.first ...       -- Elephants
     *  ... this.rest ...        -- ILoElephants
     *  
     *  Methods:
     *  ... this.moveElephants() ...          -- ILoElephants
     *  ... this.randomInt(int) ...           -- int
     *  ... this.ElephantsImage() ...         -- WorldImage
     *  ... this.bumpElephants(Moving) ...    -- boolean
     *  ... this.removeElephants(Moving) ...  -- ILoElephants
     *  
     *  Methods for Fields:
     *  ... this.first.location.x ...             -- int
     *  ... this.first.location.y ...             -- int
     *  ... this.first.speed ...                  -- int
     *  ... this.first.getImage() ...             -- WorldImage
     *  ... this.rest.ElephantsImage() ...        -- WorldImage
     *  ... that.location.x ...                   -- int
     *  ... that.location.y ...                   -- int
     *  ... this.randomInt(int) ...               -- int
     *  ... this.first.nearTarget(int, int) ...   -- boolean
     *  ... this.rest.bumpElephants(Moving) ...   -- boolean
     *  ... this.rest.removeElephants(Moving) ... -- ILoElephants
     *  
     */
    
    // Move all the Elephants in this list randomly
    public ILoElephants moveElephants() {
        return new ConsLoElephants(new Elephants(
                new Posn((this.first.location.x + 
                        this.randomInt(this.first.speed)),
                        this.first.location.y + 
                        this.randomInt(this.first.speed)),
                this.first.speed), this.rest.moveElephants());     
    }
    
    // generate random number from from -n to n
    int randomInt(int n){
        return -n + (new Random().nextInt(2 * n + 1));
    }
    
    // Produce the image of this Elephants at its current location
    public WorldImage ElephantsImage() {
        return new OverlayImages(this.first.getImage(),
                        this.rest.ElephantsImage());
    }
    
    // Are any of these Elephants touching the player?
    public boolean bumpElephants(Moving that) {
        return (this.first.nearTarget(that.location.x, that.location.y)) ||
                (this.rest.bumpElephants(that));
    }
    
    // Return the list without the elephant that got hit. Return full list
    // if no elephant was hit
    public ILoElephants removeElephants(Moving that) {
        if(this.first.nearTarget(that.location.x, that.location.y)) {
            return this.rest.removeElephants(that);
        }
        else {
            return new ConsLoElephants(this.first, 
                    this.rest.removeElephants(that));
        }
    }
}

// to represent GameWorld class
class GameWorld extends World {
    int length = 400;
    int width = 400;
    Player player;
    ILoElephants elephants;
    Net catchingNet;
    
    // Constructor of the GameWorld class
    GameWorld(Player player, ILoElephants elephants, Net c) {
        super();
        this.player = player;
        this.elephants = elephants;
        this.catchingNet = c;
   }
   
   /* TEMPLATE
    * Fields:
    * ... this.length ...        -- int
    * ... this.width ...         -- int
    * ... this.player ...        -- Player
    * ... this.Elephants ...     -- ILoElephants
    * ... this.catchingNet ...   -- Net
    *  
    *  Methods:
    *  ... this.makeImage() ...              -- WorldImage
    *  ... this.textOnScreen() ...           -- WorldImage
    *  ... this.onKeyEvent(String) ...       -- World
    *  ... this.onTick() ...                 -- World
    *  ... this.worldEnds() ...              -- WorldEnd
    *  
    *  Methods for Fields:
    *  ... this.elephants.ElephantsImage() ...           -- WorldImage
    *  ... this.player.getImage() ...                    -- WorldImage
    *  ... this.catchingNet.getImage() ...               -- WorldImage
    *  ... this.player.location.x ...                    -- int
    *  ... this.player.location.y ...                    -- int
    *  ... this.player.moveIt(String) ...                -- Player
    *  ... this.elephants.removeElephants(
    *          this.catchingNet).moveElephants() ...     -- ILoElephants
    *  ... this.catchingNet.moveIt(String) ...           -- Net
    *  
    */
   
   // images of the seats
   public WorldImage s1 = new DiskImage(new Posn(150, 65), 15, new Red());
   public WorldImage s2 = new DiskImage(new Posn(180, 65), 15, new Red());
   public WorldImage s3 = new DiskImage(new Posn(210, 65), 15, new Red());
   public WorldImage s4 = new CircleImage(new Posn(240, 65), 15, new Black());
   public WorldImage s5 = new DiskImage(new Posn(270, 65), 15, new Red());
   public WorldImage seats = new OverlayImages(s1,
           new OverlayImages(s2, 
                   new OverlayImages(s3,new OverlayImages(s4, s5))));
   
   // background image of the bar
   public WorldImage bar = new RectangleImage(new Posn(210,25), 150, 
                  40, new Black());
   
   // to make an image of the world state
   public WorldImage makeImage() {
       return new OverlayImages(this.bar, new OverlayImages(this.seats,
               new OverlayImages(this.elephants.ElephantsImage(),
                       new OverlayImages(this.player.getImage(),
                               new OverlayImages(this.catchingNet.getImage(),
                               this.textOnScreen())))));
                               
   }
   
   // to make an image of the text being written on screen
   WorldImage textOnScreen() {
       return new OverlayImages(new TextImage(new Posn(60, 20), "The Bar", 
               25, 3, Color.red), new TextImage(new Posn(200, 380), 
                       "Find an empty seat...press space to shoot"
               + " the squares!", 14, 3, Color.blue));
   }
   
   // Move the player when the arrow keys are pressed
   // Pressing "x" ends the game
   public World onKeyEvent(String ke) {
       if (ke.equals("x")) {
           return this.endOfWorld("Why did you give up?");
       }
       if (ke.equals(" ")) {
           return new GameWorld(this.player, this.elephants,
                   new Net(new Posn(this.player.location.x,
                           (this.player.location.y + 5))).moveIt(ke));
       }
         else
           return new GameWorld(this.player.moveIt(ke), this.elephants, 
                   this.catchingNet);
   }
   
   // Move the Elephants (random) on tick
   public World onTick() {
       return new GameWorld(this.player, 
               this.elephants.removeElephants(this.catchingNet).moveElephants(),
               this.catchingNet.moveIt("hi"));
   }
   
   // the game ends when player touches one of the pink elephants
   public WorldEnd worldEnds() {
       // If player is outside the canvas, stop the game
       if (this.player.outOfBounds(this.width, this.length)) {
           return 
           new WorldEnd(true,
               new OverlayImages(this.makeImage(),
                   new TextImage(new Posn(200, 200), 
                           "Player left the game.", 35, 3,
                                 Color.black)));
       }
       if((this.player.nearTarget(s4.pinhole.x, s4.pinhole.y))){
           return new WorldEnd(true, new OverlayImages(this.makeImage(),
                   new TextImage(new Posn(200, 200), "SUCCESS!!!",
                                   35, 3, Color.red)));
       }
       if(this.elephants.bumpElephants(this.player)) {
            return new WorldEnd(true,
                    new OverlayImages(this.makeImage(),
                            new TextImage(new Posn(200, 200),
                                            "Someone knocked you out!", 20, 3,
                                            Color.red)));
       }
       else { 
           return new WorldEnd(false, this.makeImage());
       }
   }
}
        
// to test examples
class ExamplesPlayer {
    
    // To represent player
    Player player1 = new Player(new Posn(100, 100));
    Player player2 = new Player(new Posn(-100, 100));
    Player player3 = new Player(new Posn(205, 201));
    Player player4 = new Player(new Posn(240, 64));
    
    // To represent a net
    Net catchingNet = new Net(new Posn(-10, -10));
    Net testNet = new Net(new Posn(200, 200));
    
    // Examples of GameWorld
    GameWorld g1 = new GameWorld(new Player(new Posn(-50, 100)), this.empty,
            this.catchingNet);
    GameWorld g2 = new GameWorld(new Player(new Posn(150, 65)), this.empty,
            this.catchingNet);
    GameWorld g3 = new GameWorld(new Player(new Posn(100, 105)), this.empty,
            this.catchingNet);
    
    // generate random number from from -n to n
    int randomInt(int n) {
        return (new Random().nextInt(n + 1));
    }
    
    // to represent empty
    ILoElephants empty = new MtLoElephants();
    
    // examples of elephants
    Elephants elephant1 = new Elephants(new Posn(240, 65), 5);
    Elephants elephant2 = new Elephants(new Posn(randomInt(400),
            randomInt(400)), 5);
    Elephants elephant3 = new Elephants(new Posn(randomInt(400), 
            randomInt(400)), 5);
    Elephants elephant4 = new Elephants(new Posn(randomInt(400),
            randomInt(400)), 5);
    Elephants elephant5 = new Elephants(new Posn(randomInt(400),
            randomInt(400)), 5);
    Elephants elephant6 = new Elephants(new Posn(randomInt(400),
            randomInt(400)), 5);
    Elephants elephant7 = new Elephants(new Posn(randomInt(400),
            randomInt(400)), 5);
    Elephants elephantTest = new Elephants(new Posn(200, 200), 1);
    
    ILoElephants testListElephant = new ConsLoElephants(this.elephantTest,
            empty);
    
    // List of the above elephants
    ILoElephants listElephants = new ConsLoElephants(this.elephant1,
            new ConsLoElephants(elephant2, new ConsLoElephants(this.elephant3,
                    new ConsLoElephants(this.elephant4, 
                            new ConsLoElephants(this.elephant5, 
                                    new ConsLoElephants(this.elephant6,
                                            new ConsLoElephants(this.elephant7,
                                                    empty)))))));
    
    
    // list for testing in the testMoveElephants test
    ILoElephants l1 = new ConsLoElephants(
            new Elephants(new Posn(199, 199), this.elephantTest.speed), empty);
    ILoElephants l2 = new ConsLoElephants(
            new Elephants(new Posn(199, 200), this.elephantTest.speed), empty);
    ILoElephants l3 = new ConsLoElephants(
            new Elephants(new Posn(199, 201), this.elephantTest.speed), empty);
    ILoElephants l4 = new ConsLoElephants(
            new Elephants(new Posn(200, 200), this.elephantTest.speed), empty);
    ILoElephants l5 = new ConsLoElephants(
            new Elephants(new Posn(201, 199), this.elephantTest.speed), empty);
    ILoElephants l6 = new ConsLoElephants(
            new Elephants(new Posn(201, 200), this.elephantTest.speed), empty);
    ILoElephants l7 = new ConsLoElephants(
            new Elephants(new Posn(201, 201), this.elephantTest.speed), empty);
                                            
    
    // Test for moveIt
    boolean testMoveIt(Tester t) {
        return 
            (t.checkExpect(this.player1.moveIt("up"),
            new Player(new Posn(100, 95)))) &&
            (t.checkExpect(this.player1.moveIt("down"),
            new Player(new Posn(100, 105)))) &&
            (t.checkExpect(this.player1.moveIt("left"),
            new Player(new Posn(95, 100)))) &&
            (t.checkExpect(this.player1.moveIt("right"),
            new Player(new Posn(105, 100))));
    }
    
    // Test for outOfBounds
    boolean testOutOfBounds(Tester t) {
        return (t.checkExpect(this.player1.outOfBounds(400, 400), false)) &&
                (t.checkExpect(this.player2.outOfBounds(400, 400), true));
    }
    
    // Test for nearTarget
    boolean testNearTarget(Tester t) {
        return (t.checkExpect(this.player1.nearTarget(95, 95), true)) &&
                (t.checkExpect(this.player2.nearTarget(95, 95), false));
    }
    
    // Test bumpElephants
    boolean testBumpElephants(Tester t) {
        return (t.checkExpect(listElephants.bumpElephants(this.player1),
                false)) &&
                (t.checkExpect(listElephants.bumpElephants(this.player2),
                        false)) &&
                        (t.checkExpect(empty.bumpElephants(this.player1), 
                                false));
    }
    
    // Test for moveElephants function                                        
    boolean testMoveElephants(Tester t) {
        return t.checkOneOf(this.testListElephant.moveElephants(), 
                l1, l2, l3, l4, l5, l6, l7) && 
                t.checkOneOf(this.empty.moveElephants(), this.empty);
    }
    
    // Test for removeElephants
    boolean testRemoveElephants(Tester t) {
        return t.checkExpect(testListElephant.removeElephants(this.player3),
                empty) && t.checkExpect(empty.removeElephants(this.player3),
                        empty) && 
                        t.checkExpect(
                                testListElephant.removeElephants(this.player2),
                                this.testListElephant);
    }
    
    // Examples of Game Worlds
    GameWorld w1 = new GameWorld(new Player(new Posn(50, 350)), 
            this.listElephants, this.catchingNet);
    GameWorld w2 = new GameWorld(this.player3, this.testListElephant, 
            this.testNet);
    GameWorld w3 = new GameWorld(this.player3, this.empty, 
            this.testNet);
    GameWorld w4 = new GameWorld(this.player4, this.empty, 
            this.testNet);
    GameWorld w5 = new GameWorld(this.player2, this.empty, 
            this.testNet);
    
    
    // Test for onTick function
    boolean testOnTick(Tester t) {
        return t.checkExpect(this.w2.onTick(), 
                new GameWorld(this.player3, this.empty,
                        new Net(new Posn(200, 180))));
    }
    
    // Test for onKeyEvent function
    boolean testOnKey(Tester t) {
        return t.checkExpect(this.w2.onKeyEvent("x"), 
                this.w2.endOfWorld("Why did you give up?")) &&
                t.checkExpect(this.w3.onKeyEvent(" "), 
                        new GameWorld(this.player3, this.empty,
                                new Net(new Posn(205, 186))));
    }
    
    // Test for textOnScreen()
    boolean testTextOnScreen(Tester t) {
        return
            t.checkExpect(g3.textOnScreen(),
                new OverlayImages(new TextImage(new Posn(60, 20), "The Bar", 
                    25, 3, Color.red), new TextImage(new Posn(200, 380), 
                    "Find an empty seat...press space to shoot the squares!", 
                    14, 3, Color.blue)));
    }
    
    // To shorten the code below
    WorldEnd we1 = new WorldEnd(true, new OverlayImages(this.w5.makeImage(),
            new TextImage(new Posn(200, 200), "Player left the game.", 35, 3,
                    Color.black)));
    
    // Test the method worldEnds for the class GameWorld
    boolean testWorldEnds(Tester t) {
        return t.checkExpect(this.w1.worldEnds(), new WorldEnd(false, 
                this.w1.makeImage())) && t.checkExpect(this.w4.worldEnds(),
                        new WorldEnd(true, new OverlayImages(
                                this.w4.makeImage(), new TextImage(
                                        new Posn(200, 200), "SUCCESS!!!", 35, 3,
                                        Color.red)))) &&
                                        t.checkExpect(this.w5.worldEnds(),
                                                this.we1);                              
    }

    // run the first game
    boolean runAnimation = this.w1.bigBang(400, 400, 0.3);
} 




















