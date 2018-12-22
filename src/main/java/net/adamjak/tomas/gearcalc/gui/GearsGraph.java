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
import net.adamjak.tomas.gearcalc.gears.GearRatio;
import net.adamjak.tomas.gearcalc.gears.Gears;
import net.adamjak.tomas.gearcalc.gears.GearsExtremes;

/**
 *
 * @author Tomas Adamjak <thomas@adamjak.net>
 */
public class GearsGraph extends JComponent {

    private final static int PADDING_TOP = 10;
    private final static int PADDING_BOTTOM = 100;
    private final static int PADDING_LEFT = 40;
    private final static int PADDING_RIGHT = 10;

    private final static Color[] GRAPH_COLORS = {
        new Color(231, 76, 60), // red
        new Color(52, 152, 219), // blue
        new Color(46, 204, 113), // green
        new Color(155, 89, 182), // purple
        new Color(243, 156, 18), // orange
        new Color(127, 140, 141) // gray
    };

    private final static int VERTICAL_SEGMENT_MIN_WIDTH = 25;
    private final static int GEAR_DOT_SIZE = 3;

    private final static Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
    private final static Font BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 13);
    private final static Font ITALIC_FONT = new Font(Font.SANS_SERIF, Font.ITALIC, 13);

    private static final Color DEFAULT_COLOR = Color.DARK_GRAY;

    private final static Stroke DEFAULT_STROKE_1 = new BasicStroke(1);
    private final static Stroke DEFAULT_STROKE_3 = new BasicStroke(3);
    private final static Stroke DOTTED_STROKE_1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{1, 5}, 0);

    public GearsGraph() {
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(DEFAULT_FONT);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setColor(DEFAULT_COLOR);
        g2.setStroke(DEFAULT_STROKE_3);
        if (Gears.getInstance().getAllGears().isEmpty()) {
            return;
        }

        GearsExtremes ge = Gears.getInstance().getGearsExtremes();

        this.drawXByExtremes(g2, ge);
        this.drawFrontGearsLegend(g2);
        this.drawYByExtremes(g2, ge);
        this.drawGraph(g2);
    }

    private void drawXByExtremes(Graphics2D g2, GearsExtremes ge) {
        g2.drawLine(PADDING_LEFT, this.getHeight() - PADDING_BOTTOM, this.getWidth() - PADDING_RIGHT, this.getHeight() - PADDING_BOTTOM);
        int rearCount = ge.getRearMax() - ge.getRearMin();
        int segment = this.getHorizontalSegment();
        int quarterSegment = this.getQuarterHorizontalSegment();
        int halfSegment = this.getHalfHorizontalSegment();

        int i = 0;
        while (i <= rearCount) {

            g2.setStroke(DEFAULT_STROKE_1);
            g2.drawLine(
                    PADDING_LEFT + (i * segment) + quarterSegment,
                    this.getHeight() - PADDING_BOTTOM,
                    PADDING_LEFT + (i * segment) + quarterSegment,
                    this.getHeight() - (PADDING_BOTTOM / 5 * 4));
            int gearToPrint = i + ge.getRearMin();

            g2.setColor(Color.LIGHT_GRAY);
            g2.setStroke(DOTTED_STROKE_1);
            g2.drawLine(
                    PADDING_LEFT + (i * segment) + quarterSegment,
                    PADDING_TOP,
                    PADDING_LEFT + (i * segment) + quarterSegment,
                    this.getHeight() - PADDING_BOTTOM);
            g2.setColor(DEFAULT_COLOR);

            if (Gears.getInstance().isInGears(gearToPrint, Gears.GearLocation.REAR)) {
                g2.setFont(BOLD_FONT);
            } else {
                g2.setFont(ITALIC_FONT);
            }

            g2.drawString(
                    String.valueOf(gearToPrint),
                    PADDING_LEFT + (i * segment),
                    this.getHeight() - (PADDING_BOTTOM / 5 * 3));

            i = i + this.getSegmentMultiplication(segment);
        }

        g2.setFont(DEFAULT_FONT);
        g2.setStroke(DEFAULT_STROKE_3);
    }

    private void drawFrontGearsLegend(Graphics2D g2) {
        int frontGearsLegendSegment = (this.getWidth() - PADDING_LEFT - PADDING_RIGHT) / Gears.getInstance().getAllFrontGears().size();

        int i = 0;
        for (GearRatio gearRatio : Gears.getInstance().getAllGears().values()) {

            for (Integer frontGears : gearRatio.getFront()) {
                g2.setColor(GRAPH_COLORS[i]);

                this.drawHorizontalLineByStartAndLength(
                        g2,
                        new Point(
                                PADDING_LEFT + (i * frontGearsLegendSegment) + frontGearsLegendSegment / 2 - 20,
                                this.getHeight() - (PADDING_BOTTOM / 5) - g2.getFont().getSize() / 2),
                        15);

                g2.setColor(DEFAULT_COLOR);
                g2.drawString(
                        String.valueOf(frontGears),
                        PADDING_LEFT + (i * frontGearsLegendSegment) + frontGearsLegendSegment / 2,
                        this.getHeight() - (PADDING_BOTTOM / 5));
                i++;
            }
        }
    }

    private void drawYByExtremes(Graphics2D g2, GearsExtremes ge) {
        g2.drawLine(PADDING_LEFT, PADDING_TOP, PADDING_LEFT, this.getHeight() - PADDING_BOTTOM);

        int frontCount = (int) Math.floor(ge.getMaxRatio()) - (int) Math.floor(ge.getMinRatio());
        int segment = this.getVerticalSegment();
        int quarterSegment = this.getQuarterVerticalSegment();
        int halfSegment = this.getHalfHorizontalSegment();

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

            int ratioToPrint = (int) Math.floor(ge.getMinRatio()) + i;
            if (Gears.getInstance().isInGears(ratioToPrint, Gears.GearLocation.FRONT)) {
                g2.setFont(BOLD_FONT);
            } else {
                g2.setFont(ITALIC_FONT);
            }

            String ratioStringToPrint = String.valueOf(ratioToPrint);

            g2.drawString(ratioStringToPrint, (PADDING_LEFT / 5), PADDING_TOP + (i * segment) + g2.getFontMetrics().getHeight() / 2);

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
        // todo
        int frontColor = 0;
        for (GearRatio gearRatio : Gears.getInstance().getAllGears().values()) {

            for (Integer front : gearRatio.getFront()) {
                g2.setColor(GRAPH_COLORS[frontColor]);
                Point previousPoint = null;
                for (Integer rear : gearRatio.getRear()) {

                    double ratio = (double) front / (double) rear;
                    Point pointToDraw = this.getPointByRatioRearGear(ratio, rear);
                    this.drawCycleByCenterAndRadius(g2, pointToDraw, GEAR_DOT_SIZE);
                    this.drawRatioNextDot(g2, ratio, pointToDraw);

                    if (previousPoint != null) {
                        this.drawLineByPoints(g2, previousPoint, pointToDraw);
                    }
                    previousPoint = pointToDraw;
                }
                frontColor++;
            }

        }

        g2.setColor(DEFAULT_COLOR);

    }

    private void drawRatioNextDot(Graphics2D g2, double ratio, Point dotPoint) {
        g2.drawString(String.format("%.2f", ratio), dotPoint.x + GEAR_DOT_SIZE, dotPoint.y + GEAR_DOT_SIZE + g2.getFont().getSize());
    }

    private void drawCycleByCenterAndRadius(Graphics2D g2, Point center, int radius) {
        g2.drawOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
    }

    private void drawHorizontalLineByStartAndLength(Graphics2D g2, Point start, int length) {
        g2.drawLine(start.x, start.y, start.x + length, start.y);
    }

    private int getHorizontalSegment() {
        int rearCount = Gears.getInstance().getGearsExtremes().getRearMax() - Gears.getInstance().getGearsExtremes().getRearMin();
        return ((this.getWidth() - PADDING_LEFT - PADDING_RIGHT) / (rearCount + 1));
    }

    private int getHalfHorizontalSegment() {
        return this.getHorizontalSegment() / 2;
    }

    private int getQuarterHorizontalSegment() {
        return this.getHorizontalSegment() / 4;
    }

    private int getVerticalSegment() {
        GearsExtremes ge = Gears.getInstance().getGearsExtremes();
        int frontCount = (int) Math.floor(ge.getMaxRatio()) - (int) Math.floor(ge.getMinRatio());
        return ((this.getHeight() - PADDING_TOP - PADDING_BOTTOM) / (frontCount + 1));
    }

    private int getHalfVerticalSegment() {
        return this.getVerticalSegment() / 2;
    }

    private int getQuarterVerticalSegment() {
        return this.getVerticalSegment() / 4;
    }

    private Point getPointByRatioRearGear(double ratio, int rear) {
        GearsExtremes ge = Gears.getInstance().getGearsExtremes();
        if (ratio < ge.getMinRatio() || ratio > ge.getMaxRatio()) {
            throw new IllegalArgumentException("Ratio in not from context");
        }

        if (rear < ge.getRearMin() || rear > ge.getRearMax()) {
            throw new IllegalArgumentException("Rear gear is not in ratio");
        }

        Point p = new Point();
        p.x = (rear - ge.getRearMin()) * this.getHorizontalSegment() + PADDING_LEFT + this.getQuarterHorizontalSegment();
        p.y = (int) Math.round(ratio * this.getVerticalSegment() + PADDING_TOP + this.getQuarterVerticalSegment());

        return p;
    }

    private void drawLineByPoints(Graphics2D g2, Point start, Point end) {
        g2.drawLine(start.x, start.y, end.x, end.y);
    }

}
