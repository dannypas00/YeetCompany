package com.nhlstenden.amazonsimulatie.models;

import java.util.HashMap;

public class Lerp {


    double speed, targetX, targetZ, currentX, currentZ, tempX, tempZ;
    public Lerp(){
    }

    public HashMap<String, Double> getLerp(double x, double z, Node target, double speed){
        HashMap<String, Double> hash = new HashMap<>();
        targetX = target.getX() + (1 * Math.signum(target.getX() - x));
        System.out.println("Targetx(" + target.getX() + ") - x(" + x + ") = " + (1 * target.getX() - x));
        targetZ = target.getZ() + (1 * Math.signum(target.getZ() - z));
        currentX = x;
        currentZ = z;

        if(targetX - currentX > 1 || targetX - currentX < -1){
             tempX += (targetX - currentX) * speed;
        }
        else{
            tempX = targetX-1;
        }

        if(targetZ - currentZ > 1 || targetZ - targetZ < -1){
            tempZ += (targetZ - currentZ) * speed;
        }
        else{
            tempZ = targetZ-1;
        }
        hash.put("x", tempX);
        hash.put("z", tempZ);
        return hash;
    }

    public double getRotation(double current, double target, double speed){
        current += (target - current) * speed;
        return current;
    }
}
