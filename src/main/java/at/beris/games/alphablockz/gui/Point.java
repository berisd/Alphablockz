/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.gui;

public class Point implements Cloneable {
    int x = 0, y = 0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Object clone() {
        Point clone;
        try {

            clone = (Point) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }

        return clone;
    }
}
