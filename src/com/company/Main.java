package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    static ArrayList<String> randomWords = new ArrayList<>();
    static Scanner s = new Scanner(System.in);

    static void populateArrayList() {
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            while (br.ready()) {
                randomWords.add(br.readLine());
            }
        } catch (IOException error) {

        }
    }

    public static void main(String[] args) {
        populateArrayList();
        int userInput;
        while (true) {
            createMenu();

            while (true) {
                try {
                    System.out.print("Please select an action: ");
                    userInput = s.nextInt();
                    if (userInput > 0) {
                        break;
                    }
                } catch (InputMismatchException error) {
                    s.next();
                    System.out.println("Please enter a valid selection.");
                }
            }

            if (userInput == 1) {
                int length;
                while (true) {
                    try {
                        System.out.print("Please enter password length: ");
                        length = s.nextInt();
                        if (length > 0) {
                            break;
                        }
                    } catch (InputMismatchException error) {
                        s.next();
                        System.out.println("Password must be greater that zero.");
                    }
                }

                String answer;
                while (true) {
                    try {
                        System.out.print("Would you like your password to have symbols (Y/N): ");
                        answer = s.next();
                        if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N")) {
                            break;
                        } else {
                            System.out.println("Please select Y or N.");
                        }
                    } catch (Exception error) {
                        s.next();
                        System.out.println(error.getMessage());
                    }
                }
                System.out.println("Password: " + RandomString(length, answer));
            } else if (userInput == 2) { // Passphrase
                int phraseLength;
                while (true) {
                    try {
                        System.out.print("Please provide passphrase length: ");
                        phraseLength = s.nextInt();
                        if (phraseLength > 0) {
                            break;
                        } else {
                            System.out.println("Passphrase must contain at least 1 word.");
                        }
                    } catch (InputMismatchException error) {
                        System.out.println("An error occurred: " + error.getMessage());
                    }
                }

                String phrase = null;
                for (int i = 0; i < phraseLength; i++) {
                    String generated = randomPassphrase(randomWords);
                    if (i == 0) {
                        phrase = generated.substring(0, 1).toUpperCase() + generated.substring(1);
                    } else {
                        phrase = phrase + "-" + generated.substring(0, 1).toUpperCase() + generated.substring(1);
                        ;
                    }
                }
                System.out.println("Passphrase: " + phrase);
            } else {
                exit(0);
            }
            System.out.println("-----------------------");
        }
    }

    static String randomPassphrase(ArrayList<String> words) {
        Random r = new Random();
        return words.get(r.nextInt(words.size()));
    }

    static String RandomString(int len, String symbols) {
        String randomString;
        if (symbols.equalsIgnoreCase("Y")) {
            randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz" + "1234567890" + "!@#$%^&*"; // With symbols
        } else {
            randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz" + "1234567890"; // Without symbols
        }
        StringBuilder SB = new StringBuilder(len); // len = Password Length
        for (int i = 0; i < len; i++) {
            int index = (int) (randomString.length() * Math.random());
            SB.append(randomString.charAt(index));
        }
        return SB.toString();
    }

    static void createMenu() {
        System.out.println("1. Generate Password");
        System.out.println("2. Generate Passphrase");
        System.out.println("3. Exit");
    }
}