package com.example.marketsimui;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class UserInterface extends Thread{
    World world;

    public UserInterface(World w) {
        super();
        world = w;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Simulation started. Type \"p\" to pause.");
        String message = null;
        while(!Thread.interrupted() && !Objects.equals(message, "q")) {
            Scanner in = new Scanner(System.in);
            do {
                message = in.nextLine();
            } while (!Objects.equals(message, "p"));

            world.pause();
            openDialogMenu();


        }
    }
    private void openDialogMenu() {
        String message;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("""
                    Simulation stopped. Type:
                     - "r" to resume
                     - "q" to quit
                     - "m" to open menu""");
            message = in.nextLine();

        } while (!Arrays.asList("r", "q", "m").contains(message));

        switch (message) {
            case "q" -> {
                System.out.println("end");
                world.finish();
                this.stop();

            }
            case "r" -> {
                world.resume_();
                System.out.println("resumed");
            }
            case "m" -> openFeatureMenu();
        }
    }

    private void openFeatureMenu() {
        Scanner in = new Scanner(System.in);
        String message;

        do {
            System.out.println(
                    """
                            1 - change parameters
                            2 - manage markets
                            3 - manage investors
                            4 - manage companies
                            5 - back""");;
            message = in.nextLine();

        } while (!Arrays.asList("1","2","3","4", "5").contains(message));
        int messageInt = Integer.parseInt(message);

        switch (messageInt) {
            case 1 -> {
                changeParameters();
            }
            case 2 -> {
                manageMarkets();
            }
            case 3 -> {
                manageInvestors();
            }
            case 4 -> {
                manageCompanies();
            }
            case 5 -> {

            }
        }
        openDialogMenu();
    }
    private void changeParameters(){
        System.out.println("Feature in development");

    }
    private void manageMarkets(){
        System.out.println("Feature in development");

    }
    private void manageCompanies(){
        System.out.println("Feature in development");

    }
    private void manageInvestors(){
        System.out.println("Feature in development");

    }

}

