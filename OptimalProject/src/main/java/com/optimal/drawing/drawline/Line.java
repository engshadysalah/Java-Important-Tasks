/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.drawing.drawline;

import java.awt.Graphics;

/**
 *
 * @author root
 */
public class Line {
    
    
 
        public final int x1;
        public final int x2;
        public final int y1;
        public final int y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        public void paint(Graphics g) {
            g.drawLine(this.x1, this.y1, this.x2, this.y2);
            System.out.println("x:" + x1);

//            
//            double length=Math.sqrt(pow((x2-x1),2) +pow((y2-y1),2));
//            
//            System.out.println("===========> length is = "+length);
        }

    }

 