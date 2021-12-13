package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day13.Folder;
import com.adventofcode.input.day13.TransparentPageManual;

import java.io.IOException;
import java.util.List;

public class Day13 {

    TransparentPageManual transparentPageManual;

    public static void main(String[] input) throws IOException {
        Day13 day13 = new Day13();
        System.out.println("part1 = " + day13.part1());
    }

    public Day13() throws IOException {
        transparentPageManual = Input.transparentPageManual("/day13");
    }

    int part1() {
        int width = transparentPageManual.dots().get(0).width();
        int height = transparentPageManual.dots().get(0).height();
        char[][] page = initPage(width, height);
        Folder folder = new Folder(page, width, height);
        List<TransparentPageManual.FoldInstruction> foldInstructions = transparentPageManual.foldInstructions();
        int result = 0;
        for (int i = 0; i < foldInstructions.size(); i++) {
            TransparentPageManual.FoldInstruction foldInstruction = foldInstructions.get(i);
            folder.fold(foldInstruction);
            if(i == 0){
                result = folder.countDots();
            }
        }
        //part2
        folder.print();
        return result;
    }

    private char[][] initPage(int width, int height) {
        char[][] page = new char[height][width];
        transparentPageManual.dots().forEach(
                dot -> dot.setValue(page, '.')
        );
        return page;
    }
}

