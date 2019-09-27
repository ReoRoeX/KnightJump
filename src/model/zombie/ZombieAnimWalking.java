package model.zombie;

import java.awt.*;

public class ZombieAnimWalking implements ZombieAnimStrategy {

    Zombie context;

    public ZombieAnimWalking(Zombie context) {
        this.context = context;
    }

    @Override
    public Image animate() {
            switch (context.walkCycle) {
                case 0:
                    return context.frame1;
                case 1:
                    return context.frame2;
                case 2:
                    return context.frame3;
                case 3:
                    return context.frame4;
            }
            return context.ded;
    }
}
