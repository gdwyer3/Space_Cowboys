package sample;

//import com.sun.xml.internal.xsom.impl.scd.Iterators;
//not sure exactly what this is -- Nooreen

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Region {

    //removed static identifier so values can change
    private String name;
    private String description;
    private String techLevel;
    private Point coordinates;

    //field to track whether region has the winning item
    private boolean winningItem = false;

    //each region has a marketplace, the region tech level determines the quantity of each item
    private Item[] marketplaceArray;

    //used to generate random region coordinates
    private static Random randGen;

    public Region(String regionName, String regDescription,
                  String regTechLevel, Point regCoordinates) {
        name = regionName;
        description = regDescription;
        techLevel = regTechLevel;
        coordinates = regCoordinates;
        randGen = new Random();
        this.marketplaceArray = Item.getItemArray();
    }

    // made regions static, instantiated Points, semicolons so that code will compile -- Nooreen
    private static Region phoenix = new Region("Phoenix", "Lots of food, water, furs, and ores.",
            "Medieval", new Point());
    private static Region hydra = new Region("Hydra", "Lots of board games, guns, and ore.",
            "Renaissance", new Point());
    private static Region siren = new Region("Siren", "Lots of factory machines and "
            + "simple medicines.", "Early Industrial", new Point());
    private static Region cyclops = new Region("Cyclops", "Lots of everything except robots.",
            "Industrial", new Point());
    private static Region medusa = new Region("Medusa", "Pollution and depletion are major "
            + "issues here.", "Post-industrial", new Point());
    private static Region sphinx = new Region("Sphinx", "Rich in water, furs, and food.",
            "Agricultural", new Point());
    private static Region lotus = new Region("Lotus", "Lots of machines and robots.",
            "Hi-tech", new Point());
    private static Region dryad = new Region("Dryad", "Lots of wildlife.",
            "Agricultural", new Point());
    private static Region minotaur = new Region("Minotaur", "Rich in furs and ore.",
            "Pre-agricultural", new Point());
    private static Region unicorn = new Region("Unicorn", "Lots of machines and robots.",
            "Hi-tech", new Point());

    //return name of region
    public String getRegName() {
        return name;
    }

    //return description of region
    public String getRegDescription() {
        return description;
    }

    //return tech level
    public String getTechLevel() {
        return techLevel;
    }

    //return coordinate of region
    public Point getRegCoordinates() {
        return coordinates;
    }

    //randomly generate region locations
    public void setRegCoordinates() {
        //the range of randGen TBD. the pane size for the map is 800 by 600
        coordinates.setLocation(randGen.nextInt(550), randGen.nextInt(350));
    }

    static { // I put this in a static block because it won't complie without it -- Nooreen
        phoenix.setRegCoordinates();
        hydra.setRegCoordinates();
        siren.setRegCoordinates();
        cyclops.setRegCoordinates();
        medusa.setRegCoordinates();
        sphinx.setRegCoordinates();
        lotus.setRegCoordinates();
        dryad.setRegCoordinates();
        minotaur.setRegCoordinates();
        unicorn.setRegCoordinates();
    }

    // changed this to an array of Points instead of using java.lang.reflect.Array; -- Nooreen
    //changed access variable to public from private to access from TravelLogController file
    private static Point[] randRegion = new Point[10];

    static { // I put this in a static block because it won't complie without it -- Nooreen
        randRegion[0] = phoenix.getRegCoordinates();
        randRegion[1] = hydra.getRegCoordinates();
        randRegion[2] = siren.getRegCoordinates();
        randRegion[3] = cyclops.getRegCoordinates();
        randRegion[4] = medusa.getRegCoordinates();
        randRegion[5] = sphinx.getRegCoordinates();
        randRegion[6] = lotus.getRegCoordinates();
        randRegion[7] = dryad.getRegCoordinates();
        randRegion[8] = minotaur.getRegCoordinates();
        randRegion[9] = unicorn.getRegCoordinates();
    }

    public static HashMap<Point, Region> regionHashMap() {
        HashMap<Point, Region> regHash = new HashMap<>(10);
        regHash.put(randRegion[0], phoenix);
        regHash.put(randRegion[1], hydra);
        regHash.put(randRegion[2], siren);
        regHash.put(randRegion[3], cyclops);
        regHash.put(randRegion[4], medusa);
        regHash.put(randRegion[5], sphinx);
        regHash.put(randRegion[6], lotus);
        regHash.put(randRegion[7], dryad);
        regHash.put(randRegion[8], minotaur);
        regHash.put(randRegion[9], unicorn);
        return regHash;
    }

    public static Point[] getRandRegion() {
        return randRegion;
    }

    public static ArrayList<Region> getAllRegion() {

        ArrayList<Region> allRegions = new ArrayList<>();
        allRegions.add(phoenix);
        allRegions.add(hydra);
        allRegions.add(siren);
        allRegions.add(cyclops);
        allRegions.add(medusa);
        allRegions.add(sphinx);
        allRegions.add(lotus);
        allRegions.add(dryad);
        allRegions.add(minotaur);
        allRegions.add(unicorn);

        return allRegions;

    }

    //region tech level determines goods quantity and pricing
    public Item[] setMarketplaceArray(String techLevel) {
        // [food, water, fur, ore, game, gun, medicine, narcotics, robots] w/ default quantity of 0
        // based on tech level, set different quantities for particular goods
        // i.e. most tech levels cannot make robots
        if (techLevel.equals("Pre-agricultural")) {
            System.out.println("pre agricultural");
            marketplaceArray[0].setItemQuantity(randGen.nextInt(3));
            for (int i = 1; i < 3; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(randGen.nextInt(10));
            }
            for (int i = 3; i < 9; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(0);
            }
        } else if (techLevel.equals("Agricultural")) {
            System.out.println("agricultural");
            for (int i = 0; i < 4; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(randGen.nextInt(20));
            }
            for (int i = 4; i < 9; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(0);
            }
        } else if (techLevel.equals("Medieval")) {
            System.out.println("medieval");
            for (int i = 0; i < 3; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(randGen.nextInt(100));
            }
            for (int i = 3; i < 9; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(0);
            }
        } else if (techLevel.equals("Renaissance")) {
            System.out.println("renaissance");
            for (int i = 0; i < 4; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(randGen.nextInt(100));
            }
            for (int i = 4; i < 9; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(0);
            }
        } else if (techLevel.equals("Early Industrial")) {
            System.out.println("early industrial system");
            for (int i = 0; i < 7; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(randGen.nextInt(100));
            }
            for (int i = 7; i < 9; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(0);
            }
        } else if (techLevel.equals("Industrial")) {
            System.out.println("industrial");
            for (int i = 0; i < 8; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(randGen.nextInt(100));
            }
            marketplaceArray[8].setItemQuantity(0);
        } else if (techLevel.equals("Post-industrial")) {
            System.out.println("post industrial");
            for (int i = 4; i < 9; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(randGen.nextInt(100));
            }
            for (int i = 0; i < 4; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(0);
            }
        } else if (techLevel.equals("Hi-tech")) {
            System.out.println("hi tech");
            for (int i = 5; i < 9; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(randGen.nextInt(100));
            }
            for (int i = 0; i < 5; ++i) {
                Item naturalResource = marketplaceArray[i];
                naturalResource.setItemQuantity(0);
            }
        }
        return marketplaceArray;
    }

    //randomly selects region to place winning item in
    //returns a string for debugging purposes
    public static String selectWinningItemRegion() {
        Random randSel = new Random();
        int region = randSel.nextInt(10);

        Region winningReg = regionHashMap().get(randRegion[region]);

        winningReg.winningItem = true;

        return (regionHashMap().get(randRegion[region])).getRegName();
    }

    //returns whether the region contains the winning item or not
    public boolean getWinningItem() {
        return winningItem;
    }
}