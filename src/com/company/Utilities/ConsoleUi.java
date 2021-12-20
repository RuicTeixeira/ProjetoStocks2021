package com.company.Utilities;


public class ConsoleUi {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    public static void employerMenu() {
        System.out.println(ANSI_RED + "Press 1 - List all products in catalog");
        System.out.println("-----------------------------");
        System.out.println("Press 2 - Add product to catalog");
        System.out.println("-----------------------------");
        System.out.println("Press 3 - Remove product from catalog by id");
        System.out.println("-----------------------------");
        System.out.println("Press 4 - Add quantity to a product in catalog");
        System.out.println("-----------------------------");
        System.out.println("Press 5 - Search products in a price range");
        System.out.println("-----------------------------");
        System.out.println("Press 6 - Finish session");
        System.out.println(ANSI_BLUE+"\nOption: " + ANSI_RESET);

    }

    public static void clientMenu() {

        System.out.println(ANSI_RESET + ANSI_BLUE + "Press 1 - Add products to your cart");
        System.out.println("-----------------------------");
        System.out.println("Press 2 - Remove product from your cart");
        System.out.println("-----------------------------");
        System.out.println("Press 3 - List all products in catalog");
        System.out.println("-----------------------------");
        System.out.println("Press 4 - Search products in a price range");
        System.out.println("-------------------------------");
        System.out.println("Press 5 - List all products in your cart");
        System.out.println("-------------------------------");
        System.out.println("Press 6 - Finish session");
        System.out.println(ANSI_RED + "\nOption: " + ANSI_RESET);

    }

    public static void dividers() {
        System.out.println(ANSI_RESET + ANSI_BLACK + "------------------------------------");
        System.out.println("------------------------------------");
        System.out.println("------------------------------------");
        System.out.println("------------------------------------\n\n" + ANSI_RESET);
    }

    public static void banner(){
        System.out.println(ANSI_BLACK_BACKGROUND +"\n\n##   ##  ### ###  ####      ## ##    ## ##   ##   ##  ### ###           #### ##   ## ##            ##   ##  ##  ##             ## ##   ###  ##   ## ##   ### ##   \n" +
                "##   ##   ##  ##   ##      ##   ##  ##   ##   ## ##    ##  ##           # ## ##  ##   ##            ## ##   ##  ##            ##   ##   ##  ##  ##   ##   ##  ##  \n" +
                "##   ##   ##       ##      ##       ##   ##  # ### #   ##                 ##     ##   ##           # ### #  ##  ##            ####      ##  ##  ##   ##   ##  ##  \n" +
                "## # ##   ## ##    ##      ##       ##   ##  ## # ##   ## ##              ##     ##   ##           ## # ##   ## ##             #####    ## ###  ##   ##   ##  ##  \n" +
                "# ### #   ##       ##      ##       ##   ##  ##   ##   ##                 ##     ##   ##           ##   ##    ##                  ###   ##  ##  ##   ##   ## ##   \n" +
                " ## ##    ##  ##   ##  ##  ##   ##  ##   ##  ##   ##   ##  ##             ##     ##   ##           ##   ##    ##              ##   ##   ##  ##  ##   ##   ##      \n" +
                "##   ##  ### ###  ### ###   ## ##    ## ##   ##   ##  ### ###            ####     ## ##            ##   ##    ##               ## ##   ###  ##   ## ##   ####     \n" +
                "                                                                                                                                                                  \n"+ANSI_RESET);

    }



}
