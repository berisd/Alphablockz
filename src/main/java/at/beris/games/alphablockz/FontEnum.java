/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public enum FontEnum {
    XSMALL(new Font(null, Font.PLAIN, 11)),
    SMALL(new Font(null, Font.PLAIN, 14)),
    SMALL_BOLD(new Font(null, Font.BOLD, 14)),
    STD(new Font(null, Font.BOLD, 18)),
    LARGE(createLargeFont()),
    LARGE_UNDERLINE(createLargeUnderlineFont()),
    XLARGE(new Font(null, Font.BOLD, 36));
    private Font font;

    FontEnum(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return this.font;
    }


    private static Font createLargeFont() {
        return new Font(null, Font.BOLD, 24);
    }

    private static Font createLargeUnderlineFont() {
        Map<TextAttribute, Integer> textAttributeMap = new HashMap<TextAttribute, Integer>();
        textAttributeMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        return LARGE.getFont().deriveFont(textAttributeMap);
    }
}
