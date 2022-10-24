/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

/**
 *
 * @author kikas
 */
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class EndGame {
    void launch(long durationGame) {
        List<String> ranking = this.readInFile();
        Scanner scan = new Scanner(System.in);
        if(ranking.size() < 10 || Long.parseLong(ranking.get(9).split("-")[1].trim()) > durationGame) ranking = addInFile(scan.nextLine(), durationGame, ranking); 
        scan.close();
        System.out.println(this.printRanking(this.readInFile()));
    }

    private List<String> addInFile(String pseudo, long durationGame, List<String> ranking) {
        List<String> temp = new ArrayList<String>();
        final int lim = Math.min(9, ranking.size()) + 1;

        if(lim == 1) {
            temp.add(pseudo + " - " + durationGame);
            System.out.println("TEst");
        }
        else {
            boolean scoreAlreadyInList = false;
            for(int i = 0, j = 0; i < lim; i++) {
                if(Long.parseLong(ranking.get(j).split("-")[1].trim()) < durationGame || scoreAlreadyInList) {
                    temp.add(ranking.get(j));
                    j++;
                } else {
                    temp.add(pseudo + " - " + durationGame);
                    scoreAlreadyInList = true;
                }
            }       
        } 

        final int newLen = temp.size();
        File file = new File(System.getProperty("user.dir") + "/result.txt");
        file.delete();
        System.out.println(newLen);
        for(int i = 0; i < newLen; i++) {
            try  (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {  
                writer.append(temp.get(i) + "\n");
                writer.close();
            } catch(Exception e) {
                System.out.println("An error occured !");
            }
        }
        return ranking; 
    }

    private List<String> readInFile() {
        List<String> ranking = new ArrayList<String>();
        try {
            File file = new File(System.getProperty("user.dir") + "/result.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) ranking.add(scan.nextLine());
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found !");
        }
        return ranking;
    }

    public String printNewLine(int maxLengthPseudo, int maxTiming, String pseudo, String timing) {
        String s = "\n║";

        int firstEmpty = (maxLengthPseudo - pseudo.length()) / 2, secondEmpty = ((maxLengthPseudo - pseudo.length()) / 2) + pseudo.length() % 2, 
        thirdEmpty = (maxTiming - timing.length()) / 2, fourthEmpty = ((maxTiming - timing.length()) / 2) + timing.length() % 2;

        for(int i = 0; i < firstEmpty; i++) s += " ";
        s += pseudo;
        for(int i = 0; i < secondEmpty; i++) s += " ";
        s += "║";
        for(int i = 0; i < thirdEmpty; i++) s += " ";
        s += timing;
        for(int i = 0; i < fourthEmpty; i++) s += " ";
        s += "║";

        return s;
    }

    public String printEnd(int maxLengthPseudo, int maxTiming) {
        String s = "╚";
        for(int i = 0; i < maxLengthPseudo; i++) s += "═";
        s += "╩";
        for(int i = 0; i < maxTiming; i++) s += "═";

        return s += "╝";
    }

    public String printHeader(int maxLengthPseudo, int maxTiming) {
        String s = "╔";
        for(int i = 0; i < maxLengthPseudo; i++) s += "═";
        s += "╦";
        for(int i = 0; i < maxTiming; i++) s += "═";

        s += "╗" + this.printNewLine(maxLengthPseudo, maxTiming, "Pseudo", "Timing") + "\n╠";

        for(int i = 0; i < maxLengthPseudo; i++) s += "═";
        s += "╬";
        for(int i = 0; i < maxTiming; i++) s += "═";
        s += "╣";

        return s;
    }

    public String printRanking(List<String> ranking) {
        String print = "";
        final int size = ranking.size();
        int maxLengthPseudo = 6, maxTiming = 6;
        List<String> pseudo = new ArrayList<String>(), timing = new ArrayList<String>(); 

        for(int i = 0; i < size; i++) {
            pseudo.add(ranking.get(i).split("-")[0].trim());
            timing.add(ranking.get(i).split("-")[1].trim());
            if(maxLengthPseudo < pseudo.get(i).length()) maxLengthPseudo = pseudo.get(i).length();
            if(maxTiming < timing.get(i).length()) maxTiming = timing.get(i).length();
        }
        maxLengthPseudo += 2;
        maxTiming += 2;

        print = this.printHeader(maxLengthPseudo, maxTiming);
        for(int i = 0; i < size; i++) print += this.printNewLine(maxLengthPseudo, maxTiming, pseudo.get(i), timing.get(i));

        return print + "\n" + this.printEnd(maxLengthPseudo, maxTiming);
    }
}