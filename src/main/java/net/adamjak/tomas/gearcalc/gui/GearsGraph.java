/*
 * Copyright (c) 2018, Tomas Adamjak <thomas@adamjak.net>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     - Neither the name of Tomas Adamjak <thomas@adamjak.net>,  nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.adamjak.tomas.gearcalc.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.JComponent;
import net.adamjak.tomas.gearcalc.gears.Gears;
import net.adamjak.tomas.gearcalc.gears.GearsExtremes;

/**
 *
 * @author Tomas Adamjak <thomas@adamjak.net>
 */
public class GearsGraph extends JComponent {

    private final static int PADDING_TOP = 10;
    private final static int PADDING_BOTTOM = 30;
    private final static int PADDING_LEFT = 30;
    private final static int PADDING_RIGHT = 10;

    private final static int VERTICAL_SEGMENT_MIN_WIDTH = 25;

    private final static Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
    private final static Font BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 13);
    private final static Font ITALIC_FONT = new Font(Font.SANS_SERIF, Font.ITALIC, 13);

    private final static Stroke DEFAULT_STROKE_1 = new BasicStroke(1);
    private final static Stroke DEFAULT_STROKE_3 = new BasicStroke(3);
    private final static Stroke DOTTED_STROKE_1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{1, 5}, 0);

    @Override

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(DEFAULT_FONT);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(DEFAULT_STROKE_3);
        if (Gears.getInstance().getAllGears().isEmpty()) {
            return;
        }

        GearsExtremes ge = Gears.getInstance().getGearsExtremes();

        this.drawXByExtremes(g2, ge);
        this.drawYByExtremes(g2, ge);
        this.drawGraph(g2);
    }

    private void drawXByExtremes(Graphics2D g2, GearsExtremes ge) {
        g2.drawLine(PADDING_LEFT, this.getHeight() - PADDING_BOTTOM, this.getWidth() - PADDING_RIGHT, this.getHeight() - PADDING_BOTTOM);
        int rearCount = ge.getRearMax() - ge.getRearMin();
        int segment = this.getHorizontalSegment();
        int quarterSegment = segment / 4;
        int halfSegment = segment / 2;

        int i = 0;
        while (i <= rearCount) {

            g2.setStroke(DOTTED_STROKE_1);
            g2.drawLine(
                    PADDING_LEFT + (i * segment) + quarterSegment,
                    PADDING_TOP,
                    PADDING_LEFT + (i * segment) + quarterSegment,
                    this.getHeight() - PADDING_BOTTOM);

            g2.setStroke(DEFAULT_STROKE_1);
            g2.drawLine(
                    PADDING_LEFT + (i * segment) + quarterSegment,
                    this.getHeight() - PADDING_BOTTOM,
                    PADDING_LEFT + (i * segment) + quarterSegment,
                    this.getHeight() - (PADDING_BOTTOM / 4 * 3));

            int gearToPrint = i + ge.getRearMin();
            if (Gears.getInstance().isInGears(gearToPrint, Gears.GearLocation.REAR)) {
                g2.setFont(BOLD_FONT);
            } else {
                g2.setFont(ITALIC_FONT);
            }
            g2.drawString(String.valueOf(gearToPrint), PADDING_LEFT + (i * segment) + halfSegment, this.getHeight() - (PADDING_BOTTOM / 2));

            i = i + this.getSegmentMultiplication(segment);
        }

        g2.setFont(DEFAULT_FONT);
        g2.setStroke(DEFAULT_STROKE_3);
    }

    private void drawYByExtremes(Graphics2D g2, GearsExtremes ge) {
        g2.drawLine(PADDING_LEFT, PADDING_TOP, PADDING_LEFT, this.getHeight() - PADDING_BOTTOM);

        int frontCount = ge.getFrontMax() - ge.getFrontMin();
        int segment = this.getVerticalSegment();
        int quarterSegment = segment / 4;
        int halfSegment = segment / 2;

        int i = 0;
        while (i <= frontCount) {

            g2.setStroke(DOTTED_STROKE_1);
            g2.drawLine(
                    PADDING_LEFT,
                    PADDING_TOP + (i * segment) + quarterSegment,
                    this.getWidth() - PADDING_RIGHT,
                    PADDING_TOP + (i * segment) + quarterSegment);

            g2.setStroke(DEFAULT_STROKE_1);
            g2.drawLine(
                    PADDING_LEFT,
                    PADDING_TOP + (i * segment) + quarterSegment,
                    (PADDING_LEFT / 4 * 3),
                    PADDING_TOP + (i * segment) + quarterSegment);

            int gearToPrint = i + ge.getFrontMin();
            if (Gears.getInstance().isInGears(gearToPrint, Gears.GearLocation.FRONT)) {
                g2.setFont(BOLD_FONT);
            } else {
                g2.setFont(ITALIC_FONT);
            }
            g2.drawString(String.valueOf(gearToPrint), (PADDING_LEFT / 4), PADDING_TOP + (i * segment) + (DEFAULT_FONT.getSize2D() / 4 * 5));

            i = i + this.getSegmentMultiplication(segment);
        }

        g2.setFont(DEFAULT_FONT);
        g2.setStroke(DEFAULT_STROKE_3);

    }

    private int getSegmentMultiplication(int segment) {
        if (segment < VERTICAL_SEGMENT_MIN_WIDTH && segment > (VERTICAL_SEGMENT_MIN_WIDTH / 3 * 2)) {
            return 2;
        } else if (segment <= (VERTICAL_SEGMENT_MIN_WIDTH / 3 * 2) && segment > (VERTICAL_SEGMENT_MIN_WIDTH / 2)) {
            return 3;
        } else if (segment <= (VERTICAL_SEGMENT_MIN_WIDTH / 2) && segment > (VERTICAL_SEGMENT_MIN_WIDTH / 3)) {
            return 4;
        } else if (segment <= (VERTICAL_SEGMENT_MIN_WIDTH / 3)) {
            return 5;
        }

        return 1;
    }

    private void drawGraph(Graphics2D g2) {
    }

    private void drawCycle(Graphics2D g2, Point center, int radius) {
        g2.drawOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
    }

    private int getHorizontalSegment() {
        int rearCount = Gears.getInstance().getGearsExtremes().getRearMax() - Gears.getInstance().getGearsExtremes().getRearMin();
        return ((this.getWidth() - PADDING_LEFT - PADDING_RIGHT) / (rearCount + 1));
    }

    private int getVerticalSegment() {
        int frontCount = Gears.getInstance().getGearsExtremes().getFrontMax() - Gears.getInstance().getGearsExtremes().getFrontMin();
        return ((this.getHeight() - PADDING_TOP - PADDING_BOTTOM) / (frontCount + 1));
    }

}
