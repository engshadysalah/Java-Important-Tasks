/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.drawing.drawrectangel;

import java.awt.Graphics;

/**
 *
 * @author root
 */
public class Rectangel {
    
    
        public final double x;
        public final double y;
        public final double width;
        public final double hight;

        public Rectangel(double x, double y, double width  , double hight) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.hight = hight;
        }

        public void paint(Graphics g) {
           
            //g.clearRect((int)this.x1, (int)this.y1, (int)this.x2, (int)this.y2);
            g.drawRect((int)x, (int)y, (int)width, (int)hight);
 
        }

    
}
