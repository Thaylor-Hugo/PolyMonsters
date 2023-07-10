package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import actions.Battle;
import entity.Player;
import entity.monsters.Ghost;
import entity.monsters.Goblin;
import entity.monsters.Golem;
import entity.monsters.Monsters;
import entity.monsters.Rat;
import entity.monsters.Sereia;
import entity.monsters.Zombie;
import menus.Dificuldades;
import menus.Menu;
import menus.MenuAmbiente;
import menus.MenuDificuldade;
import menus.MenuInicial;
import menus.MenuOptions;
import menus.MenuPause;
import menus.MenuPersonagens;

import entity.npcs.Npc;
import entity.objects.Backpack;
import tile.TileManager;
import entity.objects.Object;
import itens.Item;
import itens.ItemTypes;

public class GamePanel extends JPanel implements Runnable {
    
    final int MENU = 0;
    final int PLAYING = 1;

    final int originalTileSize = 16;    //TileSize in bits
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // Map settings:
    public final int maxMapCol = 191;    
    public final int maxMapRow = 91;
    public final int mapWidth = tileSize*maxMapCol;
    public final int mapHeight = tileSize*maxMapRow;

    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this, keyH);
    int currentMenu = 0;
    boolean terror = false;
    private final Menu menu[] = {new MenuInicial(this, keyH), new MenuPersonagens(this, keyH, player), 
    new MenuDificuldade(this, keyH), new MenuAmbiente(this, keyH), new MenuPause(this, keyH)};

    int FPS = 60;

    int gameState = MENU;
    public boolean showInventory = false;

    MenuOptions gameState = MenuOptions.INICIAL;

    public void setGameState(MenuOptions opcao) {
        gameState = opcao;
        if(opcao == MenuOptions.INICIAL){
            currentMenu = 0;
        }else if(opcao == MenuOptions.ESCOLHERJOGADOR){
            currentMenu = 1; 
        }else if(opcao == MenuOptions.ESCOLHERDIFICULDADE){
            currentMenu = 2;
        }else if (opcao == MenuOptions.ESCOLHERAMBIENTE){
            currentMenu = 3;
        }else if(opcao == MenuOptions.PAUSE){
            currentMenu = 4;
        }
    }
    TileManager tileM = new TileManager(this);

    Thread gameThread;
    
    ArrayList<Monsters> monsters = new ArrayList<>();
    ArrayList<Object> objects = new ArrayList<>();
    ArrayList<Item> cereal = new ArrayList<>();
    ArrayList<Item> ginger = new ArrayList<>();
    ArrayList<Item> fruit = new ArrayList<>();
    Map<ItemTypes, ArrayList<Item>> backpackContent = new HashMap<>();
    Battle battle = new Battle(monsters, player, this, keyH);

    private Dificuldades dificuldade;

    ArrayList<Npc> npcs = new ArrayList<>();

    public GamePanel() {
        for (int i = 0; i < 10; i++) {
            cereal.add(new Item(ItemTypes.CEREAL_BAR));
            ginger.add(new Item(ItemTypes.FRUIT));
            fruit.add(new Item(ItemTypes.GINGERBREAD));
        }
        backpackContent.put(ItemTypes.CEREAL_BAR, cereal);
        backpackContent.put(ItemTypes.FRUIT, fruit);
        backpackContent.put(ItemTypes.GINGERBREAD, ginger);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.LIGHT_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        for (int i = 0; i < 60; i++) {
            // Cria 10 monstros de cada tipo, espalhados aleatoriamente
            Random rand = new Random();
            if (i < 10) {
                monsters.add(new Ghost(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize)));
                objects.add(new Backpack(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH, backpackContent));
                npcs.add(new Npc(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH));
            } else if (i < 20) {
                monsters.add(new Goblin(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize)));
                objects.add(new Backpack(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH, backpackContent));
                npcs.add(new Npc(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH));
            } else if (i < 30) {
                monsters.add(new Golem(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize)));
                objects.add(new Backpack(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH, backpackContent));
                npcs.add(new Npc(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH));
            } else if (i < 40) {
                monsters.add(new Rat(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize)));
                objects.add(new Backpack(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH, backpackContent));
                npcs.add(new Npc(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH));
            } else if(i < 50) {
                monsters.add(new Sereia(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize)));
                objects.add(new Backpack(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH, backpackContent));
                npcs.add(new Npc(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH));
            } else if (i < 60) {
                monsters.add(new Zombie(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player));
                objects.add(new Backpack(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH, backpackContent));
                npcs.add(new Npc(this, rand.nextInt(mapWidth - tileSize), rand.nextInt(mapHeight - tileSize), player, keyH));
            }
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // Game loop, runs 60 times per second

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                // What actually happens in the loop in each FPS update 
                if (keyH.inventoryPressed) {
                    showInventory = !showInventory;
                    keyH.inventoryPressed = false;
                }
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        // Update the information on screen, as such player position
        if (gameState != MenuOptions.JOGANDO) {
            menu[currentMenu].tick();
        } else {
            if (keyH.pausePressed) {
                setGameState(MenuOptions.PAUSE);
            } else {
                battle.update();
                if (!battle.inBattle) {
                    player.update();        
                    for (Monsters monster : monsters) monster.update();
                    for (Object object : objects) object.update();
                    for (Npc npc : npcs) npc.update();
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        // Paint components on screen. Its used by repaint() on loop (repaint updated information) 
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        if (gameState != MenuOptions.JOGANDO) {
            menu[currentMenu].render(g);
        } else {
            if (battle.inBattle) battle.draw(g2);
            else {
                tileM.draw(g2); //needs to be called first to no overlay Player
                for (Monsters monster : monsters) monster.draw(g2);
                for (Object object : objects) object.draw(g2);
                for (Npc npc : npcs) npc.draw(g2);
                player.draw(g2);
            }
        }

        g2.dispose();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public TileManager getTileM() {
        return tileM;
    }

    public void setDificult(Dificuldades dificuldade) {
        this.dificuldade = dificuldade;
    }

    public void setAmbiente(boolean b) {
        terror = b;
    }

}
