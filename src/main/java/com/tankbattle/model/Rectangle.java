package com.tankbattle.model;

public  class Rectangle {
   protected int x,y;
   protected double width,height;
   public boolean intersects(Rectangle rec){
        double a1=this.x-width/2,
                b1=this.x+width/2,
                c1=this.y-height/2,
                d1=this.y+height/2;
        double a2=rec.x-rec.width/2,
                b2=rec.x+rec.width/2,
                c2=rec.y-rec.height/2,
                d2=rec.y+rec.height/2;
        if (Math.max(a1,a2)<=Math.min(b1,b2)&&
                Math.max(c1,c2)<=Math.min(d1,d2)) {
            return true;
        }else {
            return false;
        }
    }
    protected Rectangle(int x,int y,double width ,double height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;

    }

    public int getX(){
       return x;
    }
    public int getY(){
       return y;
    }


}
