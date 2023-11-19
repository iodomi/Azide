package xyz.azide.util.move;

import xyz.azide.trait.Util;

public final class MovementUtil implements Util {
    public static boolean getMoving() {
        return mc.thePlayer.moveStrafing != 0 || mc.thePlayer.moveForward != 0;
    }

    public static double getSpeed() {
        return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }

    public static boolean onGround(final double y) {
        return mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty();
    }

    public static void strafe() {
        setMoving(getSpeed());
    }

    public static double stop(){
        return mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0;
    }

    public static double direction(){
        float rotationYaw = mc.thePlayer.rotationYaw;

        if(mc.thePlayer.moveForward < 0f){
            rotationYaw += 180;
        }

        float forward = 1f;

        if(mc.thePlayer.moveForward < 0f){
            forward = -0.5f;
        }else if (mc.thePlayer.moveForward > 0f){
            forward = 0.5f;
        }

        if(mc.thePlayer.moveStrafing > 0f){
            rotationYaw -= 90f * forward;
        } else if (mc.thePlayer.moveStrafing < 0f) {
            rotationYaw += 90f * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    public static void setMoving(final double speed){
        double yaw = MovementUtil.direction();

        if (getMoving()) {
            mc.thePlayer.motionX = -Math.sin(yaw) * speed;
            mc.thePlayer.motionZ = Math.cos(yaw) * speed;
        }
    }
}
