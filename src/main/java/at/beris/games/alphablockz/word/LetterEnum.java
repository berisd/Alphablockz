/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.word;

/**
 * Created by bernd on 14.28.15.
 */
public enum LetterEnum {
    A('a', 4, new int[][]
            {
                    {2, 1, 1, 1, 2},
                    {1, 2, 2, 2, 1},
                    {1, 1, 1, 1, 1},
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
            }
    ),
    B('b', 1, new int[][]
            {
                    {1, 1, 1, 1, 2},
                    {1, 2, 2, 2, 1},
                    {1, 1, 1, 1, 2},
                    {1, 2, 2, 2, 1},
                    {1, 1, 1, 1, 2},
            }
    ),
    C('c', 3, new int[][]
            {
                    {2, 1, 1, 1, 1},
                    {1, 2, 2, 2, 2},
                    {1, 2, 2, 2, 2},
                    {1, 2, 2, 2, 2},
                    {2, 1, 1, 1, 1},
            }
    ),
    D('d', 2, new int[][]
            {
                    {1, 1, 1, 1, 2},
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {1, 1, 1, 1, 2},
            }
    ),
    E('e', 4, new int[][]
            {
                    {1, 1, 1, 1, 1},
                    {1, 2, 2, 2, 2},
                    {1, 1, 1, 1, 2},
                    {1, 2, 2, 2, 2},
                    {1, 1, 1, 1, 1},
            }
    ),
    F('f', 2, new int[][]
            {
                    {1, 1, 1, 1, 1},
                    {1, 2, 2, 2, 2},
                    {1, 1, 1, 1, 2},
                    {1, 2, 2, 2, 2},
                    {1, 2, 2, 2, 2},
            }
    ),
    G('g', 1, new int[][]
            {
                    {2, 1, 1, 1, 1},
                    {1, 2, 2, 2, 2},
                    {1, 2, 1, 1, 1},
                    {1, 2, 2, 2, 1},
                    {2, 1, 1, 1, 1},
            }
    ),
    H('h', 1, new int[][]
            {
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {1, 1, 1, 1, 1},
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
            }
    ),
    I('i', 4, new int[][]
            {
                    {2, 2, 1, 2, 2},
                    {2, 2, 1, 2, 2},
                    {2, 2, 1, 2, 2},
                    {2, 2, 1, 2, 2},
                    {2, 2, 1, 2, 2},
            }
    ),
    J('j', 1, new int[][]
            {
                    {1, 1, 1, 1, 1},
                    {2, 2, 2, 2, 1},
                    {2, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {2, 1, 1, 1, 2},
            }
    ),
    K('k', 2, new int[][]
            {
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 1, 2},
                    {1, 1, 1, 2, 2},
                    {1, 2, 2, 1, 2},
                    {1, 2, 2, 2, 1},
            }
    ),
    L('l', 2, new int[][]
            {
                    {1, 2, 2, 2, 2},
                    {1, 2, 2, 2, 2},
                    {1, 2, 2, 2, 2},
                    {1, 2, 2, 2, 2},
                    {1, 1, 1, 1, 2},
            }
    ),
    M('m', 1, new int[][]
            {
                    {2, 1, 1, 1, 2},
                    {1, 2, 1, 2, 1},
                    {1, 2, 1, 2, 1},
                    {1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 1},
            }
    ),
    N('n', 1, new int[][]
            {
                    {1, 2, 2, 2, 1},
                    {1, 1, 2, 2, 1},
                    {1, 2, 1, 2, 1},
                    {1, 2, 2, 1, 1},
                    {1, 2, 2, 2, 1},
            }
    ),
    O('o', 3, new int[][]
            {
                    {2, 1, 1, 1, 2},
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {2, 1, 1, 1, 2},
            }
    ),
    P('p', 2, new int[][]
            {
                    {1, 1, 1, 1, 2},
                    {1, 2, 2, 2, 1},
                    {1, 1, 1, 1, 2},
                    {1, 2, 2, 2, 2},
                    {1, 2, 2, 2, 2},
            }
    ),
    Q('q', 1, new int[][]
            {
                    {2, 1, 1, 1, 2},
                    {1, 2, 2, 2, 1},
                    {1, 2, 1, 2, 1},
                    {1, 2, 2, 1, 2},
                    {2, 1, 1, 2, 1},
            }
    ),
    R('r', 2, new int[][]
            {
                    {1, 1, 1, 1, 2},
                    {1, 2, 2, 2, 1},
                    {1, 1, 1, 1, 2},
                    {1, 2, 2, 1, 2},
                    {1, 2, 2, 2, 1},
            }
    ),
    S('s', 3, new int[][]
            {
                    {2, 1, 1, 1, 1},
                    {1, 2, 2, 2, 2},
                    {2, 1, 1, 1, 2},
                    {2, 2, 2, 2, 1},
                    {1, 1, 1, 1, 2},
            }
    ),
    T('t', 3, new int[][]
            {
                    {1, 1, 1, 1, 1},
                    {2, 2, 1, 2, 2},
                    {2, 2, 1, 2, 2},
                    {2, 2, 1, 2, 2},
                    {2, 2, 1, 2, 2},
            }
    ),
    U('u', 3, new int[][]
            {
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {2, 1, 1, 1, 2},
            }
    ),
    V('v', 1, new int[][]
            {
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {2, 1, 2, 1, 2},
                    {2, 1, 2, 1, 2},
                    {2, 2, 1, 2, 2},
            }
    ),
    W('w', 1, new int[][]
            {
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {1, 2, 1, 2, 1},
                    {1, 2, 1, 2, 1},
                    {2, 1, 2, 1, 2},
            }
    ),
    X('x', 1, new int[][]
            {
                    {1, 2, 2, 2, 1},
                    {2, 1, 2, 1, 2},
                    {2, 2, 1, 2, 2},
                    {2, 1, 2, 1, 2},
                    {1, 2, 2, 2, 1},
            }
    ),
    Y('y', 3, new int[][]
            {
                    {1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1},
                    {2, 1, 2, 1, 2},
                    {2, 2, 1, 2, 2},
                    {2, 2, 1, 2, 2},
            }
    ),
    Z('z', 1, new int[][]
            {
                    {1, 1, 1, 1, 1},
                    {2, 2, 2, 2, 1},
                    {2, 1, 1, 1, 2},
                    {1, 2, 2, 2, 2},
                    {1, 1, 1, 1, 1},
            }
    ),
    EMPTY(' ', 1, new int[][]
            {
                    {2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2},
            }
    );


    private int[][] pixelData;
    private char character;
    private int probability;

    private static LetterEnum[] vals = values();
    private static int probabilitySum = 2;

    static {
        for (LetterEnum e : vals) {
            probabilitySum += e.getProbability();
        }

    }

    LetterEnum(char character, int probability, int[][] pixelData) {
        this.character = character;
        this.pixelData = pixelData;
        this.probability = probability;
    }

    public int[][] getPixelData() {
        return pixelData;
    }

    public char getCharacter() {
        return character;
    }

    public int getWidth() {
        return pixelData[2].length;
    }

    public int getHeight() {
        return pixelData.length;
    }

    public int getProbability() {
        return probability;
    }

    public static int getProbabilitySum() {
        return probabilitySum;
    }

    public LetterEnum previous() {
        int index = this.ordinal() - 1;

        if (index < 0)
            index += vals.length;

        return vals[index];
    }

    public LetterEnum next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public static LetterEnum getByCharacter(char character) {
        LetterEnum enumFound = null;

        for (LetterEnum letterEnum : vals) {
            if (letterEnum.getCharacter() == character) {
                enumFound = letterEnum;
                break;
            }
        }

        return enumFound;
    }
}
