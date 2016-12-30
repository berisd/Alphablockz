/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.gui;

import java.awt.Graphics2D;

public interface Drawable {
    void draw(Graphics2D context);

    boolean isVisible();
}
